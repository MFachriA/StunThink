package com.example.stunthink.presentation.screen.main.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.stunthink.R
import com.example.stunthink.presentation.component.card.EducationCard
import com.example.stunthink.presentation.component.card.MonitoringCard
import com.example.stunthink.presentation.ui.theme.StunThinkTheme
import com.example.stunthink.presentation.ui.theme.Typography

@Composable
fun HomeScreen(
    navController: NavController
) {
    StunThinkTheme {
        Box(modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    MonitoringCard(
                        modifier = Modifier,
                        title = "Monitoring Anak",
                        image = R.drawable.mother_baby_illustration,
                        description = "Belum ada data anak",
                        buttonText = "Tambah Data Anak"
                    ) {

                    }
                }
                item {
                    MonitoringCard(
                        modifier = Modifier,
                        title = "Monitoring Ibu",
                        image = R.drawable.mother_illustration,
                        description = "Belum ada data ibu",
                        buttonText = "Tambah Data ibu"
                    ) {

                    }
                }
                item {
                    EducationList()
                }
            }
        }
    }
}

@Composable
fun EducationList() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "Edukasi Stunting",
            style = Typography.headlineSmall
        )
        Text(
            text = "Lihat Semua",
            style = Typography.bodySmall
        )
    }
    Column(
        modifier = Modifier.padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        (1..2).forEach {
            EducationCard(
                modifier = Modifier,
                imageLink = "https://p2ptm.kemkes.go.id/uploads/VHcrbkVobjRzUDN3UCs4eUJ0dVBndz09/2017/stunting_01.png",
                title = "Cegah Stunting dengan Perbaikan Pola Makan, Pola Asuh dan Sanitasi",
                description = "Padahal seperti kita ketahui, genetika merupakan faktor determinan kesehatan yang paling kecil pengaruhnya bila dibandingkan dengan faktor perilaku, lingkungan , dan pelayanan kesehatan. \\\"\\\"Terdapat tiga hal yang harus diperhatikan dalam pencegahan stunting, yaitu perbaikan terhadap pola makan, pola asuh, serta perbaikan sanitasi dan akses air bersih”, tutur Menteri Kesehatan RI, Nila Farid Moeloek, di Jakarta . Diterangkan Menkes Nila Moeloek, kesehatan berada di hilir. Seringkali masalah-masalah non kesehatan menjadi akar dari masalah stunting, baik itu masalah ekonomi, politik, sosial, budaya, kemiskinan, kurangnya pemberdayaan perempuan, serta masalah degradasi lingkungan. Karena itu, ditegaskan oleh Menkes, kesehatan membutuhkan peran semua sektor dan tatanan masyarakat. Pola Makan Masalah stunting dipengaruhi oleh rendahnya akses terhadap makanan dari segi jumlah dan kualitas gizi, serta seringkali tidak beragam. Pola Asuh Bersalin di fasilitas kesehatan, lakukan inisiasi menyusu dini dan berupayalah agar bayi mendapat colostrum air susu ibu Sanitasi dan Akses Air Bersih Rendahnya akses terhadap pelayanan kesehatan, termasuk di dalamnya adalah akses sanitasi dan air bersih, mendekatkan anak pada risiko ancaman penyakit infeksi. \\\"\\\"Pola asuh dan status gizi sangat dipengaruhi oleh pemahaman orang tua maka, dalam mengatur kesehatan dan gizi di keluarganya. Karena itu, edukasi diperlukan agar dapat mengubah perilaku yang bisa mengarahkan pada peningkatan kesehatan gizi atau ibu dan anaknya”, tutupnya.",
                publishedDate = "7 Juni 2023"
            ) {

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