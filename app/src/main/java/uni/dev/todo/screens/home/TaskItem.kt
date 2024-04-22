package uni.dev.todo.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uni.dev.todo.R
import uni.dev.todo.components.Category
import uni.dev.todo.databaseLocal.Task
import uni.dev.todo.ui.theme.Gray
import uni.dev.todo.ui.theme.GrayLight
import java.util.Locale

@Composable
fun TaskItem(vm: HomeViewModel, task: Task, isFirst: Boolean = false) {
    Column {
        if (!isFirst) Divider(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp), .8.dp, GrayLight
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(text = task.name, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding()) {
                    val category = Category.getCategory(task.categoryId)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Card(
                            content = {},
                            modifier = Modifier
                                .size(20.dp)
                                .padding(5.dp),
                            shape = RoundedCornerShape(3.dp),
                            colors = CardDefaults.cardColors(containerColor = category.lightColor)
                        )
                        Spacer(modifier = Modifier.width(1.dp))
                        Text(
                            text = category.title.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                            fontSize = 13.sp,
                            color = Gray
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    if (task.urgent) Row {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.urgent), contentDescription = "", tint = Color(
                                0xFFFF9292
                            ), modifier = Modifier.height(18.dp)
                        )
                    }

                    if (task.important) Row {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.star_fill), contentDescription = "", tint = Color(
                                0xFFFFD747
                            ), modifier = Modifier.height(20.dp)
                        )
                    }
                }
            }
            Row {
                Spacer(modifier = Modifier.width(4.dp))
                Row(
                    Modifier
                        .clip(CircleShape)
                        .clickable { vm.onEdit(task) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = "",
                        modifier = Modifier
                            .size(28.dp)
                            .padding(4.dp),
                        tint = GrayLight
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Row(
                    Modifier
                        .clip(CircleShape)
                        .clickable { vm.onDelete(task) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete),
                        contentDescription = "",
                        modifier = Modifier
                            .size(28.dp)
                            .padding(4.dp),
                        tint = GrayLight
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
    }

}
