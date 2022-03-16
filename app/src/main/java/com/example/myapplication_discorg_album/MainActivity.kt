package com.example.myapplication_discorg_album

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication_discorg_album.Network.DiscorgsAPI
import com.example.myapplication_discorg_album.entities.ResultOfDiscords
import com.example.myapplication_discorg_album.recycleView.DiscorgsAdapter
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppCompatActivity(), retrofit2.Callback<ResultOfDiscords> {
    private val discorgsAdapter = DiscorgsAdapter()
    private lateinit var searchText: EditText
    private lateinit var searchButton: Button
    private lateinit var favoriteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.main_recycle)
        recyclerView.adapter = discorgsAdapter
        searchText = findViewById<EditText>(R.id.main_search)
        searchButton = findViewById(R.id.main_searchButton)
        searchButton.setOnClickListener(this::onClickButton)
        recyclerView.setHasFixedSize(true)
        favoriteButton = findViewById(R.id.main_favorite)
        favoriteButton.setOnClickListener(this::sendDataToDataBaseAdapter)


    }


    fun onClickButton(view: View) {

        if (searchText.text.toString() != "") {
            val checkAPI = DiscorgsAPI.retrofitService.getSearchResults(
                "ZyZwXQsAyfGdBUukMtkc",
                "ltXEwZDWSKFvbcZwFxqldAcSSyjSlDel",
                searchText.text.toString(), "canada"
            )
            checkAPI.enqueue(this)
        } else {

            Toast.makeText(this, "Insert Some Data ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendDataToDataBaseAdapter(view: View) {
        val intent = Intent(this, DatabaseActivity::class.java)
        startActivity(intent)
    }

    override fun onResponse(call: Call<ResultOfDiscords>, response: Response<ResultOfDiscords>) {
        searchText.setText("")
        discorgsAdapter.removeAllData()
        val Tempdata = response.body()
        if (Tempdata != null) {
            val data = Tempdata.owner

            if (data.count() != 0) {
                discorgsAdapter.changeData(data)
            } else {
                Toast.makeText(this, "Data Not Found", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onFailure(call: Call<ResultOfDiscords>, t: Throwable) {
        println(t)
    }

}