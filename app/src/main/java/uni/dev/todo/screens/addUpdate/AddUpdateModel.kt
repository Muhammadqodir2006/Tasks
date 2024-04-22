package uni.dev.todo.screens.addUpdate

import uni.dev.todo.databaseLocal.Task
import uni.dev.todo.databaseLocal.TaskDao

class AddUpdateModel(val dao: TaskDao) {
    fun getTask(id: Int): Task {
        return dao.getTask(id)
    }

    fun addTask(task: Task) {
        dao.addTask(task)
    }

    fun updateTask(task: Task) {
        dao.updateTask(task)
    }


}