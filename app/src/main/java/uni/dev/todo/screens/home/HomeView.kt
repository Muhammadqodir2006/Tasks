package uni.dev.todo.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import uni.dev.todo.components.Category
import uni.dev.todo.ui.theme.Blue
import uni.dev.todo.ui.theme.Gray
import uni.dev.todo.ui.theme.GrayLight
import uni.dev.todo.ui.theme.Primary
import java.util.Locale

@Composable
fun HomeView(vm: HomeViewModel) {
    vm.loadTasks()
    val tasks = vm.tasks.observeAsState().value!!
    Scaffold(floatingActionButton = {
        HomeAddFAB(vm)
    }, topBar = { HomeTopBar() }) { paddingValues ->
        Column {
            FilterBar(vm, Modifier.padding(top = paddingValues.calculateTopPadding()))
            if (tasks.isNotEmpty()) TaskLazyColumn(vm)
            else {
                NoTasksYet()
            }
        }
    }
}

@Composable
fun ColumnScope.NoTasksYet() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "No tasks", color = Gray)
    }
}

@Composable
fun HomeAddFAB(vm: HomeViewModel) {
    ExtendedFloatingActionButton(
        containerColor = Blue,
        onClick = { vm.onAddButtonClick() },
        contentColor = Color.White,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp, pressedElevation = 0.dp)
    ) {
        Icon(Icons.Rounded.Add, contentDescription = "")
        Text(text = "Add", fontWeight = FontWeight.W600)
    }

}

@Composable
fun FilterBar(vm: HomeViewModel, modifier: Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(6.dp))
        CategoryFilterBar(vm)
        Spacer(modifier = Modifier.height(2.dp))
        UrgentImportantFilterBar(vm)
        Spacer(modifier = Modifier.height(4.dp))

    }
}

@Composable
fun UrgentImportantFilterBar(vm: HomeViewModel) {
    val urgentImportantFilter = vm.urgentImportantFilter.observeAsState().value!!


    Row(
        Modifier.border(1.dp, GrayLight, RoundedCornerShape(12.dp)), horizontalArrangement = Arrangement.End
    ) {
        Row {
            val index = 0
            Text(text = "Eisenhower",
                modifier = Modifier
                    .padding(horizontal = 2.dp, vertical = 2.dp)
                    .clip(RoundedCornerShape(12.dp, 4.dp, 4.dp, 12.dp))
                    .clickable { vm.onUrgentImportantFilterClick(index) }
                    .background(
                        color = if (urgentImportantFilter == index) GrayLight else Color.Transparent,
                        shape = RoundedCornerShape(12.dp, 4.dp, 4.dp, 12.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                color = if (urgentImportantFilter == index) Color.Black else Gray)
        }
        Row {
            val index = 1
            Text(text = "Urgent",
                modifier = Modifier
                    .padding(horizontal = 2.dp, vertical = 2.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .clickable { vm.onUrgentImportantFilterClick(index) }
                    .background(
                        color = if (urgentImportantFilter == index) GrayLight else Color.Transparent,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                color = if (urgentImportantFilter == index) Color.Black else Gray)
        }
        Row {
            val index = 2
            Text(text = "Important",
                modifier = Modifier
                    .padding(horizontal = 2.dp, vertical = 2.dp)
                    .clip(RoundedCornerShape(4.dp, 12.dp, 12.dp, 4.dp))
                    .clickable { vm.onUrgentImportantFilterClick(index) }
                    .background(
                        color = if (urgentImportantFilter == index) GrayLight else Color.Transparent,
                        shape = RoundedCornerShape(4.dp, 12.dp, 12.dp, 4.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                color = if (urgentImportantFilter == index) Color.Black else Gray)
        }

    }
}


@Composable
fun CategoryFilterBar(vm: HomeViewModel) {
    val categoryFilter = vm.categoryFilter.observeAsState().value!!
    LazyRow(contentPadding = PaddingValues(horizontal = 12.dp)) {
        items(Category.getCategories().size + 1) { index ->
            Text(text = if (index == 0) "All" else Category.getCategory(index - 1).title.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            },
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .clickable { vm.onCategoryClick(index) }
                    .background(
                        color = if (categoryFilter == index) GrayLight else Color.Transparent,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                color = if (categoryFilter == index) Color.Black else Gray)
        }
    }
}

@Composable
fun TaskLazyColumn(vm: HomeViewModel) {
    val tasks = vm.tasks.observeAsState().value!!
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp)
    ) {
        items(tasks.size) {
            val task = tasks[it]
            if (it == 0) TaskItem(vm, task, true)
            else TaskItem(vm, task)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar() {
    TopAppBar(
        title = { Text(text = "Tasks", fontWeight = FontWeight.W900) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Primary, titleContentColor = Color.White)
    )
}
