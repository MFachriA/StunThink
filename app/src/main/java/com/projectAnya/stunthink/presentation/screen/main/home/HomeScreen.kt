package com.projectAnya.stunthink.presentation.screen.main.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectAnya.stunthink.R
import com.projectAnya.stunthink.presentation.component.card.EducationCard
import com.projectAnya.stunthink.presentation.component.card.MonitoringCard
import com.projectAnya.stunthink.presentation.navigation.ScreenRoute
import com.projectAnya.stunthink.presentation.screen.main.MainViewModel
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.presentation.ui.theme.Typography

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val educationState = viewModel.educationState.value

    val userState = viewModel.profileState.value
    val user = userState.userDetail

    StunThinkTheme {

        Box(modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Selamat Datang,",
                            style = Typography.titleLarge,
                            color = Color.Black
                        )
                        Text(
                            text = "${user?.namaLengkap}",
                            fontWeight = FontWeight.SemiBold,
                            style = Typography.headlineSmall,
                            color = Color.Black
                        )
                        Text(
                            modifier = Modifier.padding(top = 4.dp),
                            text = "Yuk Cukupi Kebutuhan Nutrisi Anda dan Anak Anda Hari Ini!",
                            style = Typography.bodyLarge,
                            color = Color.Black
                        )
                    }
                }
                item {
                    MonitoringCard(
                        modifier = Modifier,
                        title = "Monitoring Anak",
                        description = "Catatan makanan dan nutrisi \nharian anak",
                        image = R.drawable.mother_baby_illustration
                    ) {
                        navController.navigate(route = ScreenRoute.ChildList.route)
                    }
                }
                item {
                    MonitoringCard(
                        modifier = Modifier,
                        title = "Monitoring Ibu",
                        description = "Catatan makanan dan nutrisi \nharian ibu",
                        image = R.drawable.mother_illustration
                    ) {
                        navController.navigate(route = ScreenRoute.MotherMonitoringMain.route)
                    }
                }
                item {
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Edukasi Stunting",
                            style = Typography.titleLarge
                        )
                        Row(
                            modifier = Modifier.clickable {
                                viewModel.changeSelectedMenu(2)
                            },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Lihat Semua",
                                style = Typography.bodyMedium,

                            )
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = "back",
                                tint = Color.DarkGray
                            )
                        }
                    }
                }
                items(items = educationState.educationList.take(2), itemContent = { education ->
                    EducationCard(
                        modifier = Modifier,
                        imageLink = education.urlToImage,
                        title = education.title,
                        description = education.desc
                    ) {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            key = "education",
                            value = education
                        )
                        navController.navigate(ScreenRoute.EducationDetail.route)
                    }
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navController = rememberNavController()
    )
}