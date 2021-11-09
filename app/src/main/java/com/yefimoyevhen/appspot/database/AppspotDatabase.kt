package com.yefimoyevhen.appspot.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yefimoyevhen.appspot.database.model.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class AppspotDatabase : RoomDatabase() {
    abstract fun appspotDao(): AppspotDao
}
const val DATABASE_NAME = "appspot_db"