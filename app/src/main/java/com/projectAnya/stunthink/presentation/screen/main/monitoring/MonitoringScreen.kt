package com.projectAnya.stunthink.presentation.screen.main.monitoring

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectAnya.stunthink.R
import com.projectAnya.stunthink.presentation.component.card.MonitoringCard
import com.projectAnya.stunthink.presentation.navigation.ScreenRoute
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.presentation.ui.theme.Typography
import com.projectAnya.stunthink.presentation.ui.theme.md_theme_light_primary
import kotlin.random.Random

@Composable
fun MonitoringScreen(
    navController: NavController
) {
    val randomTitle = remember { mutableStateListOf(
        "Bukan Uang dan Tahta, Namun Kesehatanlah Yang Menjadi Aset Tak Ternilai Agar Tetap Menikmati Hidup Sampai Tua.",
        "Asupan Nutrisi Seimbang Sangat Penting Agar Kita Bisa Selalu Hidup Sehat dan Produktif",
        "Sehat Membawa Kebahagian dan Yang Baik Membawa Kesehatan",
        "Hari Esok Yang Indah Dimulai Dari Sekarang",
        "Cegah Stunting Sebelum Genting"
    ) }
    val titleIndex = remember { mutableStateOf(Random.nextInt(0, 5)) }

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
                            text = randomTitle[titleIndex.value],
                            style = Typography.titleLarge,
                            color = md_theme_light_primary
                        )
                        Text(
                            modifier = Modifier.padding(bottom = 8.dp),
                            text = "Mari pantau gizi kita dan anak pada 1000 hari pertama kelahiran, agar gizi tercukupi & tumbuh besar dengan senyuman di masa depan. Kalau tidak sekarang, kapan lagi?",
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
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MonitoringScreenPreview() {
    MonitoringScreen(
        navController = rememberNavController()
    )
}