package uni.dev.todo.screens.addUpdate

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import uni.dev.todo.components.BackIconButton
import uni.dev.todo.components.Category
import uni.dev.todo.ui.theme.GrayLight
import uni.dev.todo.ui.theme.Primary
import uni.dev.todo.ui.theme.PrimaryLight
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUpdateView(vm: AddUpdateViewModel) {
    val name = vm.name.observeAsState().value!!
    val category = vm.category.observeAsState().value!!
    val urgent = vm.urgent.observeAsState().value!!
    val important = vm.important.observeAsState().value!!

    val focusRequester = remember {
        FocusRequester()
    }

    BackHandler {
        vm.onBack()
    }
    LaunchedEffect(key1 = "key1") {
        focusRequester.requestFocus()
    }
    Scaffold(topBar = {
        TopAppBar(title = { }, navigationIcon = {
            BackIconButton {
                vm.onBack()
            }
        })
    }, bottomBar = {
        TextButton(
            enabled = if (vm.taskId == -1) name.isNotBlank() else vm.task.name != name || vm.task.urgent != urgent || vm.task.important != important || vm.task.categoryId != category.id,
            onClick = { vm.addUpdate() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.textButtonColors(
                containerColor = Primary, contentColor = Color.White, disabledContainerColor = GrayLight
            )
        ) {
            Text(text = vm.getButtonText(), fontWeight = FontWeight.W900)
        }

    }) { paddingValues ->
        Column(
            Modifier
                .padding(top = paddingValues.calculateTopPadding(), start = 12.dp, end = 12.dp)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { vm.updateName(it) },
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                placeholder = {
                    Text(
                        text = "example: go shopping"
                    )
                },
                maxLines = 2,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    cursorColor = Primary,
                    focusedIndicatorColor = Primary,
                    unfocusedIndicatorColor = GrayLight,
                    unfocusedPlaceholderColor = GrayLight,
                    focusedPlaceholderColor = GrayLight,
                    selectionColors = TextSelectionColors(handleColor = Primary, backgroundColor = PrimaryLight)
                ),
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = important, onCheckedChange = { vm.updateImportant(it) }, colors = CheckboxDefaults.colors(
                        Primary
                    )
                )
                Text(text = "Important")
                Spacer(modifier = Modifier.width(6.dp))
                Checkbox(
                    checked = urgent, onCheckedChange = { vm.updateUrgent(it) }, colors = CheckboxDefaults.colors(
                        Primary
                    )
                )
                Text(text = "Urgent")
            }
            Spacer(modifier = Modifier.height(6.dp))
            LazyVerticalGrid(
                columns = GridCells.Adaptive(100.dp), modifier = Modifier.padding(
                    horizontal = 6.dp
                )
            ) {
                items(Category.entries.toList()) { cat ->
                    val isSelected = category == cat
                    Row(
                        Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .clickable { vm.updateCategory(cat) }, verticalAlignment = Alignment.CenterVertically) {
                        Card(
                            content = {},
                            modifier = Modifier
                                .size(20.dp)
                                .padding(3.dp),
                            shape = RoundedCornerShape(3.dp),
                            colors = CardDefaults.cardColors(containerColor = if (isSelected) cat.color else cat.lightColor)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = cat.title.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                            color = if (isSelected) Color.Black else GrayLight
                        )
                    }
                }
            }
        }
    }
}