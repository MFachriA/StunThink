package com.projectAnya.stunthink.presentation.screen.stunting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.projectAnya.stunthink.domain.model.stunting.Stunting
import com.projectAnya.stunthink.presentation.component.appbar.BackButtonAppBar
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.stunting.ChildStuntingContent
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.utils.DateUtils

@Composable
fun StuntingDetailScreen(
    navController: NavController,
    stunting: Stunting?
) {
    StunThinkTheme {
        Scaffold(
            topBar = {
                BackButtonAppBar(
                    title = "Detail Stunting",
                    navigationOnClick = { navController.popBackStack() }
                )
            },
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    StuntingDetailContent(stunting)
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StuntingDetailContentPreview() {
    StuntingDetailContent(
        stunting = null
    )
}

@Composable
fun StuntingDetailContent(
    stunting: Stunting?
) {
    stunting?.let {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ChildStuntingContent(
                    title = "Level Stunting",
                    content = stunting.result.displayName
                )
                ChildStuntingContent(
                    title = "Tanggal Pengukuran",
                    content = DateUtils.formatDateTimeToIndonesianTimeDate(stunting.timestamp)
                )
                ChildStuntingContent(
                    title = "Tinggi",
                    content = "${stunting.tinggiBadan} cm"
                )
                ChildStuntingContent(
                    title = "Umur",
                    content = stunting.umur
                )
            }
        }
    }
}