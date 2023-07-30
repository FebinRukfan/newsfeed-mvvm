package com.febinrukfan.newsfeed.newsfeed.model.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.febinrukfan.newsfeed.newsfeed.model.NewsFeedResponseItem
import com.febinrukfan.newsfeed.newsfeed.model.local.NewsFeedDao

@Database(
    entities = [NewsFeedResponseItem::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class NewsFeedDatabase : RoomDatabase() {

    abstract fun getNewsFeedDao(): NewsFeedDao

    companion object {

        @Volatile
        private var instance: NewsFeedDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NewsFeedDatabase::class.java,
                "newsfeed_db.db"
            ).build()
    }
}