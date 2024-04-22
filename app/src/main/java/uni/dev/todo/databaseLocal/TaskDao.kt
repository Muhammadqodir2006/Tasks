package uni.dev.todo.databaseLocal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {
    @Insert
    fun addTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Query("select * from tasks where id = :id")
    fun getTask(id: Int): Task

    @Query("select * from tasks")
    fun getTasks(): List<Task>
}