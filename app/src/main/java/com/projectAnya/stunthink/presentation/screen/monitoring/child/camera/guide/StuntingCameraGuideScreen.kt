package com.projectAnya.stunthink.presentation.screen.monitoring.child.camera.guide

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.projectAnya.stunthink.R
import com.projectAnya.stunthink.presentation.component.appbar.BackButtonAppBar
import com.projectAnya.stunthink.presentation.ui.theme.Typography

@Composable
fun StuntingCameraGuideScreen(
    navController: NavController,
) {
    val uriHandler = LocalUriHandler.current

    val annotatedText = buildAnnotatedString {
        append("1. Mendownload barcode dengan ukuran kertas A4. Untuk barcodenya dapat di download dengan menekan ")
        pushStringAnnotation(
            tag = "ThisPage",
            annotation = "ThisPage"
        )
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("tombol ini")
        }
        pop()
    }

    Scaffold(
        topBar = {
            BackButtonAppBar(
                title = "Panduan Pengukuran",
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
                    ClickableText(
                        text = annotatedText,
                        style = Typography.bodyLarge,
                        onClick = { offset ->
                            annotatedText.getStringAnnotations(
                                tag = "ThisPage",
                                start = offset,
                                end = offset
                            )[0].let {
                                uriHandler.openUri("https://storage.googleapis.com/stunted-bucket/Aruco-A4.pdf")
                            }
                        }
                    )
                    Text(
                        text = "2. Membaringkan bayi secara telentang agar memudahkan ai dalam memproses dan mengukur tinggi si bayi."
                    )
                    Text(
                        text = "3. Kertas A4 barcode yang sudah di download dan print di sebelah bayi yang sedang telentang. Meletakkan kertas barcode disamping bayi seperti pada gambar berikut sebagai contoh: "
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baby_height_unscanned),
                        contentDescription = "image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .align(Alignment.CenterHorizontally)
                        ,
                        alignment = Alignment.Center
                    )
                    Text(
                        text = "4. Lalu tekan \"Ambil Gambar\" pada halaman pengukuran tinggi."
                    )
                    Text(
                        text = "5. Menunggu AI memproses dan mengukur tinggi dari bayi tersebut."
                    )
                    Text(
                        text = "6. Melihat hasil dari pengukuran tinggi bayi yang sudah keluar. Berikut adalah contoh bayi yang sudah diukur dan hasilnya sudah keluar."
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baby_height_scanned),
                        contentDescription = "image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .align(Alignment.CenterHorizontally)
                        ,
                        alignment = Alignment.Center
                    )
                }
            }
        }
    )
}