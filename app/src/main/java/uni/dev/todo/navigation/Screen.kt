package uni.dev.todo.navigation

sealed class Screen (val route:String){
    data object Home:Screen("home")
    data object AddUpdate:Screen("add_update/{id}")
}