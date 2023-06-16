package org.d3if3112.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FoodEntity::class], version = 2, exportSchema = false)
abstract class TrekDb : RoomDatabase() {

    abstract val dao : FoodDao

    companion object {

        @Volatile
        private var INSTANCE: TrekDb? = null

        fun getInstance(context: Context): TrekDb {
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null) {

                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TrekDb::class.java,
                        "Trek.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}