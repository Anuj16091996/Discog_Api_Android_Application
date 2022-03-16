package com.reza.sqlliteapp.db

import android.provider.BaseColumns

object DatingDBContract {
    //Tables
    object AlbumTable : BaseColumns {
        const val TABLE_NAME = "Album"
        const val ALBUM_ID="int"
        const val Title = "user_id"
        const val GENRE = "username"
        const val THUMB_IMAGE = "password"
        const val LABEL = "first_name"
        const val COVER_IMAGE = "last_name"
    }
    object MessageTable : BaseColumns {
        const val MESSAGE_ID = "message_id"
        const val RECEIVER_ID = "receiver_id"
        const val SENDER_ID = "sender_id"
        const val DATE = "date"
        const val CONTENT = "content"
    }
}