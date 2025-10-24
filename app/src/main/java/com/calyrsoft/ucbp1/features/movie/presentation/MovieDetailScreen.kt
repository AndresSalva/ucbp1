package com.calyrsoft.ucbp1.features.movie.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movie: MovieModel,
    back: () -> Unit,
    viewModel: MovieDetailViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = movie) {
        viewModel.loadMovie(movie)
    }
    val movieState by viewModel.movieState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Movie Details")
                },
                navigationIcon = {
                    IconButton(
                        onClick = back,
                        content = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    )
                },
                actions = {
                    IconButton(onClick = { viewModel.toggleFavorite() }) {
                        Icon(
                            imageVector = if (movieState?.isFavorite == true) Icons.Default.Star else Icons.Outlined.StarBorder,
                            contentDescription = "Favorite"
                        )
                    }
                }
            )
        },
        content = {
            paddingValues ->
            movieState?.let {
                Column(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    Text(text = it.title)
                    Text(text = it.overview)
                }
            }
        }
    )
}