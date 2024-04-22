package uni.dev.todo.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import uni.dev.todo.R

@Composable
fun BackIconButton(onBack: () -> Unit) {
    IconButton(onClick = { onBack() }) {
        Icon(painter = painterResource(id = R.drawable.back), contentDescription = "")
    }
}