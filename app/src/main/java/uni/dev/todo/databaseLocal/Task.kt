package uni.dev.todo.databaseLocal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    @ColumnInfo(name = "category_id") val categoryId: Int,
    val important: Boolean,
    val urgent: Boolean,
    val status: Boolean = false,
)