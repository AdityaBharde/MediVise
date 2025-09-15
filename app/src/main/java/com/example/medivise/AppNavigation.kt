package com.example.medivise

import androidx.compose.material3.Button // For Button in simplified LoginScreen
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.RowScope // For Button content scope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


// Screen Composable Definitions (Placeholders - ensure these are your actual screen composables)
@Composable
fun LoginScreen(navController: NavHostController, onLoginSuccess: () -> Unit) {

     AuthenticationScreen(
         state = State,
         onGoogleSignInClicked = { /* ViewModel logic */ onLoginSuccess() },
         onLoginClicked = { email, password -> /* ViewModel logic */ onLoginSuccess() },
         onSignUpClicked = { navController.navigate(AppRoutes.SIGN_UP) },
         onForgotPasswordClicked = { navController.navigate(AppRoutes.FORGOT_PASSWORD) },
         isAuthenticating = false,
         authError = "Authentication Error",
     )
}

@Composable
fun SignUpScreen(navController: NavHostController) { Text("Sign Up Screen UI") }

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
fun ChatBotScreen() { Text("Chat Bot Screen UI") } // << CHANGED from VitalsTrackerScreen

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
    const val CHATBOT = "chatbot" // << CHANGED from VITALS_TRACKER
    const val LANGUAGE_SETTINGS = "language_settings"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppRoutes.LOGIN) {
        composable(AppRoutes.LOGIN) {
            LoginScreen(navController = navController) {
                navController.navigate(AppRoutes.DASHBOARD) {
                    popUpTo(AppRoutes.LOGIN) { inclusive = true }
                }
            }
        }
        composable(AppRoutes.SIGN_UP) { SignUpScreen(navController) }
        composable(AppRoutes.FORGOT_PASSWORD) { ForgotPasswordScreen(navController) }

        composable(AppRoutes.DASHBOARD) {
            DashboardScreenWithSidebar( // This function must be defined only ONCE in its own file
                onNavigateToMentalHealth = { navController.navigate(AppRoutes.MENTAL_HEALTH) },
                onNavigateToReportAnalyzer = { navController.navigate(AppRoutes.REPORT_ANALYZER) },
                onNavigateToChatBot = { navController.navigate(AppRoutes.CHATBOT) }, // << CHANGED
                onNavigateToMultilingualSettings = { navController.navigate(AppRoutes.LANGUAGE_SETTINGS) },
                onDrawerItemClick = { route ->
                    if (route == AppRoutes.DASHBOARD && navController.currentDestination?.route == AppRoutes.DASHBOARD) {
                        // If current is dashboard and clicking home, do nothing or just close drawer
                        // (drawer closing is handled in DashboardScreenWithSidebar)
                    } else if (route != navController.currentDestination?.route) {
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) { // Or AppRoutes.DASHBOARD
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                onLogoutClick = {
                    navController.navigate(AppRoutes.LOGIN) {
                        popUpTo(AppRoutes.DASHBOARD) { inclusive = true }
                    }
                }
            )
        }

        composable(AppRoutes.PROFILE) { ProfileScreen() }
        composable(AppRoutes.REPORTS) { ReportsScreen() }
        composable(AppRoutes.SETTINGS) { SettingsScreen() }
        composable(AppRoutes.MENTAL_HEALTH) { MentalHealthScreen() }
        composable(AppRoutes.REPORT_ANALYZER) { ReportAnalyzerScreen() }
        composable(AppRoutes.CHATBOT) { ChatBotScreen() } // << CHANGED from VITALS_TRACKER
        composable(AppRoutes.LANGUAGE_SETTINGS) { LanguageSettingsScreen() }
    }
}
