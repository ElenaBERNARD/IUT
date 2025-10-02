package com.example.marsphotos.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marsphotos.R
import com.example.marsphotos.model.MarsPhoto
import com.example.marsphotos.ui.screens.HomeScreen
import com.example.marsphotos.ui.screens.ImageDetailScreen
import com.example.marsphotos.ui.screens.MarsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarsPhotosApp() {
    val navController = rememberNavController() // Instantiate the NavController
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { MarsTopAppBar(scrollBehavior = scrollBehavior) }
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    val marsViewModel: MarsViewModel =
                        viewModel(factory = MarsViewModel.Factory)
                    HomeScreen(
                        marsUiState = marsViewModel.marsUiState,
                        retryAction = marsViewModel::getMarsPhotos,
                        navController = navController // Pass the navController
                    )
                }
                composable("imageDetail/{photoId}") { backStackEntry ->
                    val photoId = backStackEntry.arguments?.getString("photoId")
                    val marsViewModel: MarsViewModel = viewModel(factory = MarsViewModel.Factory)
                    val photo = marsViewModel.getPhotoById(photoId) // Get the photo by ID
                    if (photo != null) {
                        ImageDetailScreen(photo = photo)
                    } else {
                        // Handle case where photo is not found
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarsTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier
    )
}
