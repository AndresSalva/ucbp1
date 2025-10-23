package com.calyrsoft.ucbp1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.calyrsoft.ucbp1.features.maintenance.presentation.MaintenanceScreen
import com.calyrsoft.ucbp1.navigation.AppNavigation
import com.calyrsoft.ucbp1.navigation.NavigationDrawer
import com.calyrsoft.ucbp1.navigation.NavigationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    private lateinit var navigationViewModel: NavigationViewModel
    private lateinit var mainViewModel: MainViewModel
    private var currentIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentIntent = intent

        navigationViewModel = getViewModel()
        mainViewModel = getViewModel()

        enableEdgeToEdge()
        setContent {
            // Observamos el nuevo uiState
            val state by mainViewModel.uiState.collectAsStateWithLifecycle()

            // Usamos un 'when' para manejar explícitamente cada estado
            when (val currentState = state) {
                is MainUiState.Loading -> {
                    // MIENTRAS ESPERAMOS A FIREBASE, MOSTRAMOS UN INDICADOR DE CARGA
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is MainUiState.Ready -> {
                    // UNA VEZ QUE TENEMOS RESPUESTA, DECIDIMOS QUÉ PINTAR
                    val maintenanceStatus = currentState.status
                    Log.d("MANTENIMIENTO_DEBUG", "Estado 'Ready' alcanzado. isActive = ${maintenanceStatus.isActive}")

                    if (maintenanceStatus.isActive) {
                        // Si el mantenimiento está activo, mostramos la pantalla
                        MaintenanceScreen(message = maintenanceStatus.message)
                    } else {
                        // Si no, mostramos la app normal
                        LaunchedEffect(Unit) {
                            navigationViewModel.handleDeepLink(currentIntent)
                        }
                        LaunchedEffect(Unit) {
                            snapshotFlow { currentIntent }
                                .distinctUntilChanged()
                                .collect { intent ->
                                    navigationViewModel.handleDeepLink(intent)
                                }
                        }
                        MainApp(navigationViewModel)
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        currentIntent = intent
        val currentState = mainViewModel.uiState.value
        if (currentState is MainUiState.Ready && !currentState.status.isActive) {
            navigationViewModel.handleDeepLink(intent)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NavigationDrawerHost(
        coroutineScope: CoroutineScope,
        drawerState: DrawerState,
        navigationViewModel: NavigationViewModel,
        navController: NavHostController
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    modifier = Modifier.statusBarsPadding(),
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            AppNavigation(
                navController = navController,
                navigationViewModel = navigationViewModel,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainApp(navigationViewModel: NavigationViewModel) {
        val navController: NavHostController = rememberNavController()
        val navBackStackEntry by
        navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        val notShowTopBar =
            (currentDestination?.route?.startsWith(Screen.MovieDetail.route) == true) ||
            (currentDestination?.route?.startsWith(Screen.Atulado.route) == true)
        val navigationDrawerItems = listOf(
            NavigationDrawer.Profile,
            NavigationDrawer.Dollar,
            NavigationDrawer.Movie,
            NavigationDrawer.Github
        )
        val drawerState =

            rememberDrawerState(
                initialValue =
                    androidx.compose.material3.DrawerValue.Closed
            )

        val coroutineScope = rememberCoroutineScope()
        if (notShowTopBar) {
            AppNavigation(
                navController = navController,
                navigationViewModel = navigationViewModel,
                modifier = Modifier
            )
        } else {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier.width(256.dp)
                ) {
                    Box(
                        modifier = Modifier.width(256.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier.width(120.dp),
                            painter = painterResource(
                                id =
                                    R.drawable.ic_launcher_background
                            ),
                            contentDescription = "Logo",
                        )
                        Image(
                            painter = painterResource(
                                id =
                                    R.drawable.ic_launcher_foreground
                            ),
                            contentDescription = "Logo",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    navigationDrawerItems.forEach { item ->
                        val isSelected = currentDestination?.route == item.route
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.label
                                )
                            },
                            label = { Text(item.label) },
                            selected = isSelected,
                            onClick = {
                                navController.navigate(item.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(
                                        navController.graph.startDestinationId
                                    ) {
                                        saveState = true
                                    }
                                }
                                coroutineScope.launch {
                                    drawerState.close()
                                }
                            }
                        )
                    }
                }
            }
        ) {
            NavigationDrawerHost(coroutineScope, drawerState, navigationViewModel, navController)
        }

    }
    }
}