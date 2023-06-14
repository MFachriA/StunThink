package com.example.stunthink.presentation.screen.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.stunthink.R
import com.example.stunthink.presentation.navigation.ScreenRoute
import com.example.stunthink.presentation.ui.theme.StunThinkTheme
import com.example.stunthink.presentation.ui.theme.Typography

@Composable
fun WelcomeScreen(
    navController: NavController
) {
    StunThinkTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(painter = painterResource(id = R.drawable.stunthink_logo_white), contentDescription = "logo")
                Spacer(modifier = Modifier.padding(12.dp))
                Text(
                    text = stringResource(R.string.welcome_title),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = Typography.headlineSmall
                )
                Spacer(modifier = Modifier.padding(12.dp))
                Text(
                    text = stringResource(R.string.welcome_message),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.padding(24.dp))

                Button(
                    onClick = { navController.navigate(ScreenRoute.Login.route) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Text(text = stringResource(R.string.login), color = Color.Black)
                }
                Button(
                    onClick = { navController.navigate(ScreenRoute.Register.route) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onPrimary)
                ) {
                    Text(text = stringResource(R.string.register), color = Color.Black)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(
        navController = rememberNavController()
    )
}