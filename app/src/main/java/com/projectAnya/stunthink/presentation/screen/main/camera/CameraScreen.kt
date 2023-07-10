package com.projectAnya.stunthink.presentation.screen.main.camera

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.projectAnya.stunthink.R
import com.projectAnya.stunthink.presentation.navigation.ScreenRoute
import com.projectAnya.stunthink.presentation.navigation.start.StartViewModel
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.ChildMonitoringMainViewModel
import com.projectAnya.stunthink.presentation.ui.theme.Typography
import com.projectAnya.stunthink.utils.getImageFileFromUri
import kotlinx.coroutines.launch

@Composable
fun CameraScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    childViewModel: ChildMonitoringMainViewModel = hiltViewModel(),
    startViewModel: StartViewModel = hiltViewModel(),
    cameraViewModel: CameraViewModel = hiltViewModel()
) {
    val state = cameraViewModel.state.value
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val userTokenState: State<String?> = startViewModel.userTokenState.collectAsState()
    val userToken: String? by userTokenState

    val childIdState: State<String?> = childViewModel.childIdState.collectAsState()
    val childId: String? by childIdState

    LaunchedEffect(state) {
        cameraViewModel.validationEvents.collect { event ->
            when (event) {
                is CameraViewModel.ValidationEvent.Success -> {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "food",
                        value = event.food
                    )
                    if (childId.isNullOrEmpty()) {
                        navController.navigate(route = ScreenRoute.MotherFoodDetail.route)
                    } else {
                        navController.navigate(route = ScreenRoute.ChildFoodDetail.route)
                    }
                }
                is CameraViewModel.ValidationEvent.Failed -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = event.message,
                            withDismissAction = true
                        )
                    }
                }
            }
        }
    }

    val selfieUri = cameraViewModel.selfieUri
    val hasPhoto = selfieUri != null

    val iconResource = if (hasPhoto) {
        Icons.Filled.Autorenew
    } else {
        Icons.Filled.AddAPhoto
    }
    var newImageUri: Uri? = null

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                cameraViewModel.onSelfieResponse(newImageUri!!)
            }
        }
    )

    val annotatedText = buildAnnotatedString {
        append("Ambil foto makanan untuk mendata gizi harian kamu. Lihat daftar makanan yang bisa di deteksi di ")
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
            append("halaman ini")
        }
        pop()
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column {
                Text(
                    text = "Deteksi Makanan",
                    style = Typography.headlineSmall
                )
                ClickableText(
                    text = annotatedText,
                    style = Typography.bodyMedium,
                    onClick = { offset ->
                        annotatedText.getStringAnnotations(
                            tag = "ThisPage",
                            start = offset,
                            end = offset
                        )[0].let {
                            navController.navigate(ScreenRoute.FoodCameraGuide.route)
                        }
                    }
                )
            }
            OutlinedButton(
                onClick = {
                    newImageUri = cameraViewModel.getNewSelfieUri()
                    cameraLauncher.launch(newImageUri)
                },
                shape = MaterialTheme.shapes.small,
                contentPadding = PaddingValues()
            ) {
                Column (horizontalAlignment = Alignment.CenterHorizontally) {
                    if (hasPhoto) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(cameraViewModel.selfieUri)
                                .crossfade(true)
                                .build(),
                            contentDescription = "image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(96.dp)
                                .aspectRatio(4 / 3f)
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.take_food_logo),
                            contentDescription = "image",
                            modifier = Modifier.padding(
                                horizontal = 64.dp,
                                vertical = 74.dp
                            )
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.BottomCenter)
                            .padding(vertical = 26.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = iconResource, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (hasPhoto) {
                                "Ambil Ulang Gambar"
                            } else {
                                "Ambil Gambar"
                            }
                        )
                    }
                }
            }
            Button(
                onClick = {
                    userToken?.let { token ->
                        getImageFileFromUri(selfieUri ?: Uri.EMPTY, context)?.let {
                            cameraViewModel.uploadFoodImage(
                                token = token,
                                image = it
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = hasPhoto
            ) {
                Text(text = "Kirim")
            }
        }

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier
                    .align(Alignment.Center)
                )
            }
        }
    }
}