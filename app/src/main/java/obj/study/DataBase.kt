package obj.study

import androidx.room.Database

import androidx.room.RoomDatabase

@Database(entities = [Cities::class], version = 1)
abstract class DataBase : RoomDatabase(){
    abstract fun WeatherDao() : WeatherDao
}