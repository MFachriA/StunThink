package com.example.stunthink.presentation.screen.sign_in

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.stunthink.R
import com.example.stunthink.database.user.UserViewModel
import com.example.stunthink.presentation.ui.theme.StunThinkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    navController: NavController
) {
    StunThinkTheme {
        Scaffold(
            topBar = {
                 TopAppBar(
                     title = { Text(text = stringResource(id = R.string.sign_in_page_title)) },
                     navigationIcon = {
                         IconButton(
                             onClick = { navController.popBackStack() },
                             content = {
                                 Icon(
                                     imageVector = Icons.Default.ArrowBack,
                                     contentDescription = "back"
                                 )
                             }
                         )
                     }
                 )
            },
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    Content()
                }
            }
        )
    }
}

@Composable
private fun Content(
    userViewModel: UserViewModel = hiltViewModel()
) {
    var emailValue by rememberSaveable { mutableStateOf("") }
    var passwordValue by rememberSaveable { mutableStateOf("") }

    val state = userViewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.sign_in_title))
        Spacer(modifier = Modifier.padding(12.dp))
        Text(text = stringResource(id = R.string.sign_in_message))
        Spacer(modifier = Modifier.padding(24.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = emailValue,
                onValueChange = { emailValue = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(id = R.string.email)) },
                placeholder = { Text(text = stringResource(id = R.string.input_email)) }
            )
            OutlinedTextField(
                value = passwordValue,
                onValueChange = { passwordValue = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(id = R.string.password)) },
                placeholder = { Text(text = stringResource(id = R.string.input_password)) }
            )
        }
        Spacer(modifier = Modifier.padding(16.dp))

        Button(
            onClick =
            {

            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = stringResource(R.string.sign_in))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen(
        navController = rememberNavController()
    )
}