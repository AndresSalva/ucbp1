package com.calyrsoft.ucbp1.features.movie.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calyrsoft.ucbp1.features.movie.domain.model.MovieModel
import com.calyrsoft.ucbp1.features.movie.domain.usecase.UpdateMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val updateMovieUseCase: UpdateMovieUseCase) : ViewModel() {

    private val _movieState = MutableStateFlow<MovieModel?>(null)
    val movieState = _movieState.asStateFlow()

    fun loadMovie(movie: MovieModel) {
        _movieState.value = movie
    }

    fun toggleFavorite() {
        _movieState.value?.let { currentMovie ->
            val updatedMovie = currentMovie.copy(isFavorite = !currentMovie.isFavorite)
            _movieState.value = updatedMovie
            viewModelScope.launch {
                updateMovieUseCase(updatedMovie)
            }
        }
    }
}
