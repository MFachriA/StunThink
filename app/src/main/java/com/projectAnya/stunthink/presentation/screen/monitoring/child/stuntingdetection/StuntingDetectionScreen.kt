package com.projectAnya.stunthink.presentation.screen.monitoring.child.stuntingdetection

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.projectAnya.stunthink.R
import com.projectAnya.stunthink.data.remote.dto.height.HeightDto
import com.projectAnya.stunthink.presentation.component.appbar.BackButtonAppBar
import com.projectAnya.stunthink.presentation.navigation.ScreenRoute
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.ChildMonitoringMainViewModel
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StuntingDetectionScreen(
    navController: NavController,
    height: HeightDto?,
    mainViewModel: ChildMonitoringMainViewModel = hiltViewModel()
) {
    StunThinkTheme {
        Scaffold(
            topBar = {
                BackButtonAppBar(
                    title = "Ukur Stunting",
                    navigationOnClick = { navController.popBackStack() }
                )
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    Content(navController, height, mainViewModel)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Content(
    navController: NavController,
    height: HeightDto?,
    mainViewModel: ChildMonitoringMainViewModel = hiltViewModel(),
    stuntingViewModel: StuntingDetectionViewModel = hiltViewModel()
) {
    val childIdState: State<String?> = mainViewModel.childIdState.collectAsState()
    val childId: String? by childIdState

    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        stuntingViewModel.setChildId(token = childId)

        height?.let {
            stuntingViewModel.onEvent(StuntingDetectionFormEvent.HeightChanged(height = height.toString()))
        }

        stuntingViewModel.validationEvents.collect { event ->
            when (event) {
                is StuntingDetectionViewModel.ValidationEvent.Success -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                    navController.navigate(route = ScreenRoute.ChildMonitoringMain.route) {
                        popUpTo(ScreenRoute.ChildMonitoringMain.route) { inclusive = true }
                    }
                }
                is StuntingDetectionViewModel.ValidationEvent.Failed -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    val stuntingDetectionState = stuntingViewModel.formState
    val stuntingDetectionSubmitState = stuntingViewModel.submitState.value

    val supineOptions = arrayOf("Terlentang", "Tidak Terlentang")
    var expanded by remember { mutableStateOf(false) }

    Image(
        painter = painterResource(id = R.drawable.register_screen_background),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        height?.let {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(height.image_url)
                    .placeholder(R.drawable.stunthink_logo_background)
                    .error(R.drawable.stunthink_logo_background)
                    .crossfade(true)
                    .scale(Scale.FILL)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = stuntingDetectionState.height,
                    onValueChange = { stuntingViewModel.onEvent(StuntingDetectionFormEvent.HeightChanged(it)) },
                    isError = stuntingDetectionState.heightError != null,
                    label = { Text(text = "Tinggi Anak") },
                    placeholder = { Text(text = "Masukkan tinggi anak") },
                    suffix = { Text(text = "cm") },
                    supportingText = stuntingDetectionState.heightError?.let {
                        { Text(text = stuntingDetectionState.heightError) }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                        ,
                        keyboardType = KeyboardType.NumberPassword
                    ),
                    maxLines = 1
                )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    OutlinedTextField(
                        value = stuntingDetectionState.supine,
                        onValueChange = {},
                        readOnly = true,
                        isError = stuntingDetectionState.supineError != null,
                        label = { Text(text = "Posisi Pengukuran") },
                        placeholder = { Text(text = "Masukkan posisi pengukuran") },
                        supportingText =
                        stuntingDetectionState.supineError?.let {
                            { Text(text = it) }
                        } ?: run {
                            { Text(text = "Posisi anak ketika tingginya diukur") }
                        },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        modifier = Modifier.exposedDropdownSize(true),
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        supineOptions.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    stuntingViewModel.onEvent(
                                        StuntingDetectionFormEvent.SupineChanged(item)
                                    )
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                Button(
                    onClick = { stuntingViewModel.onEvent(StuntingDetectionFormEvent.Submit) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                ) {
                    Text(text = "Selesai")
                }
            }
        }
    }

    if (stuntingDetectionSubmitState.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier
                .align(Alignment.Center)
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun StuntingDetectionScreenPreview() {
    StuntingDetectionScreen(
        navController = rememberNavController(),
        height = null
    )
}