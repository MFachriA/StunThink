package com.example.stunthink.presentation.screen.main.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.stunthink.R
import com.example.stunthink.presentation.navigation.ScreenRoute
import com.example.stunthink.presentation.ui.theme.StunThinkTheme
import com.example.stunthink.presentation.ui.theme.Typography

@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    StunThinkTheme {
        val context = LocalContext.current
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Akun",
                style = Typography.headlineMedium
            )
            Button(
                onClick = {
                    profileViewModel.deleteUserToken()
                    navController.navigate(route = ScreenRoute.Welcome.route) {
                        popUpTo(ScreenRoute.Main.route) { inclusive = true }
                    }
                    Toast.makeText(context, R.string.logout_success_message, Toast.LENGTH_LONG).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
            ) {
                Text(text = stringResource(id = R.string.logout))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        navController = rememberNavController()
    )
}