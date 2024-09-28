package uni.dev.todo.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.navigation.NavHostController
import uni.dev.todo.databaseLocal.Task

class HomeViewModel(private val navController: NavHostController, private val model: HomeModel) {
    private val _tasks = MediatorLiveData(model.getTasks())
    val tasks: LiveData<List<Task>> = _tasks

    private var _categoryFilter = MediatorLiveData(0)
    val categoryFilter: LiveData<Int> = _categoryFilter

    private val _urgentImportantFilter = MediatorLiveData(0)
    val urgentImportantFilter: LiveData<Int> = _urgentImportantFilter

    private var allTasks = model.getTasks()

    fun onAddButtonClick() {
        navController.navigate("add_update/-1")
    }

    fun onEdit(task: Task) {
        navController.navigate("add_update/${task.id}")
    }

    fun onDelete(task: Task) {
        model.deleteTask(task)
        notifyDeleted(task)
    }

    private fun notifyDeleted(task: Task) {
        val deletedList = _tasks.value!!.toMutableList()
        deletedList.remove(task)
        _tasks.value = deletedList
    }

    fun loadTasks() {
        allTasks = model.getTasks()
        filter()
    }

    fun getCategoryFilter(): Int {
        return categoryFilter.value!!
    }

    fun onCategoryClick(index: Int) {
        _categoryFilter.value = index
        filter()
    }

    private fun filter() {
        val categoryFiltered = filterByCategory(allTasks)
        val urgentImportantFiltered = filterByUrgentImportant(categoryFiltered)
        _tasks.value = urgentImportantFiltered
    }

    private fun filterByUrgentImportant(tasks: List<Task>): List<Task> {
//        return tasks
        when (urgentImportantFilter.value) {
            0 -> {
                val newList = mutableListOf<Task>()
                newList.addAll(tasks.filter { it.urgent && it.important })
                newList.addAll(tasks.filter { it.important && !it.urgent })
                newList.addAll(tasks.filter { it.urgent && !it.important })
                newList.addAll(tasks.filter { !it.urgent && !it.important })

                return newList
            }

            1 -> {
                return tasks.sortedBy { !it.important }.filter { it.urgent }
            }

            2 -> {
                return tasks.sortedBy { !it.urgent }.filter { it.important }
            }
        }
        return emptyList()
    }

    private fun filterByCategory(tasks: List<Task>): List<Task> {
        val categoryFilterId = _categoryFilter.value!! - 1
        if (categoryFilterId == -1) return tasks
        return tasks.filter { it.categoryId == categoryFilterId }
    }

    fun onUrgentImportantFilterClick(index: Int) {
        _urgentImportantFilter.value = index
        filter()
    }
}