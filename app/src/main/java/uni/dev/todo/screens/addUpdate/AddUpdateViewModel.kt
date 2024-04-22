package uni.dev.todo.screens.addUpdate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import uni.dev.todo.components.Category
import uni.dev.todo.databaseLocal.Task

class AddUpdateViewModel(
    private val navController: NavHostController, private val model: AddUpdateModel,  val taskId: Int
) {
    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    private val _category = MutableLiveData(Category.Personal)
    val category: LiveData<Category> = _category

    private val _urgent = MutableLiveData(false)
    val urgent: LiveData<Boolean> = _urgent

    private val _important = MutableLiveData(true)
    val important: LiveData<Boolean> = _important

    var task = Task(-2, "", 0, false, urgent = false)

    init {
        if (taskId != -1) {
            task = model.getTask(id = taskId)
            _name.value = task.name
            _category.value = Category.getCategory(task.categoryId)
            _urgent.value = task.urgent
            _important.value = task.important
        }
    }

    fun onBack() {
        navController.popBackStack()
    }

    fun updateName(newText: String) {
        _name.value = newText
    }

    fun addUpdate() {
        if (taskId == -1) addTask() else updateTask()
    }

    private fun addTask() {
        val newTask = Task(
            name = name.value!!,
            categoryId = category.value!!.id,
            important = important.value!!,
            urgent = urgent.value!!
        )
        model.addTask(newTask)
        navController.popBackStack()
    }

    private fun updateTask() {
        val updatedTask = Task(
            id = task.id,
            name = name.value!!,
            categoryId = category.value!!.id,
            important = important.value!!,
            urgent = urgent.value!!
        )
        model.updateTask(updatedTask)
        navController.popBackStack()
    }

    fun getButtonText(): String {
        return if (taskId == -1) "Add" else "Save"
    }

    fun updateCategory(category: Category) {
        _category.value = category
    }

    fun updateImportant(newValue: Boolean) {
        _important.value = newValue
    }

    fun updateUrgent(newValue: Boolean) {
        _urgent.value = newValue
    }


}