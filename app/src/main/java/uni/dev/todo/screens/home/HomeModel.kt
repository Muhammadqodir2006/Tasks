package uni.dev.todo.screens.home

import uni.dev.todo.databaseLocal.Task
import uni.dev.todo.databaseLocal.TaskDao

class HomeModel(val dao: TaskDao) {
    fun getTasks(): List<Task> {
        return  dao.getTasks()
    }
    fun deleteTask(task: Task){
        dao.deleteTask(task)
    }
}