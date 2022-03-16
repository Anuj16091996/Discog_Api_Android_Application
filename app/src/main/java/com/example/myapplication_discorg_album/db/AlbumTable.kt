package com.reza.sqlliteapp.db

import android.content.ContentValues
import android.content.Context
import com.example.myapplication_discorg_album.entities.Album
import java.util.*


class AlbumTable(context: Context) {
    private val dbHelper = DatingDbHelper(context)

    fun insertData(album: Album) {
        //Map of column name + row value
        val values = ContentValues().apply {
            put(DatingDBContract.AlbumTable.GENRE, album.genre.toString())
            put(DatingDBContract.AlbumTable.THUMB_IMAGE, album.thumb_Image)
            put(DatingDBContract.AlbumTable.LABEL, album.label.toString())
            put(DatingDBContract.AlbumTable.COVER_IMAGE, album.cover_Image)
            put(DatingDBContract.AlbumTable.Title, album.title)
        }

        val writeToDb = dbHelper.writableDatabase //EXPENSIVE if DB is closed
        //Second argument: What to do when there is no value.
        // Because of null: If there is no value then we just do not insert.
        val newRowId = writeToDb.insert(DatingDBContract.AlbumTable.TABLE_NAME, null, values)
    }

    fun authenticate(album: Album): Boolean {
        for (user in getAll()) {
            if (user.title == album.title) {
                return true
            }
        }
        return false;
    }

    fun getSingleAlbum(album: Album): Album? {
        for (user in getAll()) {
            if (user.title == album.title) {
                return user
            }
        }
        return null
    }

    //    fun get(username: String): User? {
//        val readFromDb = dbHelper.readableDatabase //EXPENSIVE if DB is closed.
//
//        //Select Columns you want
//        val projection = arrayOf(
//            DatingDBContract.AlbumTable.Title,
//            DatingDBContract.AlbumTable.GENRE,
//            DatingDBContract.AlbumTable.THUMB_IMAGE,
//            DatingDBContract.AlbumTable.LABEL,
//            DatingDBContract.AlbumTable.COVER_IMAGE,
//        )
//
//        val cursor = readFromDb.rawQuery(
//            "SELECT * from ${DatingDBContract.AlbumTable.TABLE_NAME} " +
//                    "where ${DatingDBContract.AlbumTable.GENRE} like '$username%'", null
//        )
//
//        with(cursor) {
//            if (moveToNext()) {
//                val user = User(
//                    getInt(getColumnIndexOrThrow(DatingDBContract.AlbumTable.Title)),
//                    getString(getColumnIndexOrThrow(DatingDBContract.AlbumTable.GENRE)),
//                    getString(getColumnIndexOrThrow(DatingDBContract.AlbumTable.THUMB_IMAGE)),
//                    getString(getColumnIndexOrThrow(DatingDBContract.AlbumTable.LABEL)),
//                    getString(getColumnIndexOrThrow(DatingDBContract.AlbumTable.COVER_IMAGE)),
//                )
//                return user
//            } else return null
//        }
//    }
//
    fun getAll(): MutableList<Album> {
        val readFromDb = dbHelper.readableDatabase //EXPENSIVE if DB is closed.

        //Select Columns you want
        val projection = arrayOf(
            DatingDBContract.AlbumTable.ALBUM_ID,
            DatingDBContract.AlbumTable.Title,
            DatingDBContract.AlbumTable.GENRE,
            DatingDBContract.AlbumTable.THUMB_IMAGE,
            DatingDBContract.AlbumTable.LABEL,
            DatingDBContract.AlbumTable.COVER_IMAGE,
        )

        //WHERE PART only to avoid SQL Injection
        //val selection = "${DatingDBContract.UserTable.USERNAME} = ? AND ${DatingDBContract.UserTable.PASSWORD} = ?"
        //val selectionArgs = arrayOf("rezaUser", "rezaPassword")


        //Sorting
        val orderBy = "${DatingDBContract.AlbumTable.LABEL} DESC"

        val cursor = readFromDb.query(
            DatingDBContract.AlbumTable.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            orderBy
        )

        val userList = mutableListOf<Album>()

        with(cursor) {
            while (moveToNext()) {//Moves from -1 row to next one
                val user = Album(
                    getInt(getColumnIndexOrThrow(DatingDBContract.AlbumTable.ALBUM_ID)),
                    getString(getColumnIndexOrThrow(DatingDBContract.AlbumTable.Title)),
                    getString(1).split(",").toList(),
                    getString(2).split(",").toList(),
                    getString(getColumnIndexOrThrow(DatingDBContract.AlbumTable.THUMB_IMAGE)),
                    getString(getColumnIndexOrThrow(DatingDBContract.AlbumTable.COVER_IMAGE)),
                )
                userList.add(user)
            }
        }
        cursor.close()
        return userList
    }

    //
//    fun update(user: User): Boolean {
//        val dbWrite = dbHelper.writableDatabase
//
//        val values = ContentValues().apply {
//            put(DatingDBContract.AlbumTable.THUMB_IMAGE, user.getPassword())
//            put(DatingDBContract.AlbumTable.LABEL, user.firstName)
//            put(DatingDBContract.AlbumTable.COVER_IMAGE, user.lastName)
//        }
//
//        val whereClaus = "${DatingDBContract.AlbumTable.Title} = ?"
//        val whereClausArgs = arrayOf(user.id.toString())
//
//        val rowsUpdated = dbWrite.update(
//            DatingDBContract.AlbumTable.TABLE_NAME,
//            values,
//            whereClaus,
//            whereClausArgs
//        )
//
//        if (rowsUpdated == 1)
//            return true
//        return false
//    }
//
    fun delete(album: Album): Boolean {

        val dbWrite = dbHelper.writableDatabase

        val whereClaus = "${DatingDBContract.AlbumTable.THUMB_IMAGE} LIKE ?"
        val whereClausArgs = arrayOf(album.thumb_Image)

        val deletedRows = dbWrite.delete(
            DatingDBContract.AlbumTable.TABLE_NAME,
            whereClaus,
            whereClausArgs
        )

        if (deletedRows >= 1) {
            return false
        }
        return true
    }

}














