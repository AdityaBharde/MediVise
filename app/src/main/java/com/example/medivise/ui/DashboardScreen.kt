package com.example.medivise.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat // Icon for ChatBot & Report Analyzer
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medivise.ui.theme.MediviseTheme
import kotlinx.coroutines.launch

// Data class for Feature Card items
data class FeatureCardItem(
    val title: String,
    val icon: ImageVector,
    val contentDescription: String,
    val onClick: () -> Unit
)

// Data class for Navigation Drawer items
data class NavDrawerItem(
    val label: String,
    val icon: ImageVector,
    val contentDescription: String,
    val route: String,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreenWithSidebar(
    // Callbacks for feature card navigation
    onNavigateToMentalHealth: () -> Unit,
    onNavigateToReportAnalyzer: () -> Unit,
    onNavigateToChatBot: () -> Unit, // << CHANGED from onNavigateToVitalsTracker
    onNavigateToMultilingualSettings: () -> Unit,
    // Callbacks for drawer item navigation
    onDrawerItemClick: (String) -> Unit,
    onLogoutClick: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    val navDrawerItems = listOf(
        NavDrawerItem("Home", Icons.Filled.Home, "Home Screen", "dashboard") { // Assuming "dashboard" is the route for home
            onDrawerItemClick("dashboard") // Use actual route from AppRoutes
            scope.launch { drawerState.close() }
        },
        NavDrawerItem("Profile", Icons.Filled.AccountCircle, "User Profile", "profile") { // Use AppRoutes.PROFILE
            onDrawerItemClick("profile") // Use actual route from AppRoutes
            scope.launch { drawerState.close() }
        },
        NavDrawerItem("Reports", Icons.AutoMirrored.Filled.ListAlt, "View Reports", "reports") { // Use AppRoutes.REPORTS
            onDrawerItemClick("reports") // Use actual route from AppRoutes
            scope.launch { drawerState.close() }
        },
        NavDrawerItem("Settings", Icons.Filled.Settings, "App Settings", "settings") { // Use AppRoutes.SETTINGS
            onDrawerItemClick("settings") // Use actual route from AppRoutes
            scope.launch { drawerState.close() }
        }
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(300.dp)) {
                Spacer(Modifier.height(12.dp))
                Text(
                    "MediVise",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Divider(modifier = Modifier.padding(vertical = 8.dp))

                navDrawerItems.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = item.contentDescription) },
                        label = { Text(item.label) },
                        selected = false, // You'll need to manage selected state based on current route
                        onClick = item.onClick,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                NavigationDrawerItem(
                    icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout") },
                    label = { Text("Logout") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        onLogoutClick()
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Dashboard") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Open navigation drawer")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        ) { paddingValues ->
            DashboardContent(
                modifier = Modifier.padding(paddingValues),
                onNavigateToMentalHealth = onNavigateToMentalHealth,
                onNavigateToReportAnalyzer = onNavigateToReportAnalyzer,
                onNavigateToChatBot = onNavigateToChatBot, // << CHANGED
                onNavigateToMultilingualSettings = onNavigateToMultilingualSettings
            )
        }
    }
}

@Composable
fun DashboardContent(
    modifier: Modifier = Modifier,
    onNavigateToMentalHealth: () -> Unit,
    onNavigateToReportAnalyzer: () -> Unit,
    onNavigateToChatBot: () -> Unit, // << CHANGED
    onNavigateToMultilingualSettings: () -> Unit
) {
    val featureCards = listOf(
        FeatureCardItem("Mental Health Check", Icons.Filled.Psychology, "Mental Health Check-up", onNavigateToMentalHealth),
        FeatureCardItem("Report Analyzer", Icons.AutoMirrored.Filled.Chat, "Analyze Reports", onNavigateToReportAnalyzer),
        FeatureCardItem("Chat Bot", Icons.Filled.SmartToy, "Chat with AI Bot", onNavigateToChatBot), // << CHANGED & new Icon
        FeatureCardItem("Multilingual Support", Icons.Filled.Language, "Change Language", onNavigateToMultilingualSettings)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HealthSummarySection(
            mood = "Feeling Good",
            activityLevel = "Active",
            lastCheckup = "July 15, 2024"
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Explore Features",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(featureCards) { cardItem ->
                FeatureCard(
                    title = cardItem.title,
                    icon = cardItem.icon,
                    contentDescription = cardItem.contentDescription,
                    onClick = cardItem.onClick
                )
            }
        }
    }
}

// HealthSummarySection, SummaryItem, FeatureCard composables remain the same...
// (Make sure they are present in your actual file)
@Composable
fun HealthSummarySection(mood: String, activityLevel: String, lastCheckup: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Quick Health Summary",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            SummaryItem(icon = Icons.Filled.Mood, label = "Mood", value = mood)
            SummaryItem(icon = Icons.Filled.DirectionsRun, label = "Activity", value = activityLevel)
            SummaryItem(icon = Icons.Filled.CalendarToday, label = "Last Checkup", value = lastCheckup)
        }
    }
}

@Composable
fun SummaryItem(icon: ImageVector, label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeatureCard(
    title: String,
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2
            )
        }
    }
}


@Preview(showBackground = true, name = "Dashboard Screen with Sidebar - Light")
@Composable
fun DashboardScreenWithSidebarPreviewLight() {
    MediviseTheme(darkTheme = false) {
        DashboardScreenWithSidebar(
            onNavigateToMentalHealth = {},
            onNavigateToReportAnalyzer = {},
            onNavigateToChatBot = {}, // << CHANGED
            onNavigateToMultilingualSettings = {},
            onDrawerItemClick = {},
            onLogoutClick = {}
        )
    }
}
