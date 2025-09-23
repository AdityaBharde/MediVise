package com.example.medivise

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medivise.auth.GoogleAuthUiClient
import com.example.medivise.auth.SignInViewModel
import com.example.medivise.ui.AuthenticationScreen
import com.example.medivise.ui.DashboardScreenWithSidebar
import com.example.medivise.ui.SignUpScreen
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(navController: NavHostController) { Text("Forgot Password Screen UI") }

@Composable
fun ReportsScreen() { Text("Reports Screen UI") }

@Composable
fun SettingsScreen() { Text("Settings Screen UI") }

@Composable
fun MentalHealthScreen() { Text("Mental Health Screen UI") }

@Composable
fun ReportAnalyzerScreen() { Text("Report Analyzer Screen UI") }

@Composable
fun ChatBotScreen() { Text("Chat Bot Screen UI") }

@Composable
fun LanguageSettingsScreen() { Text("Language Settings Screen UI") }

object AppRoutes {
    const val LOGIN = "login"
    const val SIGN_UP = "signup"
    const val FORGOT_PASSWORD = "forgot_password"
    const val DASHBOARD = "dashboard"
    const val PROFILE = "profile"
    const val REPORTS = "reports"
    const val SETTINGS = "settings"
    const val MENTAL_HEALTH = "mental_health"
    const val REPORT_ANALYZER = "report_analyzer"
    const val CHATBOT = "chatbot"
    const val LANGUAGE_SETTINGS = "language_settings"
}

@Composable
fun AppNavigation(googleAuthUiClient: GoogleAuthUiClient) {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = AppRoutes.LOGIN) {
        composable(AppRoutes.LOGIN) {
            val viewModel = viewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(key1 = Unit) {
                if (googleAuthUiClient.getSignedInUser() != null) {
                    navController.navigate(AppRoutes.DASHBOARD) {
                        popUpTo(AppRoutes.LOGIN) { inclusive = true }
                    }
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == RESULT_OK) {
                        coroutineScope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    Toast.makeText(context, "Sign in successful", Toast.LENGTH_LONG).show()
                    navController.navigate(AppRoutes.DASHBOARD) {
                        popUpTo(AppRoutes.LOGIN) { inclusive = true }
                    }
                    viewModel.resetState()
                }
            }

            AuthenticationScreen(
                state = state,
                onGoogleSignInClicked = {
                    coroutineScope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                },
                onSignUpClicked = { navController.navigate(AppRoutes.SIGN_UP) },
                onForgotPasswordClicked = { navController.navigate(AppRoutes.FORGOT_PASSWORD) }
            )
        }

        composable(AppRoutes.DASHBOARD) {
            DashboardScreenWithSidebar(
                onNavigateToMentalHealth = { navController.navigate(AppRoutes.MENTAL_HEALTH) },
                onNavigateToReportAnalyzer = { navController.navigate(AppRoutes.REPORT_ANALYZER) },
                onNavigateToChatBot = { navController.navigate(AppRoutes.CHATBOT) },
                onNavigateToMultilingualSettings = { navController.navigate(AppRoutes.LANGUAGE_SETTINGS) },
                onDrawerItemClick = { route -> navController.navigate(route) },
                onLogoutClick = {
                    coroutineScope.launch {
                        googleAuthUiClient.signOut()
                        Toast.makeText(context, "Signed out", Toast.LENGTH_LONG).show()
                        navController.navigate(AppRoutes.LOGIN) {
                            popUpTo(AppRoutes.DASHBOARD) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(AppRoutes.PROFILE) {
            ProfileScreen(
                userData = googleAuthUiClient.getSignedInUser(),
                onSignOut = {
                    coroutineScope.launch {
                        googleAuthUiClient.signOut()
                        Toast.makeText(context, "Signed out", Toast.LENGTH_LONG).show()
                        navController.navigate(AppRoutes.LOGIN) {
                            popUpTo(AppRoutes.DASHBOARD) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(AppRoutes.SIGN_UP) { SignUpScreen(navController) }
        composable(AppRoutes.FORGOT_PASSWORD) { ForgotPasswordScreen(navController) }
        composable(AppRoutes.REPORTS) { ReportsScreen() }
        composable(AppRoutes.SETTINGS) { SettingsScreen() }
        composable(AppRoutes.MENTAL_HEALTH) { MentalHealthScreen() }
        composable(AppRoutes.REPORT_ANALYZER) { ReportAnalyzerScreen() }
        composable(AppRoutes.CHATBOT) { ChatBotScreen() }
        composable(AppRoutes.LANGUAGE_SETTINGS) { LanguageSettingsScreen() }
    }
}