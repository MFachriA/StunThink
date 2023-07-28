package com.projectAnya.stunthink.presentation.screen.main.profile

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.projectAnya.stunthink.R
import com.projectAnya.stunthink.data.remote.dto.user.UserDto
import com.projectAnya.stunthink.presentation.navigation.ScreenRoute
import com.projectAnya.stunthink.presentation.screen.main.MainViewModel
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.stunting.ChildStuntingContent
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.utils.DateUtils
import com.projectAnya.stunthink.utils.StringUtils

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.profileState.value
    val user = state.userDetail

    StunThinkTheme {
        ProfileScreenContent(
            user = user,
            editCallback = {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = "user",
                    value = user
                )
                navController.navigate(route = ScreenRoute.EditProfile.route)
            },
            logoutCallback = {
                profileViewModel.deleteUserToken()
                navController.navigate(route = ScreenRoute.Welcome.route) {
                    popUpTo(ScreenRoute.Main.route) { inclusive = true }
                }
                Toast.makeText(context, R.string.logout_success_message, Toast.LENGTH_LONG).show()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreenContent(
        user = UserDto(
            jenisKelamin = "M",
            namaLengkap = "Muhammad Avei",
            tempatLahir = "Lubang Buaya, Jakarta Timur",
            tanggalLahir = "2000-01-01T00:00:00.000Z",
            profileUrl = ""
        ),
        editCallback = { },
        logoutCallback = { }
    )
}

@Composable
fun ProfileScreenContent(
    user: UserDto?,
    editCallback: () -> Unit,
    logoutCallback: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile_picture_illustration),
            contentDescription = "image",
            modifier = Modifier
                .size(150.dp)
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally)
            ,
            alignment = Alignment.Center
        )
        ChildStuntingContent(
            title = "Nama Lengkap",
            content = user?.namaLengkap ?: "-"
        )
        ChildStuntingContent(
            title = "Kelamin",
            content = StringUtils.convertGenderEnum(user?.jenisKelamin ?: "-")
        )
        ChildStuntingContent(
            title = "Tanggal Lahir",
            content = DateUtils.formatDateTimeToIndonesianDate(user?.tanggalLahir ?: "-")
        )
        ChildStuntingContent(
            title = "Tempat Lahir",
            content = user?.tempatLahir ?: "-"
        )
        Button(
            onClick = { editCallback() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary,
            ),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Ubah Data Akun")
        }
        Button(
            onClick = { logoutCallback() },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.error,
            ),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.error)
        ) {
            Text(text = stringResource(id = R.string.logout))
        }
    }
}