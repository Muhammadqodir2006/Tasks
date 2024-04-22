package uni.dev.todo.components

import androidx.compose.ui.graphics.Color
import uni.dev.todo.ui.theme.BlueDark
import uni.dev.todo.ui.theme.BlueDarkLight
import uni.dev.todo.ui.theme.Cyan
import uni.dev.todo.ui.theme.CyanLight
import uni.dev.todo.ui.theme.Green
import uni.dev.todo.ui.theme.GreenLight
import uni.dev.todo.ui.theme.Orange
import uni.dev.todo.ui.theme.OrangeLight
import uni.dev.todo.ui.theme.Purple
import uni.dev.todo.ui.theme.PurpleLight

enum class Category(val id :Int, val title: String, val color: Color, val lightColor: Color) {
    Personal(id = 0, "personal", Green, GreenLight),
    Study(id = 1, "study", Cyan, CyanLight),
    Home(id = 2, "home", Orange, OrangeLight),
    Work(id = 3, "work", Purple, PurpleLight),
    Shopping(id = 4, "shopping", BlueDark, BlueDarkLight), ;

    companion object{
        fun getCategory(id: Int): Category {
            return entries[id]
        }
        fun getCategories(): List<Category> {
            return entries.toList()
        }
    }
}