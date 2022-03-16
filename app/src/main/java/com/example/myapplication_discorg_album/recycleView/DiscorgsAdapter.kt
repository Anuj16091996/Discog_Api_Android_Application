package com.example.myapplication_discorg_album.recycleView

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication_discorg_album.DatabaseActivity
import com.example.myapplication_discorg_album.MainActivityRecivingActivity
import com.example.myapplication_discorg_album.R
import com.example.myapplication_discorg_album.entities.Album
import com.reza.sqlliteapp.db.AlbumTable
import com.squareup.picasso.Picasso


class DiscorgsAdapter() : RecyclerView.Adapter<DiscorgsAdapter.DiscorgsViewHolder>() {
    private val dataset = mutableListOf<Album>()


    class DiscorgsViewHolder(private val parent: DiscorgsAdapter, private val containerView: View) :
        RecyclerView.ViewHolder(containerView), View.OnCreateContextMenuListener {

        companion object {
            const val EXTRA_INTENT = "valie"
        }

        var position: Album? = null
        val click: CardView = containerView.findViewById(R.id.item_click)
        val imageView: ImageView = containerView.findViewById(R.id.itemList_Image)
        val title: TextView = containerView.findViewById(R.id.itemList_Title)
        val genre: TextView = containerView.findViewById(R.id.itemList_genre)
        val context: Context = title.context
        val menu = containerView.setOnCreateContextMenuListener(this)
        var albumTable = AlbumTable(context)

        init {
            click.setOnClickListener {
                val intent = Intent(context, MainActivityRecivingActivity::class.java)
                intent.putExtra(EXTRA_INTENT, position)
                context.startActivity(intent)
            }

        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context).apply {
                setTitle("Select Your Options")
            }
            builder.setPositiveButton("Delete", this::userSelect)
            builder.setNeutralButton("Delete All", this::userSelect)
            builder.setNegativeButton("Details", this::userSelect)
            builder.show()
        }

        private fun userSelect(dialog: DialogInterface, which: Int) {
            when (which) {
                DialogInterface.BUTTON_NEGATIVE -> {
                    val intent = Intent(context, MainActivityRecivingActivity::class.java)
                    intent.putExtra(EXTRA_INTENT, position)
                    context.startActivity(intent)
                }
                DialogInterface.BUTTON_NEUTRAL -> {
                    Toast.makeText(context, "Neutral", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    position?.let { albumTable.delete(it) }
                    Toast.makeText(context, "Album Remove", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, DatabaseActivity::class.java)
                    context.startActivity(intent)
                    (context as Activity).finish()
                }
            }

        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscorgsViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return DiscorgsViewHolder(this, view)
    }


    override fun onBindViewHolder(holder: DiscorgsViewHolder, position: Int) {
        val currentData = dataset[position]
        holder.title.setText(currentData.title)
        holder.genre.append(currentData.genre.toString())
        holder.position = dataset[position]



        if (currentData.thumb_Image != null && currentData.thumb_Image == "") {
            holder.imageView.setImageResource(R.drawable.data)
        } else {
            Picasso.get()
                .load(currentData.thumb_Image)
                .into(holder.imageView)
        }

    }

    fun addData(nature: Album) {
        this.dataset.add(nature)
        notifyDataSetChanged()
    }


    fun changeData(data: MutableList<Album>) {
        this.dataset.clear()
        this.dataset.addAll(data)
        notifyDataSetChanged()
    }

    fun removeData(nature: Album) {
        this.dataset.remove(nature)
        notifyDataSetChanged()
    }

    fun removeAllData() {
        this.dataset.clear()
        notifyDataSetChanged()
    }


    override fun getItemCount() = dataset.count()
    override fun getItemViewType(position: Int): Int {
        return R.layout.item_list
    }


}



