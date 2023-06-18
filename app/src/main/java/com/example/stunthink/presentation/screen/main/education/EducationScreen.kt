package com.example.stunthink.presentation.screen.main.education

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.stunthink.presentation.component.card.EducationCard
import com.example.stunthink.presentation.navigation.ScreenRoute
import com.example.stunthink.presentation.screen.main.MainViewModel
import com.example.stunthink.presentation.ui.theme.StunThinkTheme

@Composable
fun EducationScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    StunThinkTheme {
        val state = viewModel.state.value

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(items = state.educationList, itemContent = { education ->
                    EducationCard(
                        modifier = Modifier,
                        imageLink = education.urlToImage,
                        title = education.title,
                        description = education.desc,
                    ) {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            key = "education",
                            value = education
                        )
                        navController.navigate(ScreenRoute.EducationDetail.route)
                    }
                })
            }
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier
                        .align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EducationScreenPreview() {
    EducationScreen(
        navController = rememberNavController()
    )
}