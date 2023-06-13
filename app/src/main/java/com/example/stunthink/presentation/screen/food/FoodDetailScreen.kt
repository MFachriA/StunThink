package com.example.stunthink.presentation.screen.food

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.stunthink.presentation.ui.theme.StunThinkTheme

@Composable
fun FoodDetailScreen(
    navController: NavController,
    id: String
) {
    StunThinkTheme {
        Box(
            modifier = Modifier
            .fillMaxSize()
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = id
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FoodDetailScreenPreview() {
    FoodDetailScreen(
        navController = rememberNavController(),
        ""
    )
}