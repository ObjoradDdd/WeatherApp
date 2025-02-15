package obj.study

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherDao {
    @Query("SELECT * FROM Cities")
    fun getAll(): List<Cities>

    @Insert
    fun insert(data : Cities)

    @Delete
    fun delete(data : Cities)
}