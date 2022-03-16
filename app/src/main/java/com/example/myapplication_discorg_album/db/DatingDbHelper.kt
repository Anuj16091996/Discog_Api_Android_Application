package com.reza.sqlliteapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * If you want to access database
    val dbHelper = DatingDbHelper(context)
 */


private const val SQL_CREATE_TABLE =
    "CREATE TABLE ${DatingDBContract.AlbumTable.TABLE_NAME} (" +
            "${DatingDBContract.AlbumTable.ALBUM_ID} INTEGER PRIMARY KEY unique  , " + //"${BaseColumns._ID}"
            "${DatingDBContract.AlbumTable.Title} TEXT, " +
            "${DatingDBContract.AlbumTable.GENRE} TEXT, " +
            "${DatingDBContract.AlbumTable.THUMB_IMAGE} TEXT, " +
            "${DatingDBContract.AlbumTable.LABEL} TEXT, " +
            "${DatingDBContract.AlbumTable.COVER_IMAGE} TEXT" +
            ")"

private const val DROP_TABLE = "DROP TABLE IF EXISTS ${DatingDBContract.AlbumTable.TABLE_NAME}"

class DatingDbHelper(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

        companion object {
            const val DATABASE_NAME = "Discog_Album"
            const val DATABASE_VERSION = 1
        }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
}


















