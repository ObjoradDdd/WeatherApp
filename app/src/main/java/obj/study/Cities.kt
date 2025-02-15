package obj.study

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cities(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    @ColumnInfo val city : String = ""
)
