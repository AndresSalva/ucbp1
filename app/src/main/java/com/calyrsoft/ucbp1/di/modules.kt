package com.calyrsoft.ucbp1.di

import com.calyrsoft.ucbp1.MainViewModel
import com.calyrsoft.ucbp1.R
import com.calyrsoft.ucbp1.features.dollar.data.api.FirebaseService
import com.calyrsoft.ucbp1.features.dollar.data.database.AppRoomDatabase
import com.calyrsoft.ucbp1.features.dollar.data.datasource.DollarLocalDataSource
<<<<<<< HEAD
import com.calyrsoft.ucbp1.features.dollar.data.datasource.RealTimeRemoteDataSource
import com.calyrsoft.ucbp1.features.dollar.data.repository.DollarRepository
=======
import com.calyrsoft.ucbp1.features.dollar.data.datasource.DollarRemoteDataSource
import com.calyrsoft.ucbp1.features.dollar.data.repository.DollarRepository
import com.calyrsoft.ucbp1.features.dollar.data.datasource.RealTimeRemoteDataSource
import com.calyrsoft.ucbp1.features.dollar.data.repository.FirebaseRepository
>>>>>>> feature/moviedetail
import com.calyrsoft.ucbp1.features.dollar.domain.repository.IDollarRepository
import com.calyrsoft.ucbp1.features.dollar.domain.repository.IFirebaseRepository
import com.calyrsoft.ucbp1.features.dollar.domain.usecase.FetchDollarParallelUseCase
import com.calyrsoft.ucbp1.features.dollar.domain.usecase.FetchDollarUseCase
import com.calyrsoft.ucbp1.features.dollar.domain.usecase.UpdateDollarUseCase
import com.calyrsoft.ucbp1.features.dollar.presentation.DollarViewModel
import com.calyrsoft.ucbp1.features.github.data.api.GithubService
import com.calyrsoft.ucbp1.features.github.data.datasource.GithubRemoteDataSource
import com.calyrsoft.ucbp1.features.github.data.repository.GithubRepository
import com.calyrsoft.ucbp1.features.github.domain.repository.IGithubRepository
import com.calyrsoft.ucbp1.features.github.domain.usecase.FindByNickNameUseCase
import com.calyrsoft.ucbp1.features.github.presentation.GithubViewModel
import com.calyrsoft.ucbp1.features.login.data.datasource.LoginDataStore
import com.calyrsoft.ucbp1.features.login.data.repository.RepositoryDataStore
import com.calyrsoft.ucbp1.features.login.domain.repository.IRepositoryDataStore
import com.calyrsoft.ucbp1.features.login.domain.usecase.GetTokenUseCase
import com.calyrsoft.ucbp1.features.login.domain.usecase.SaveTokenUseCase
import com.calyrsoft.ucbp1.features.maintenance.data.datasource.MaintenanceRemoteDataSource
import com.calyrsoft.ucbp1.features.maintenance.data.repository.MaintenanceRepository
import com.calyrsoft.ucbp1.features.maintenance.domain.repository.IMaintenanceRepository
import com.calyrsoft.ucbp1.features.maintenance.domain.usecase.GetMaintenanceStatusUseCase
import com.calyrsoft.ucbp1.features.movie.data.api.MovieService
import com.calyrsoft.ucbp1.features.movie.data.datasource.MovieLocalDataSource
import com.calyrsoft.ucbp1.features.movie.data.datasource.MovieRemoteDataSource
import com.calyrsoft.ucbp1.features.movie.data.repository.MovieRepository
import com.calyrsoft.ucbp1.features.movie.domain.repository.IMoviesRepository
import com.calyrsoft.ucbp1.features.movie.domain.usecase.FetchPopularMoviesUseCase
import com.calyrsoft.ucbp1.features.movie.domain.usecase.RateMovieUseCase
import com.calyrsoft.ucbp1.features.movie.presentation.PopularMoviesViewModel
import com.calyrsoft.ucbp1.features.profile.application.ProfileViewModel
import com.calyrsoft.ucbp1.features.profile.data.repository.ProfileRepository
import com.calyrsoft.ucbp1.features.profile.domain.repository.IProfileRepository
import com.calyrsoft.ucbp1.features.profile.domain.usecase.GetProfileUseCase
import com.calyrsoft.ucbp1.navigation.NavigationViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkConstants {
    const val RETROFIT_GITHUB = "RetrofitGithub"
    const val GITHUB_BASE_URL = "https://api.github.com/"
    const val RETROFIT_MOVIE = "RetrofitMovie"
    const val MOVIE_BASE_URL = "https://api.themoviedb.org/"

    const val RETROFIT_FIREBASE = "RetrofitFirebase"

    const val FIREBASE_BASE_URL = "https://nowapp-b7b95-default-rtdb.firebaseio.com/"
}

val appModule = module {
    // OkHttpClient
    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // Retrofit
    single(named(NetworkConstants.RETROFIT_GITHUB)) {
        Retrofit.Builder()
            .baseUrl(NetworkConstants.GITHUB_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Retrofit
    single(named(NetworkConstants.RETROFIT_MOVIE)) {
        Retrofit.Builder()
            .baseUrl(NetworkConstants.MOVIE_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single(named(NetworkConstants.RETROFIT_FIREBASE)) {
        Retrofit.Builder()
            .baseUrl(NetworkConstants.FIREBASE_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // GithubService
    single<GithubService> {
        get<Retrofit>( named(NetworkConstants.RETROFIT_GITHUB)).create(GithubService::class.java)
    }
    single{ GithubRemoteDataSource(get()) }
    single<IGithubRepository>{ GithubRepository(get()) }

    factory { FindByNickNameUseCase(get()) }
    viewModel { GithubViewModel(get(), get(), get()) }

    single<IProfileRepository> { ProfileRepository() }
    factory { GetProfileUseCase(get()) }
    viewModel { ProfileViewModel(get(), get()) }

    single { AppRoomDatabase.getDatabase(get()) }
    single(named("dollarDao")) { get<AppRoomDatabase>().dollarDao() }
    single { RealTimeRemoteDataSource() }
    single { DollarLocalDataSource(get(named("dollarDao"))) }
    single<IDollarRepository> { DollarRepository(get(), get()) }
    factory { FetchDollarUseCase(get()) }
    factory { FetchDollarParallelUseCase(get()) }
    viewModel{ DollarViewModel(get(), get(), get()) }


    single(named("apiKey")) {
        androidApplication().getString(R.string.api_key)
    }

    single<MovieService> {
        get<Retrofit>(named(NetworkConstants.RETROFIT_MOVIE)).create(MovieService::class.java)
    }

    single<FirebaseService> {
        get<Retrofit>(named(NetworkConstants.RETROFIT_FIREBASE)).create(FirebaseService::class.java)
    }
    single(named("movieDao")) { get<AppRoomDatabase>().movieDao() }
    single { MovieRemoteDataSource(get(), get(named("apiKey"))) }
    single { MovieLocalDataSource(get(named("movieDao"))) }
    single<IMoviesRepository> { MovieRepository(get(), get()) }
    factory { FetchPopularMoviesUseCase(get()) }
    factory { RateMovieUseCase(get()) }
    viewModel{ PopularMoviesViewModel(get(), get()) }

    viewModel { NavigationViewModel() }
    single { LoginDataStore(androidContext()) }
    single<IRepositoryDataStore>{RepositoryDataStore(get())}
    factory { GetTokenUseCase(get()) }
    factory { SaveTokenUseCase(get()) }

<<<<<<< HEAD
    // ===============================================
    //      NUEVO MÓDULO DE MANTENIMIENTO
    // ===============================================
    single { MaintenanceRemoteDataSource() }
    single<IMaintenanceRepository> { MaintenanceRepository(get()) }
    factory { GetMaintenanceStatusUseCase(get()) }
    single { MainViewModel(get()) }
=======
    single { DollarRemoteDataSource(get()) }
    single<IFirebaseRepository>{FirebaseRepository(get())}
    factory { UpdateDollarUseCase(get()) }



>>>>>>> feature/moviedetail
}