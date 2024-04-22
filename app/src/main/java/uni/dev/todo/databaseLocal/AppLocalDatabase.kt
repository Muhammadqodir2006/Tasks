package uni.dev.todo.databaseLocal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Task::class], version = 1)
abstract class AppLocalDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao

    companion object {
        private var instance: AppLocalDatabase? = null
        fun getInstance(context: Context): AppLocalDatabase {
            if (instance == null) instance =
                Room.databaseBuilder(context, AppLocalDatabase::class.java, "app_local_database").allowMainThreadQueries()
                    .build()
            return instance!!
        }
    }
}