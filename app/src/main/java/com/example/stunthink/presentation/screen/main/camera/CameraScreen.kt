package com.example.stunthink.presentation.screen.main.camera

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.stunthink.presentation.ui.theme.Typography

@Composable
fun CameraScreen(
    navController: NavController,
    cameraViewModel: CameraViewModel = hiltViewModel()
) {
    val hasPhoto = cameraViewModel.selfieUri != null

    val iconResource = if (hasPhoto) {
        Icons.Filled.SwapHoriz
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

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "Deteksi Makanan",
            style = Typography.headlineSmall
        )
        Text(
            text = "Ambil foto makanan untuk mendata gizi harian kamu",
            style = Typography.bodyMedium
        )
        OutlinedButton(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                newImageUri = cameraViewModel.getNewSelfieUri()
                cameraLauncher.launch(newImageUri)
            },
            shape = MaterialTheme.shapes.small,
            contentPadding = PaddingValues()
        ) {
            Column {
                if (hasPhoto) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(cameraViewModel.selfieUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(96.dp)
                            .aspectRatio(4 / 3f)
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
    }
}

@Preview(showBackground = true)
@Composable
fun CameraScreenPreview() {
    CameraScreen(
        navController = rememberNavController()
    )
}