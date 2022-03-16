package com.example.myapplication_discorg_album

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication_discorg_album.entities.Album
import com.example.myapplication_discorg_album.recycleView.DiscorgsAdapter
import com.reza.sqlliteapp.db.AlbumTable
import com.squareup.picasso.Picasso

class MainActivityRecivingActivity : AppCompatActivity() {
    private lateinit var albumTable: AlbumTable
    private lateinit var imageView: ImageView
    private lateinit var title: TextView
    private lateinit var label: TextView
    private lateinit var addButton: Button
    private var data: Album? = null
    var albumAddToDatabase = false


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_reciving)
        imageView = findViewById(R.id.receiveMain_image)
        title = findViewById(R.id.receiveMain_title)
        label = findViewById(R.id.receiveMain_label)
        addButton = findViewById(R.id.receiveMain_button)
        albumTable = AlbumTable(this)


        val intent = intent
        if (intent.hasExtra(DiscorgsAdapter.DiscorgsViewHolder.EXTRA_INTENT)) {
            data =
                intent.getSerializableExtra(DiscorgsAdapter.DiscorgsViewHolder.EXTRA_INTENT) as Album
        }

        if (data != null) {
            albumAddToDatabase = albumTable.authenticate(data!!)
        }

        if (albumAddToDatabase) {
            addButton.setText("Remove")
            addButton.setBackgroundTintList(ColorStateList.valueOf(R.color.red))
            addButton.setTextColor(R.color.black)
        }


        if (data != null) {
            Picasso.get()
                .load(data?.cover_Image)
                .into(imageView)

            title.setText(data?.title)
            label.setText(data?.label.toString())
            addButton.setOnClickListener(this::addToDataBase)
        }
    }


    @SuppressLint("ResourceAsColor")
    private fun addToDataBase(view: View) {
        if (!albumAddToDatabase) {
            if (data != null) {
                albumTable.insertData(data!!)
                addButton.setText("Remove")
                addButton.setBackgroundTintList(ColorStateList.valueOf(R.color.red))
                addButton.setTextColor(R.color.black)
                Toast.makeText(this, "Album Added", Toast.LENGTH_SHORT).show()
                albumAddToDatabase = true

            }
        } else {
            albumAddToDatabase = albumTable.delete(data!!)
            Toast.makeText(this, "Album Remove", Toast.LENGTH_SHORT).show()
            addButton.setText("Add")
            addButton.setBackgroundTintList(ColorStateList.valueOf(R.color.purple_500))
            addButton.setTextColor(R.color.white)

        }


    }
}


