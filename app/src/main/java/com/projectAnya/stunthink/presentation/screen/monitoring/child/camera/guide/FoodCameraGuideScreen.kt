package com.projectAnya.stunthink.presentation.screen.monitoring.child.camera.guide

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.projectAnya.stunthink.presentation.component.appbar.BackButtonAppBar
import com.projectAnya.stunthink.presentation.ui.theme.Typography

@Composable
fun FoodCameraGuideScreen(
    navController: NavController,
) {
    Scaffold(
        topBar = {
            BackButtonAppBar(
                title = "Daftar Makanan",
                navigationOnClick = { navController.popBackStack() }
            )
        },
        content = { paddingValues ->
            Box(modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
            ) {
                Column(
                    modifier =  Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Berikut adalah daftar makanan yang dapat di deteksi:",
                        style = Typography.titleMedium
                    )
                    Text(
                        text = "1. Bebek Goreng",
                    )
                    Text(
                        text = "2. Beef Burger",
                    )
                    Text(
                        text = "3. Cumi Goreng Tepung",
                    )
                    Text(
                        text = "4. Gulai Kambing",
                    )
                    Text(
                        text = "5. Gurame Asam Manis",
                    )
                    Text(
                        text = "6. Mie Ayam",
                    )
                    Text(
                        text = "7. Rendang Sapi",
                    )
                    Text(
                        text = "8. Pelecing Kangkung",
                    )
                    Text(
                        text = "9. Sayur Asem",
                    )
                    Text(
                        text = "10. Semur Jengkol",
                    )
                    Text(
                        text = "11. Sup Buntut",
                    )
                    Text(
                        text = "12. Soto Padang",
                    )
                    Text(
                        text = "13. Tekwan",
                    )
                }
            }
        }
    )
}