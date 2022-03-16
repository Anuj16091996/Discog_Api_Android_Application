package com.example.myapplication_discorg_album

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication_discorg_album.entities.Album
import com.example.myapplication_discorg_album.recycleView.DiscorgsAdapter
import com.reza.sqlliteapp.db.AlbumTable

class DatabaseActivity : AppCompatActivity() {
    private lateinit var albumTable: AlbumTable
    private val discorgsAdapter = DiscorgsAdapter()
    private lateinit var recyclerView: RecyclerView
    private lateinit var album: MutableList<Album>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)
        albumTable = AlbumTable(this)
        album = albumTable.getAll()
        recyclerView = findViewById<RecyclerView>(R.id.database_recycle)
        recyclerView.adapter = discorgsAdapter
        discorgsAdapter.changeData(album)
        recyclerView.setHasFixedSize(true)

    }

    override fun onResume() {
        super.onResume()
        album = albumTable.getAll()
        discorgsAdapter.changeData(album)
    }


}