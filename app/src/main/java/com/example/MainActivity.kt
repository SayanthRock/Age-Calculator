package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.alpha
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.togetherWith
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.Canvas
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.random.Random
import androidx.compose.runtime.withFrameMillis
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Settings
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.CoolPrimary
import com.example.ui.theme.CoolSecondary
import androidx.compose.ui.graphics.Brush
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.CardDefaults
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.ZoneId
import java.time.Duration
import kotlinx.coroutines.delay

import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.blur
import android.content.ContentValues
import android.provider.MediaStore
import android.os.Environment
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material.icons.filled.CameraAlt

import android.content.Intent
import android.provider.CalendarContract
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.material.icons.filled.ContentCopy
import android.widget.Toast
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Calculate
import com.example.ui.CalendarScreen
import com.example.ui.theme.ThemeColors

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.animateFloat
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.LinearEasing

val PredefinedBackgrounds = listOf(
    "https://images.unsplash.com/photo-1506318137071-a8e063b4bec0?q=80&w=2000&auto=format&fit=crop", // Cosmic Space
    "https://images.unsplash.com/photo-1579033461380-adb47c3eb938?q=80&w=2000&auto=format&fit=crop", // Aurora Nights
    "https://images.unsplash.com/photo-1506744038136-46273834b3fb?q=80&w=2000&auto=format&fit=crop", // Golden Sunset
    "https://images.unsplash.com/photo-1448375240586-882707db888b?q=80&w=2000&auto=format&fit=crop", // Forest Mist
    "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?q=80&w=2000&auto=format&fit=crop", // Cyber Grid
    "https://images.unsplash.com/photo-1557683316-973673baf926?q=80&w=2000&auto=format&fit=crop", // Purple Dream
    "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?q=80&w=2000&auto=format&fit=crop", // Velvet Dark
    "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?q=80&w=2000&auto=format&fit=crop"  // Deep Pacific
)

val PredefinedBackgroundNames = listOf(
    "Cosmic Space",
    "Aurora Nights",
    "Golden Glow",
    "Forest Mist",
    "Cyber Grid",
    "Purple Dream",
    "Velvet Dark",
    "Deep Pacific"
)

data class PaletteDesignItem(
    val id: String,
    val name: String,
    val primaryColor: Color,
    val secondaryColor: Color,
    val barColor: Color
)

class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val viewModel: AgeViewModel = viewModel()
      val systemDarkTheme = isSystemInDarkTheme()
      var themeModeState by remember { mutableStateOf(viewModel.themeMode) } // 0 = Light, 1 = Dark, 2 = System
      val isDarkTheme = when (themeModeState) {
          0 -> false
          1 -> true
          2 -> systemDarkTheme
          else -> systemDarkTheme
      }
      var isAmoledEnabled by remember { mutableStateOf(viewModel.isAmoledEnabled) }
      var isSystemFontEnabled by remember { mutableStateOf(viewModel.isSystemFontEnabled) }
      var selectedColor by remember { mutableStateOf(Color(viewModel.savedColorHex)) }
      var backgroundImageUri by remember {
          mutableStateOf(viewModel.savedBackgroundImageUri?.let { android.net.Uri.parse(it) })
      }
      var isWallpaperBlurEnabled by remember { mutableStateOf(viewModel.isWallpaperBlurEnabled) }
      var wallpaperBlurRadius by remember { mutableStateOf(viewModel.wallpaperBlurRadius) }

      MyApplicationTheme(darkTheme = isDarkTheme, primaryColor = selectedColor, amoled = isAmoledEnabled) {
        var showSplash by remember { mutableStateOf(true) }
        var selectedTab by remember { mutableIntStateOf(0) }

        Scaffold(
          modifier = Modifier.fillMaxSize(),
          containerColor = MaterialTheme.colorScheme.background,
          bottomBar = {
            if (!showSplash) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    tonalElevation = 8.dp
                ) {
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Calculate,
                                contentDescription = "Age Calculator Screen"
                            )
                        },
                        label = {
                            Text(
                                "Age Calculator",
                                fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = selectedColor,
                            selectedTextColor = selectedColor,
                            indicatorColor = selectedColor.copy(alpha = 0.15f)
                        )
                    )

                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = "Calendar Screen"
                            )
                        },
                        label = {
                            Text(
                                "Calendar",
                                fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = selectedColor,
                            selectedTextColor = selectedColor,
                            indicatorColor = selectedColor.copy(alpha = 0.15f)
                        )
                    )
                }
            }
          }
        ) { innerPadding ->
          Box(
            modifier = Modifier
              .padding(innerPadding)
              .fillMaxSize()
          ) {
            AnimatedContent(
                targetState = showSplash,
                transitionSpec = {
                    (fadeIn(animationSpec = tween(700)) + scaleIn(initialScale = 1.05f, animationSpec = tween(700))) togetherWith
                    (fadeOut(animationSpec = tween(500)) + scaleOut(targetScale = 0.95f, animationSpec = tween(500)))
                },
                label = "SplashToConsole"
            ) { isSplashActive ->
                if (isSplashActive) {
                    CinematicSplashLoaderScreen(
                        selectedColor = selectedColor,
                        isDarkTheme = isDarkTheme,
                        onLoadingComplete = { showSplash = false }
                    )
                } else {
                    AnimatedContent(
                        targetState = selectedTab,
                        transitionSpec = {
                            (fadeIn(animationSpec = tween(350)) + slideInVertically { height -> height / 20 }) togetherWith
                            (fadeOut(animationSpec = tween(250)) + slideOutVertically { height -> -height / 20 })
                        },
                        label = "MainTabs"
                    ) { targetTab ->
                        when (targetTab) {
                            0 -> AgeCalculatorScreen(
                        isDarkTheme = isDarkTheme,
                        themeModeState = themeModeState,
                        onThemeModeChanged = { mode ->
                            themeModeState = mode
                            viewModel.themeMode = mode
                        },
                        isAmoledEnabled = isAmoledEnabled,
                        onAmoledChanged = { enabled ->
                            isAmoledEnabled = enabled
                            viewModel.isAmoledEnabled = enabled
                        },
                        isSystemFontEnabled = isSystemFontEnabled,
                        onSystemFontChanged = { enabled ->
                            isSystemFontEnabled = enabled
                            viewModel.isSystemFontEnabled = enabled
                        },
                        viewModel = viewModel,
                        selectedColor = selectedColor,
                        onColorSelected = { selectedColor = it },
                        backgroundImageUri = backgroundImageUri,
                        onBackgroundImageSelected = { uri ->
                            backgroundImageUri = uri
                            viewModel.savedBackgroundImageUri = uri?.toString()
                        },
                        isWallpaperBlurEnabled = isWallpaperBlurEnabled,
                        onWallpaperBlurEnabledChanged = { enabled ->
                            isWallpaperBlurEnabled = enabled
                            viewModel.isWallpaperBlurEnabled = enabled
                        },
                        wallpaperBlurRadius = wallpaperBlurRadius,
                        onWallpaperBlurRadiusChanged = { radius ->
                            wallpaperBlurRadius = radius
                            viewModel.wallpaperBlurRadius = radius
                        }
                    )
                }
            }
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgeCalculatorScreen(
    isDarkTheme: Boolean,
    themeModeState: Int,
    onThemeModeChanged: (Int) -> Unit,
    isAmoledEnabled: Boolean,
    onAmoledChanged: (Boolean) -> Unit,
    isSystemFontEnabled: Boolean,
    onSystemFontChanged: (Boolean) -> Unit,
    viewModel: AgeViewModel = viewModel(),
    selectedColor: Color,
    onColorSelected: (Color) -> Unit,
    backgroundImageUri: android.net.Uri?,
    onBackgroundImageSelected: (android.net.Uri?) -> Unit,
    isWallpaperBlurEnabled: Boolean,
    onWallpaperBlurEnabledChanged: (Boolean) -> Unit,
    wallpaperBlurRadius: Float,
    onWallpaperBlurRadiusChanged: (Float) -> Unit
) {
    var dob by remember { mutableStateOf(viewModel.savedDob) }
    var today by remember { mutableStateOf(LocalDate.now()) }

    val history by viewModel.history.collectAsStateWithLifecycle()

    var showDobPicker by remember { mutableStateOf(false) }
    var showTodayPicker by remember { mutableStateOf(false) }
    var showSettings by remember { mutableStateOf(false) }
    var showCompareDialog by remember { mutableStateOf(false) }
    
    val onThemeToggle: () -> Unit = {
        val newMode = if (isDarkTheme) 0 else 1
        onThemeModeChanged(newMode)
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> onBackgroundImageSelected(uri) }
    )

    if (showCompareDialog) {
        CompareAgesDialog(onDismiss = { showCompareDialog = false })
    }

    if (showSettings) {
        val palettes = listOf(
            PaletteDesignItem("dynamic", "Dynamic", Color(0xFF6366F1), Color(0xFFEC4899), Color(0xFF818CF8)),
            PaletteDesignItem("nord", "Nord", Color(0xFF5E81AC), Color(0xFF88C0D0), Color(0xFF81A1C1)),
            PaletteDesignItem("cream", "Cream", Color(0xFFD08770), Color(0xFFE5A88C), Color(0xFFF4F1DE)),
            PaletteDesignItem("forest", "Forest", Color(0xFF457B52), Color(0xFFCAD2C5), Color(0xFF6B8A6B)),
            PaletteDesignItem("plum", "Plum", Color(0xFF7B3B82), Color(0xFFD6BCFA), Color(0xFFB48EAD))
        )
        
        val selectedPaletteId = when (selectedColor) {
            Color(0xFF5E81AC) -> "nord"
            Color(0xFFD08770) -> "cream"
            Color(0xFF457B52) -> "forest"
            Color(0xFF7B3B82) -> "plum"
            else -> "dynamic"
        }

        androidx.compose.ui.window.Dialog(
            onDismissRequest = { showSettings = false },
            properties = androidx.compose.ui.window.DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Scaffold(
                topBar = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.95f))
                            .windowInsetsPadding(WindowInsets.statusBars)
                            .padding(vertical = 12.dp, horizontal = 16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IconButton(
                                onClick = { showSettings = false },
                                modifier = Modifier.size(48.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Back",
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Appearance",
                                fontWeight = FontWeight.Bold,
                                fontSize = 21.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                },
                contentWindowInsets = WindowInsets.safeDrawing,
                containerColor = MaterialTheme.colorScheme.background
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 20.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // SECTION 1: Mode
                    Column(modifier = Modifier.fillMaxWidth()) {
                        SectionHeader(title = "Mode", colorAccent = selectedColor)
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val modes = listOf(
                                Triple(0, "Light", 0),
                                Triple(1, "Dark", 1),
                                Triple(2, "System", 2)
                            )
                            modes.forEach { (modeVal, label, designType) ->
                                ModePreviewCard(
                                    themeMode = designType,
                                    isSelected = themeModeState == modeVal,
                                    onClick = {
                                        onThemeModeChanged(modeVal)
                                    },
                                    label = label,
                                    accentColor = selectedColor
                                )
                            }
                        }
                    }
                    
                    // SECTION 2: Palette
                    Column(modifier = Modifier.fillMaxWidth()) {
                        SectionHeader(title = "Palette", colorAccent = selectedColor)
                        Spacer(modifier = Modifier.height(12.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            val rows = palettes.chunked(3)
                            rows.forEach { rowItems ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    rowItems.forEach { p ->
                                        Box(modifier = Modifier.weight(1f)) {
                                            PaletteCardView(
                                                palette = p,
                                                isSelected = selectedPaletteId == p.id,
                                                onClick = {
                                                    onColorSelected(p.primaryColor)
                                                    viewModel.savedColorHex = p.primaryColor.toArgb()
                                                }
                                            )
                                        }
                                    }
                                    if (rowItems.size < 3) {
                                        repeat(3 - rowItems.size) {
                                            Spacer(modifier = Modifier.weight(1f))
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    // SECTION 3: AMOLED
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onAmoledChanged(!isAmoledEnabled) },
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "True black (AMOLED)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Pure-black background — saves power on OLED screens.",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Switch(
                                checked = isAmoledEnabled,
                                onCheckedChange = { onAmoledChanged(it) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = selectedColor,
                                    checkedTrackColor = selectedColor.copy(alpha = 0.4f)
                                )
                            )
                        }
                    }
                    
                    // SECTION 4: Display
                    Column(modifier = Modifier.fillMaxWidth()) {
                        SectionHeader(title = "Display", colorAccent = selectedColor)
                        Spacer(modifier = Modifier.height(12.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSystemFontChanged(!isSystemFontEnabled) },
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "System font",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "Match your device's font for better readability.",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Switch(
                                    checked = isSystemFontEnabled,
                                    onCheckedChange = { onSystemFontChanged(it) },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = selectedColor,
                                        checkedTrackColor = selectedColor.copy(alpha = 0.4f)
                                    )
                                )
                            }
                        }
                    }
                    
                    // SECTION 5: Wallpapers
                    Column(modifier = Modifier.fillMaxWidth()) {
                        SectionHeader(title = "Wallpapers", colorAccent = selectedColor)
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // Beautiful Predefined Wallpaper Flow Grid
                        val chunkedBackgrounds = PredefinedBackgrounds.chunked(2)
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            chunkedBackgrounds.forEachIndexed { rowIndex, pair ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    pair.forEachIndexed { colIndex, url ->
                                        val index = rowIndex * 2 + colIndex
                                        val wallpaperName = PredefinedBackgroundNames.getOrNull(index) ?: "Wallpaper"
                                        val isSelected = backgroundImageUri?.toString() == url
                                        val wallScale by animateFloatAsState(
                                            targetValue = if (isSelected) 1.05f else 1.0f,
                                            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
                                            label = "wallSelectScale"
                                        )
                                        Card(
                                            modifier = Modifier
                                                .weight(1f)
                                                .aspectRatio(1.6f)
                                                .scale(wallScale)
                                                .clickable {
                                                    onBackgroundImageSelected(android.net.Uri.parse(url))
                                                }
                                                .then(
                                                    if (isSelected) Modifier.border(
                                                        2.5.dp,
                                                        selectedColor,
                                                        RoundedCornerShape(16.dp)
                                                    ) else Modifier.border(
                                                        1.dp,
                                                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                                                        RoundedCornerShape(16.dp)
                                                    )
                                                ),
                                            shape = RoundedCornerShape(16.dp),
                                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                                        ) {
                                            Box(modifier = Modifier.fillMaxSize()) {
                                                AsyncImage(
                                                    model = url,
                                                    contentDescription = wallpaperName,
                                                    contentScale = ContentScale.Crop,
                                                    modifier = Modifier.fillMaxSize()
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .background(Color.Black.copy(alpha = 0.35f))
                                                )
                                                Text(
                                                    text = wallpaperName,
                                                    color = Color.White,
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.ExtraBold,
                                                    modifier = Modifier
                                                        .align(Alignment.BottomStart)
                                                        .padding(10.dp)
                                                )
                                                if (isSelected) {
                                                    Box(
                                                        modifier = Modifier
                                                            .padding(8.dp)
                                                            .size(20.dp)
                                                            .clip(androidx.compose.foundation.shape.CircleShape)
                                                            .background(selectedColor)
                                                            .align(Alignment.TopEnd),
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        Icon(
                                                            imageVector = Icons.Default.Star,
                                                            contentDescription = "Active",
                                                            tint = Color.White,
                                                            modifier = Modifier.size(12.dp)
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (pair.size < 2) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Custom Backdrop Picker Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                },
                                modifier = Modifier.weight(1f).height(48.dp),
                                shape = RoundedCornerShape(24.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = selectedColor)
                            ) {
                                Icon(imageVector = Icons.Default.Wallpaper, contentDescription = "Gallery Picker", modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Pick from Gallery", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            }
                            
                            if (backgroundImageUri != null) {
                                OutlinedButton(
                                    onClick = { onBackgroundImageSelected(null) },
                                    modifier = Modifier.height(48.dp),
                                    shape = RoundedCornerShape(24.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.4f))
                                ) {
                                    Text("Clear Wallpaper", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        // Blurred Wallpaper Radius control
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Checkbox(
                                            checked = isWallpaperBlurEnabled,
                                            onCheckedChange = { onWallpaperBlurEnabledChanged(it) },
                                            colors = CheckboxDefaults.colors(checkedColor = selectedColor)
                                        )
                                        Spacer(modifier = Modifier.width(2.dp))
                                        Text(
                                            text = "Enable Wallpaper Blur",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                                
                                if (isWallpaperBlurEnabled) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Blur Radius",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            text = "${wallpaperBlurRadius.toInt()} dp",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Black,
                                            color = selectedColor
                                        )
                                    }
                                    Slider(
                                        value = wallpaperBlurRadius,
                                        onValueChange = { onWallpaperBlurRadiusChanged(it) },
                                        valueRange = 1f..30f,
                                        colors = SliderDefaults.colors(
                                            thumbColor = selectedColor,
                                            activeTrackColor = selectedColor,
                                            inactiveTrackColor = selectedColor.copy(alpha = 0.15f)
                                        )
                                    )
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }

    if (showDobPicker) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = dob.toEpochDay() * 24 * 60 * 60 * 1000)
        DatePickerDialog(
            onDismissRequest = { showDobPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        val newDob = LocalDate.ofEpochDay(it / (24 * 60 * 60 * 1000))
                        if (newDob != dob) {
                            dob = newDob
                            viewModel.savedDob = newDob
                            if (!newDob.isAfter(today)) {
                                viewModel.saveCalculation(newDob, today)
                            }
                        }
                    }
                    showDobPicker = false
                }) { Text("OK", fontWeight = FontWeight.Bold) }
            },
            dismissButton = {
                TextButton(onClick = { showDobPicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTodayPicker) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = today.toEpochDay() * 24 * 60 * 60 * 1000)
        DatePickerDialog(
            onDismissRequest = { showTodayPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        val newToday = LocalDate.ofEpochDay(it / (24 * 60 * 60 * 1000))
                        if (newToday != today) {
                            today = newToday
                            if (!dob.isAfter(newToday)) {
                                viewModel.saveCalculation(dob, newToday)
                            }
                        }
                    }
                    showTodayPicker = false
                }) { Text("OK", fontWeight = FontWeight.Bold) }
            },
            dismissButton = {
                TextButton(onClick = { showTodayPicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    val isBirthday = dob.monthValue == today.monthValue && dob.dayOfMonth == today.dayOfMonth

    // Dynamic drifting background orbits - Ambient Floating Orbs for luxury feel
    val ambientTransition = rememberInfiniteTransition(label = "AmbientFloatingOrbs")
    
    val orb1FloatingX by ambientTransition.animateFloat(
        initialValue = -15f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb1FloatingX"
    )
    val orb1FloatingY by ambientTransition.animateFloat(
        initialValue = -25f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(14000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb1FloatingY"
    )

    val orb2FloatingX by ambientTransition.animateFloat(
        initialValue = 20f,
        targetValue = -20f,
        animationSpec = infiniteRepeatable(
            animation = tween(15000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb2FloatingX"
    )
    val orb2FloatingY by ambientTransition.animateFloat(
        initialValue = -15f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(13000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb2FloatingY"
    )

    val orb3FloatingX by ambientTransition.animateFloat(
        initialValue = -10f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(16000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb3FloatingX"
    )
    val orb3FloatingY by ambientTransition.animateFloat(
        initialValue = 15f,
        targetValue = -20f,
        animationSpec = infiniteRepeatable(
            animation = tween(11000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb3FloatingY"
    )

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background), contentAlignment = Alignment.TopCenter) {
        if (backgroundImageUri != null) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = backgroundImageUri,
                    contentDescription = "Background Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .then(
                            if (isWallpaperBlurEnabled) Modifier.blur(wallpaperBlurRadius.dp)
                            else Modifier
                        )
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            if (isDarkTheme) {
                                if (isWallpaperBlurEnabled) Color(0x990B0F19) else Color(0xCD0B0F19)
                            } else {
                                if (isWallpaperBlurEnabled) Color(0x55E2E8F0) else Color(0xADE2E8F0)
                            }
                        )
                )
            }
        } else {
            // Elegant large fluid gradient background orbs with drift animations
            val systemColorAccent = selectedColor
            Box(
                modifier = Modifier
                    .offset(x = (-60 + orb1FloatingX).dp, y = (-20 + orb1FloatingY).dp)
                    .size(240.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(systemColorAccent.copy(alpha = 0.28f))
                    .blur(50.dp)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset(x = (60 + orb2FloatingX).dp, y = (-80 + orb2FloatingY).dp)
                    .size(280.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.16f))
                    .blur(60.dp)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = (-30 + orb3FloatingX).dp, y = (140 + orb3FloatingY).dp)
                    .size(320.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(systemColorAccent.copy(alpha = 0.18f))
                    .blur(80.dp)
            )
        }

        Column(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            // Elegant Capsule Panel Top Header
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.45f)),
                border = androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f),
                            selectedColor.copy(alpha = 0.25f),
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f)
                        )
                    )
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(androidx.compose.foundation.shape.CircleShape)
                                    .background(selectedColor)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                "TEMPORAL CONSOLE // V3.2",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = selectedColor,
                                letterSpacing = 1.2.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            "Chronos Age",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Rounded visual glass controllers dock
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val context = LocalContext.current
                        
                        IconButton(
                            onClick = {
                                val p = Period.between(dob, today)
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, "I am ${p.years} years, ${p.months} months, and ${p.days} days old! Calculated instantly with Chronos Age Calculator.")
                                }
                                context.startActivity(Intent.createChooser(shareIntent, "Share Age Calculation"))
                            },
                            modifier = Modifier
                                .size(38.dp)
                                .clip(androidx.compose.foundation.shape.CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share Info",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        IconButton(
                            onClick = onThemeToggle,
                            modifier = Modifier
                                .size(38.dp)
                                .clip(androidx.compose.foundation.shape.CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        ) {
                            androidx.compose.animation.AnimatedContent(
                                targetState = isDarkTheme,
                                transitionSpec = {
                                    (androidx.compose.animation.scaleIn() + androidx.compose.animation.fadeIn() togetherWith 
                                     androidx.compose.animation.scaleOut() + androidx.compose.animation.fadeOut())
                                },
                                label = "themeToggleIcon"
                            ) { darkTheme ->
                                Icon(
                                    imageVector = if (darkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                                    contentDescription = "Toggle Theme",
                                    tint = if (darkTheme) Color(0xFFFFB74D) else MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        IconButton(
                            onClick = { showCompareDialog = true },
                            modifier = Modifier
                                .size(38.dp)
                                .clip(androidx.compose.foundation.shape.CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        ) {
                            Icon(
                                imageVector = Icons.Default.CompareArrows,
                                contentDescription = "Compare Ages",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        IconButton(
                            onClick = { showSettings = true },
                            modifier = Modifier
                                .size(38.dp)
                                .clip(androidx.compose.foundation.shape.CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings Option",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            // Beautiful Chronometer Timeline Input Hub - Combined selectors in a luxurious timeline capsule
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)),
                border = androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.26f),
                            MaterialTheme.colorScheme.secondary.copy(alpha = 0.16f)
                        )
                    )
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    Text(
                        "TIME ANCHORS",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        letterSpacing = 1.5.sp,
                        modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
                    )

                    // Unified vertical timeline layouts representation
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Creative Timeline Graphic node track on left
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(androidx.compose.foundation.shape.CircleShape)
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f))
                                    .border(1.5.dp, MaterialTheme.colorScheme.primary, androidx.compose.foundation.shape.CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(androidx.compose.foundation.shape.CircleShape)
                                        .background(MaterialTheme.colorScheme.primary)
                                )
                            }
                            // Neon glowing physical line connector
                            Box(
                                modifier = Modifier
                                    .width(2.dp)
                                    .height(58.dp)
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
                                        )
                                    )
                            )
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(androidx.compose.foundation.shape.CircleShape)
                                    .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f))
                                    .border(1.5.dp, MaterialTheme.colorScheme.secondary, androidx.compose.foundation.shape.CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(androidx.compose.foundation.shape.CircleShape)
                                        .background(MaterialTheme.colorScheme.secondary)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        // High fidelity selectors stacked side-by-side to timeline line
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Birth Epoch date controller
                            ChronologyAnchorWidget(
                                badgeText = "Birth Epoch Origin",
                                date = dob,
                                badgeColor = MaterialTheme.colorScheme.primary,
                                dateColor = MaterialTheme.colorScheme.onSurface,
                                onClick = { showDobPicker = true }
                            )

                            // Target Anchor date controller
                            ChronologyAnchorWidget(
                                badgeText = "Temporal Target Coord",
                                date = today,
                                badgeColor = MaterialTheme.colorScheme.secondary,
                                dateColor = MaterialTheme.colorScheme.onSurface,
                                onClick = { showTodayPicker = true }
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = dob.isAfter(today),
                enter = expandVertically(spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)) + fadeIn(),
                exit = shrinkVertically(spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)) + fadeOut()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.85f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.4f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Time Anomaly Detected",
                            tint = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                "Temporal Paradox Detected",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                fontSize = 14.sp
                            )
                            Text(
                                "Epoch Origin must precede the Temporal Anchor setting.",
                                color = MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.8f),
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }

            // Results core display
            AnimatedVisibility(
                visible = !dob.isAfter(today),
                enter = expandVertically(spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)) + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                AgeCard(dob = dob, today = today)
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Beautiful terminal timeline for recent calculations
            if (history.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "HISTORICAL LOG DATABASE",
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.5.sp
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    "RECORDS: ${history.size}",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            history.take(4).forEach { item ->
                                val historyDob = LocalDate.ofEpochDay(item.dob)
                                val historyToday = LocalDate.ofEpochDay(item.today)
                                val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
                                
                                val itemIntSource = remember(item) { androidx.compose.foundation.interaction.MutableInteractionSource() }
                                val itemPressed by itemIntSource.collectIsPressedAsState()
                                val itemScale by animateFloatAsState(
                                    targetValue = if (itemPressed) 0.96f else 1f,
                                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
                                    label = "itemClickScale"
                                )
                                
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .scale(itemScale)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f))
                                        .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f), RoundedCornerShape(16.dp))
                                        .clickable(
                                            interactionSource = itemIntSource,
                                            indication = androidx.compose.foundation.LocalIndication.current
                                        ) {
                                            dob = historyDob
                                            viewModel.savedDob = historyDob
                                            today = historyToday
                                        }
                                        .padding(14.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Box(
                                                modifier = Modifier
                                                    .size(32.dp)
                                                    .clip(androidx.compose.foundation.shape.CircleShape)
                                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.06f)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Timeline,
                                                    contentDescription = "Log",
                                                    tint = MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier.size(14.dp)
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(12.dp))
                                            Column {
                                                Text(
                                                    text = "Born Epoch: ${historyDob.format(formatter)}",
                                                    fontSize = 12.sp,
                                                    color = MaterialTheme.colorScheme.onSurface,
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                                Text(
                                                    text = "Target Anchor: ${historyToday.format(formatter)}",
                                                    fontSize = 10.sp,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                                    fontWeight = FontWeight.Medium
                                                )
                                            }
                                        }
                                        val p = Period.between(historyDob, historyToday)
                                        Text(
                                            text = "${p.years} yrs",
                                            fontWeight = FontWeight.Black,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontSize = 13.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(androidx.compose.foundation.shape.CircleShape)
                        .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Temporal Core Engine • Chronos",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(androidx.compose.foundation.shape.CircleShape)
                        .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f))
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        ConfettiEffect(dob, today, isBirthday)
    }
}

@Composable
fun ChronologyAnchorWidget(
    badgeText: String,
    date: LocalDate,
    badgeColor: Color,
    dateColor: Color,
    onClick: () -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("EEEE, MMM d, yyyy")
    val interactionSource = androidx.compose.runtime.remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "scaleSpring"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
            .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f), RoundedCornerShape(18.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = androidx.compose.foundation.LocalIndication.current,
                onClick = onClick
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1.5f)) {
                // Customized mini status badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(badgeColor.copy(alpha = 0.08f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = badgeText.uppercase(),
                        color = badgeColor,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.8.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = date.format(formatter),
                    color = dateColor,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(badgeColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Calendar Picker",
                    tint = badgeColor,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun AgeCard(dob: LocalDate, today: LocalDate) {
    val period = Period.between(dob, today)
    var nextBday = dob.plusYears((today.year - dob.year).toLong())
    if (nextBday.isBefore(today)) {
        nextBday = nextBday.plusYears(1)
    }

    val nextBdayDayOfWeek = nextBday.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }

    val totalYears = period.years
    val totalMonths = ChronoUnit.MONTHS.between(dob, today)
    val totalWeeks = ChronoUnit.WEEKS.between(dob, today)
    val totalDays = ChronoUnit.DAYS.between(dob, today)
    val totalHours = totalDays * 24
    val totalMinutes = totalHours * 60
    val totalSeconds = totalMinutes * 60

    // Precise live calculation matrix state ticks
    var liveSeconds by remember(dob, today) { mutableStateOf(0L) }
    var liveMillis by remember(dob, today) { mutableStateOf(0) }
    var liveHours by remember(dob, today) { mutableStateOf(0L) }
    var liveMinutes by remember(dob, today) { mutableStateOf(0L) }
    var totalDaysLived by remember(dob, today) { mutableStateOf(0L) }

    LaunchedEffect(dob, today) {
        val birthInstant = dob.atStartOfDay(ZoneId.systemDefault()).toInstant()
        val endInstant = if (today == LocalDate.now()) {
            null
        } else {
            today.atStartOfDay(ZoneId.systemDefault()).toInstant()
        }

        while (true) {
            val nowInstant = endInstant ?: java.time.Instant.now()
            val totalSecs = java.time.temporal.ChronoUnit.SECONDS.between(birthInstant, nowInstant)
            val totalDays = java.time.temporal.ChronoUnit.DAYS.between(birthInstant, nowInstant)
            val totalHrs = java.time.temporal.ChronoUnit.HOURS.between(birthInstant, nowInstant)
            val totalMins = java.time.temporal.ChronoUnit.MINUTES.between(birthInstant, nowInstant)

            liveSeconds = totalSecs
            totalDaysLived = totalDays
            liveHours = totalHrs
            liveMinutes = totalMins

            if (endInstant != null) {
                liveMillis = 0
                break
            } else {
                liveMillis = ((System.currentTimeMillis() % 1000) / 10).toInt()
                delay(50)
            }
        }
    }

    val alphaAnim = remember(dob, today) { Animatable(0f) }
    val offsetYAnim = remember(dob, today) { Animatable(30f) }

    LaunchedEffect(dob, today) {
        launch {
            alphaAnim.snapTo(0f)
            alphaAnim.animateTo(1f, animationSpec = tween(650))
        }
        launch {
            offsetYAnim.snapTo(30f)
            offsetYAnim.animateTo(0f, animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow))
        }
    }

    val defaultSurface = MaterialTheme.colorScheme.surface
    val themeTargetColor = when {
        totalYears < 13 -> Color(0xFFFFB74D) // Playful Gold
        totalYears < 25 -> Color(0xFFFF8A65) // Neo Coral
        totalYears < 40 -> Color(0xFF6366F1) // Indigo Pulse
        totalYears < 55 -> Color(0xFF8B5CF6) // Violet Dream
        totalYears < 70 -> Color(0xFF10B981) // Emerald Core
        else -> Color(0xFF06B6D4) // Deep Ocean
    }

    val resultsBackgroundColor by animateColorAsState(
        targetValue = androidx.compose.ui.graphics.lerp(defaultSurface, themeTargetColor, 0.12f),
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "resultsBg"
    )

    val graphicsLayer = rememberGraphicsLayer()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    Column(
        modifier = Modifier
            .animateContentSize(animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow))
            .offset(y = offsetYAnim.value.dp)
            .alpha(alphaAnim.value)
    ) {
        val milestones = setOf(1, 10, 18, 20, 21, 25, 30, 40, 50, 60, 65, 70, 75, 80, 90, 100)
        if (totalYears in milestones) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Milestone Reward",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Milestone Reached: Year $totalYears Alignment 🎉",
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 13.sp,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Milestone Reward",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // Spectacular Glassmorphic Temporal Core Display - Replaced with Unified Results Dashboard Card
        UnifiedAgeResultsComponent(
            dob = dob,
            today = today,
            period = period,
            nextBday = nextBday,
            nextBdayDayOfWeek = nextBdayDayOfWeek,
            themeTargetColor = themeTargetColor,
            resultsBackgroundColor = resultsBackgroundColor,
            onCopyClick = {
                val ageText = "I am ${period.years} years, ${period.months} months, and ${period.days} days old!"
                clipboardManager.setText(AnnotatedString(ageText))
                Toast.makeText(context, "Age copied to clipboard!", Toast.LENGTH_SHORT).show()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // REAL-TIME COMPUTATIONAL PRECISION TICKER HUD (Terminal Style)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .drawWithContent {
                    graphicsLayer.record {
                        this@drawWithContent.drawContent()
                    }
                    drawLayer(graphicsLayer)
                },
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)), // Deep obsidian terminal view
            border = androidx.compose.foundation.BorderStroke(
                width = 1.2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF10B981).copy(alpha = 0.45f),
                        Color(0xFF0F172A),
                        Color(0xFF10B981).copy(alpha = 0.15f)
                    )
                )
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(androidx.compose.foundation.shape.CircleShape)
                                .background(Color(0xFF10B981)) // Live Green status
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "RUNNING ELAPSED TIME INDEX",
                            color = Color(0xFF10B981),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.2.sp
                        )
                    }
                    Text(
                        "SYS_UPTIME",
                        color = Color(0xFF94A3B8).copy(alpha = 0.5f),
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    // Row 1: Live Days
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("[EPOCH DAYCOUNT]", color = Color(0xFF38BDF8), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text("$totalDaysLived days", color = Color(0xFFF1F5F9), fontSize = 12.sp, fontWeight = FontWeight.Black)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("[TOTAL HOURS]", color = Color(0xFF38BDF8), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text("$liveHours hrs", color = Color(0xFFF1F5F9), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("[TOTAL MINUTES]", color = Color(0xFF38BDF8), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text("$liveMinutes mins", color = Color(0xFFF1F5F9), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    // Row 4: Detailed seconds and milliseconds counting up
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("[ELAPSED SECONDS.MS]", color = Color(0xFFF59E0B), fontSize = 10.sp, fontWeight = FontWeight.ExtraBold)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "$liveSeconds",
                                color = Color(0xFFF59E0B),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                text = String.format(".%02d", liveMillis),
                                color = Color(0xFFF59E0B).copy(alpha = 0.7f),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Beautiful side-by-side Horoscope Oracle essence Cards
        Row(
            modifier = Modifier.fillMaxWidth(), 
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                ZodiacOracleCard("WESTERN ZODIAC", getWesternZodiac(dob), themeTargetColor)
            }
            Box(modifier = Modifier.weight(1f)) {
                ZodiacOracleCard("CHINESE ZODIAC", getChineseZodiac(dob.year), themeTargetColor)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // High Fidelity Terminal Summary statistics board layout
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.35f)),
            border = androidx.compose.foundation.BorderStroke(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.26f),
                        MaterialTheme.colorScheme.tertiary.copy(alpha = 0.16f)
                    )
                )
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    "CHRONOS DETAILED DATABASE pods",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.5.sp,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    textAlign = TextAlign.Center
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    SummaryItem("YEARS", "$totalYears")
                    SummaryItem("MONTHS", "$totalMonths")
                    SummaryItem("WEEKS", "$totalWeeks")
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    SummaryItem("DAYS", "$totalDays")
                    SummaryItem("HOURS", "$totalHours")
                    SummaryItem("MINUTES", "$totalMinutes")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Large high gloss buttons dock
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_INSERT).apply {
                        data = CalendarContract.Events.CONTENT_URI
                        putExtra(CalendarContract.Events.TITLE, "Birthday Reminder")
                        putExtra(CalendarContract.Events.ALL_DAY, true)
                        val startMillis = nextBday.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                        putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
                        putExtra(CalendarContract.EXTRA_EVENT_END_TIME, startMillis + 24 * 60 * 60 * 1000)
                        putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY")
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier.weight(1f).height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = "Calendar Notification Link", modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Set Reminder", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        try {
                            val bitmap = graphicsLayer.toImageBitmap().asAndroidBitmap()
                            saveBitmapToMediaStoreAndShare(context, bitmap)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                },
                modifier = Modifier.weight(1f).height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Icon(imageVector = Icons.Default.CameraAlt, contentDescription = "Screen Capture", modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.onSecondary)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Capture & Share", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondary)
            }
        }
    }
}

@Composable
fun ZodiacOracleCard(badgeTitle: String, sign: String, themeBorder: Color) {
    val parts = sign.split(" ")
    val name = parts.getOrNull(0) ?: ""
    val emoji = parts.getOrNull(1) ?: ""

    // Dynamic attribute tags to augment oracle aesthetics
    val attributeTag = when (name) {
        "Aries" -> "Fire • Dynamic Initiative"
        "Taurus" -> "Earth • Resilience"
        "Gemini" -> "Air • Intelligence"
        "Cancer" -> "Water • Empathy"
        "Leo" -> "Fire • Courage"
        "Virgo" -> "Earth • Precision Detail"
        "Libra" -> "Air • Harmony Balance"
        "Scorpio" -> "Water • Focus Intensity"
        "Sagittarius" -> "Fire • Optimism Scout"
        "Capricorn" -> "Earth • Ambition Peak"
        "Aquarius" -> "Air • Innovation Mind"
        "Pisces" -> "Water • Compassion Dream"
        // Chinese Zodiac values
        "Rat" -> "Water • Resourceful"
        "Ox" -> "Earth • Patient Trust"
        "Tiger" -> "Wood • Brave Explorer"
        "Rabbit" -> "Wood • Gentle Heart"
        "Dragon" -> "Earth • Vital Energy"
        "Snake" -> "Fire • Wisdom Intuition"
        "Horse" -> "Fire • Freedom Speed"
        "Goat" -> "Earth • Gentle Artist"
        "Monkey" -> "Metal • Wit Intelligence"
        "Rooster" -> "Metal • Radiant Courage"
        "Dog" -> "Earth • Loyal Alliance"
        "Pig" -> "Water • Pure Abundance"
        else -> "Epoch Aspect"
    }

    val elementColors = when {
        attributeTag.startsWith("Fire") -> listOf(Color(0xFFEF4444), Color(0xFFF59E0B))
        attributeTag.startsWith("Earth") -> listOf(Color(0xFF10B981), Color(0xFF047857))
        attributeTag.startsWith("Air") -> listOf(Color(0xFF6366F1), Color(0xFF8B5CF6))
        attributeTag.startsWith("Water") -> listOf(Color(0xFF3B82F6), Color(0xFF00D2D3))
        attributeTag.startsWith("Wood") -> listOf(Color(0xFF22C55E), Color(0xFF15803D))
        attributeTag.startsWith("Metal") -> listOf(Color(0xFF94A3B8), Color(0xFF475569))
        else -> listOf(themeBorder, themeBorder.copy(alpha = 0.5f))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.2.dp,
            brush = Brush.linearGradient(colors = elementColors)
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = badgeTitle,
                fontSize = 8.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                fontWeight = FontWeight.Black,
                letterSpacing = 1.1.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = emoji, fontSize = 28.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = attributeTag,
                fontSize = 9.sp,
                color = elementColors[0],
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun BirthdayCountdownTimer(nextBday: LocalDate) {
    var currentTime by remember { mutableStateOf(LocalDateTime.now()) }

    LaunchedEffect(nextBday) {
        while (true) {
            delay(1000)
            currentTime = LocalDateTime.now()
        }
    }

    var targetTime = nextBday.atStartOfDay()
    if (targetTime.isBefore(currentTime)) {
        targetTime = targetTime.plusYears(1)
    }

    val duration = Duration.between(currentTime, targetTime)
    val totalSeconds = duration.seconds
    if (totalSeconds < 0) return // Fallback just in case

    val days = duration.toDays()
    val hours = duration.toHours() % 24
    val minutes = duration.toMinutes() % 60
    // We can show days, hours, mins, maybe no seconds if space is tight, but seconds are fun.
    val seconds = totalSeconds % 60

    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CountdownItem(value = days.toString(), label = "Days")
            CountdownItem(value = String.format("%02d", hours), label = "Hours")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CountdownItem(value = String.format("%02d", minutes), label = "Minutes")
            CountdownItem(value = String.format("%02d", seconds), label = "Seconds")
        }
    }
}

@Composable
fun CountdownItem(value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            AnimatedContent(
                targetState = value,
                transitionSpec = {
                    (slideInVertically { height -> height } + fadeIn() togetherWith
                            slideOutVertically { height -> -height } + fadeOut())
                },
                label = "countdownValue",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) { targetValue ->
                Text(
                    text = targetValue,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 10.dp)
                )
            }
        }
        Text(text = label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 4.dp), fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun ZodiacItem(label: String, sign: String) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            val parts = sign.split(" ")
            val name = parts.getOrNull(0) ?: ""
            val emoji = parts.getOrNull(1) ?: ""
            Text(text = emoji, fontSize = 28.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
fun AgeBlock(value: Int, label: String, accentColor: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedContent(
                targetState = value,
                transitionSpec = {
                    (slideInVertically { height -> height } + fadeIn() togetherWith
                            slideOutVertically { height -> -height } + fadeOut())
                },
                label = "ageBlockValue"
            ) { targetValue ->
                Text(
                    text = "$targetValue",
                    color = accentColor,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label.uppercase(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun MiniCountdownCell(value: String, label: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedContent(
                targetState = value,
                transitionSpec = {
                    (slideInVertically { height -> height / 2 } + fadeIn() togetherWith
                            slideOutVertically { height -> -height / 2 } + fadeOut())
                },
                label = "countdownMiniValue"
            ) { targetValue ->
                Text(
                    text = targetValue,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                fontSize = 9.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun UnifiedAgeResultsComponent(
    dob: LocalDate,
    today: LocalDate,
    period: Period,
    nextBday: LocalDate,
    nextBdayDayOfWeek: String,
    themeTargetColor: Color,
    resultsBackgroundColor: Color,
    onCopyClick: () -> Unit
) {
    var currentTime by remember { mutableStateOf(LocalDateTime.now()) }

    LaunchedEffect(nextBday) {
        while (true) {
            delay(1000)
            currentTime = LocalDateTime.now()
        }
    }

    var targetTime = nextBday.atStartOfDay()
    if (targetTime.isBefore(currentTime)) {
        targetTime = targetTime.plusYears(1)
    }

    val duration = Duration.between(currentTime, targetTime)
    val totalSeconds = duration.seconds
    val daysRemaining = duration.toDays()
    val hoursRemaining = (duration.toHours() % 24).coerceAtLeast(0)
    val minutesRemaining = (duration.toMinutes() % 60).coerceAtLeast(0)
    val secondsRemaining = (totalSeconds % 60).coerceAtLeast(0)

    val isBdayToday = dob.monthValue == today.monthValue && dob.dayOfMonth == today.dayOfMonth

    // Solar cycle progress
    val prevBday = nextBday.minusYears(1)
    val totalDaysInCycle = ChronoUnit.DAYS.between(prevBday, nextBday).coerceAtLeast(1)
    val daysElapsed = ChronoUnit.DAYS.between(prevBday, today).coerceIn(0, totalDaysInCycle)
    val progressFraction = daysElapsed.toFloat() / totalDaysInCycle.toFloat()
    val progressPercent = (progressFraction * 100).toInt()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        themeTargetColor.copy(alpha = 0.55f),
                        themeTargetColor.copy(alpha = 0.15f),
                        themeTargetColor.copy(alpha = 0.65f)
                    )
                ),
                shape = RoundedCornerShape(26.dp)
            ),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = resultsBackgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Timeline,
                        contentDescription = "Age Results",
                        tint = themeTargetColor,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "TEMPORAL SNAPSHOT",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        letterSpacing = 1.2.sp
                    )
                }
                
                IconButton(
                    onClick = onCopyClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy Age",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Calculated Age Grid/Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AgeBlock(value = period.years, label = "Years", accentColor = themeTargetColor, modifier = Modifier.weight(1f))
                AgeBlock(value = period.months, label = "Months", accentColor = themeTargetColor.copy(alpha = 0.85f), modifier = Modifier.weight(1f))
                AgeBlock(value = period.days, label = "Days", accentColor = themeTargetColor.copy(alpha = 0.7f), modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
            Spacer(modifier = Modifier.height(16.dp))

            // Birthday Countdown & Solar Progress Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Cake,
                        contentDescription = "Next Birthday",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "NEXT SOLAR RETURN",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        letterSpacing = 1.2.sp
                    )
                }
                
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = nextBday.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            if (isBdayToday) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF10B981).copy(alpha = 0.15f),
                                    Color(0xFF3B82F6).copy(alpha = 0.1f)
                                )
                            )
                        )
                        .border(
                            width = 1.dp,
                            color = Color(0xFF10B981).copy(alpha = 0.35f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🥳 CELEBRATION MODE ACTIVE 🥳",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF10B981),
                            letterSpacing = 1.5.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "HAPPY BIRTHDAY TODAY! 🎉",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Wishing you an extraordinary ${period.years}th orbit around the Sun! Enjoy your special day to the fullest.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            lineHeight = 16.sp
                        )
                    }
                }
            } else {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        MiniCountdownCell(value = daysRemaining.toString(), label = "Days", modifier = Modifier.weight(1f))
                        MiniCountdownCell(value = String.format("%02d", hoursRemaining), label = "Hrs", modifier = Modifier.weight(1f))
                        MiniCountdownCell(value = String.format("%02d", minutesRemaining), label = "Mins", modifier = Modifier.weight(1f))
                        MiniCountdownCell(value = String.format("%02d", secondsRemaining), label = "Secs", modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Orbit Progress: $progressPercent%",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "$nextBdayDayOfWeek Anniversary 🎂",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.06f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(progressFraction)
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(
                                            themeTargetColor,
                                            MaterialTheme.colorScheme.primary
                                        )
                                    )
                                )
                        )
                    }
                }
            }
        }
    }
}

fun getWesternZodiac(dob: LocalDate): String {
    val month = dob.monthValue
    val day = dob.dayOfMonth
    return when (month) {
        1 -> if (day <= 19) "Capricorn ♑" else "Aquarius ♒"
        2 -> if (day <= 18) "Aquarius ♒" else "Pisces ♓"
        3 -> if (day <= 20) "Pisces ♓" else "Aries ♈"
        4 -> if (day <= 19) "Aries ♈" else "Taurus ♉"
        5 -> if (day <= 20) "Taurus ♉" else "Gemini ♊"
        6 -> if (day <= 20) "Gemini ♊" else "Cancer ♋"
        7 -> if (day <= 22) "Cancer ♋" else "Leo ♌"
        8 -> if (day <= 22) "Leo ♌" else "Virgo ♍"
        9 -> if (day <= 22) "Virgo ♍" else "Libra ♎"
        10 -> if (day <= 22) "Libra ♎" else "Scorpio ♏"
        11 -> if (day <= 21) "Scorpio ♏" else "Sagittarius ♐"
        12 -> if (day <= 21) "Sagittarius ♐" else "Capricorn ♑"
        else -> ""
    }
}

fun getChineseZodiac(year: Int): String {
    return when (year % 12) {
        0 -> "Monkey 🐒"
        1 -> "Rooster 🐓"
        2 -> "Dog 🐕"
        3 -> "Pig 🐖"
        4 -> "Rat 🐀"
        5 -> "Ox 🐂"
        6 -> "Tiger 🐅"
        7 -> "Rabbit 🐇"
        8 -> "Dragon 🐉"
        9 -> "Snake 🐍"
        10 -> "Horse 🐎"
        11 -> "Goat 🐐"
        else -> ""
    }
}

@Composable
fun AnimatedAgeDisplayComponent(years: Int, months: Int, days: Int, modifier: Modifier = Modifier) {
    val alphaAnim = remember(years, months, days) { Animatable(0f) }
    val scaleAnim = remember(years, months, days) { Animatable(1f) }
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    LaunchedEffect(years, months, days) {
        launch {
            alphaAnim.snapTo(0f)
            alphaAnim.animateTo(1f, animationSpec = tween(600))
        }
        launch {
            scaleAnim.snapTo(1f)
            scaleAnim.animateTo(1.3f, animationSpec = tween(150))
            scaleAnim.animateTo(1f, animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            ))
        }
    }

    Column(
        modifier = modifier.alpha(alphaAnim.value),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Age", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp, fontWeight = FontWeight.Medium, textAlign = TextAlign.Start)
            IconButton(onClick = {
                val ageText = "I am $years years, $months months, and $days days old!"
                clipboardManager.setText(AnnotatedString(ageText))
                Toast.makeText(context, "Age copied to clipboard!", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Copy Age",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(verticalAlignment = Alignment.Bottom, modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            AnimatedContent(
                targetState = years,
                transitionSpec = {
                    (slideInVertically { height -> height } + fadeIn() togetherWith
                            slideOutVertically { height -> -height } + fadeOut())
                },
                label = "yearsValue"
            ) { targetYears ->
                Text(
                    text = "$targetYears",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.scale(scaleAnim.value)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "years",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 20.sp,
                modifier = Modifier.alignByBaseline().padding(bottom = 12.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        AnimatedContent(
            targetState = "$months months | $days days",
            transitionSpec = {
                (slideInVertically { height -> height / 2 } + fadeIn() togetherWith
                        slideOutVertically { height -> -height / 2 } + fadeOut())
            },
            label = "monthsDaysValue"
        ) { targetText ->
            Text(
                text = targetText,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 15.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
fun SummaryItem(title: String, value: String) {
    Card(
        modifier = Modifier
            .width(96.dp)
            .aspectRatio(0.9f),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(8.dp)
        ) {
            Text(title, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            AnimatedContent(
                targetState = value,
                transitionSpec = {
                    (slideInVertically { height -> height } + fadeIn() togetherWith
                            slideOutVertically { height -> -height } + fadeOut())
                },
                label = "summaryValue"
            ) { targetValue ->
                Text(
                    text = targetValue,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


fun formatRemainingTime(months: Int, days: Int): String {
    val monthStr = if (months == 1) "1 month" else if (months > 1) "$months months" else ""
    val dayStr = if (days == 1) "1 day" else if (days > 1) "$days days" else ""
    
    return when {
        months > 0 && days > 0 -> "$monthStr and $dayStr remaining"
        months > 0 -> "$monthStr remaining"
        days > 0 -> "$dayStr remaining"
        else -> "Today!"
    }
}

class Particle(
    var x: Float,
    var y: Float,
    var vx: Float,
    var vy: Float,
    val color: Color,
    val size: Float,
    var rotation: Float,
    val rotationSpeed: Float
)

@Composable
fun ConfettiEffect(dob: LocalDate, today: LocalDate, isBirthday: Boolean) {
    if (!isBirthday) return
    
    val particles = remember { mutableListOf<Particle>() }
    val colors = listOf(Color(0xFFE91E63), Color(0xFF9C27B0), Color(0xFF2196F3), Color(0xFF4CAF50), Color(0xFFFFEB3B), Color(0xFFFF9800), MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
    var frame by remember { mutableLongStateOf(0L) }

    LaunchedEffect(dob, today) {
        val count = 100 // Subtle count
        particles.clear()
        for (i in 0 until count) {
            particles.add(
                Particle(
                    x = Random.nextFloat(),
                    y = Random.nextFloat() * -0.5f,
                    vx = Random.nextFloat() * 0.01f - 0.005f,
                    vy = Random.nextFloat() * 0.015f + 0.005f, // slower falling
                    color = colors.random(),
                    size = Random.nextFloat() * 20f + 5f, // smaller size
                    rotation = Random.nextFloat() * 360f,
                    rotationSpeed = Random.nextFloat() * 8f - 4f
                )
            )
        }

        var lastFrame = withFrameMillis { it }
        while(true) {
            val currentFrame = withFrameMillis { it }
            val delta = (currentFrame - lastFrame) / 16f
            lastFrame = currentFrame

            var active = false
            particles.forEach { p ->
                p.x += p.vx * delta
                p.y += p.vy * delta
                p.vy += 0.0003f * delta // subtle gravity
                p.rotation += p.rotationSpeed * delta
                if (p.y < 1.5f) active = true
            }
            
            if (Random.nextFloat() < 0.02f) { // Less frequent new particles
                particles.add(
                    Particle(
                        x = Random.nextFloat(),
                        y = -0.1f,
                        vx = Random.nextFloat() * 0.008f - 0.004f,
                        vy = Random.nextFloat() * 0.008f + 0.002f,
                        color = colors.random(),
                        size = Random.nextFloat() * 20f + 5f,
                        rotation = Random.nextFloat() * 360f,
                        rotationSpeed = Random.nextFloat() * 8f - 4f
                    )
                )
                if (particles.size > 150) {
                    particles.removeAt(0)
                }
                active = true
            }

            frame = currentFrame

            if (!active) break
        }
    }

    if (particles.isNotEmpty()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val f = frame // read to trigger recomposition
            val canvasWidth = size.width
            val canvasHeight = size.height

            particles.forEach { p ->
                val actualX = p.x * canvasWidth
                val actualY = p.y * canvasHeight
                rotate(degrees = p.rotation, pivot = Offset(actualX, actualY)) {
                    drawRect(
                        color = p.color,
                        topLeft = Offset(actualX, actualY),
                        size = Size(p.size, p.size)
                    )
                }
            }
        }
    }
}

fun saveBitmapToMediaStoreAndShare(context: android.content.Context, bitmap: android.graphics.Bitmap) {
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, "AgeCalculation_${System.currentTimeMillis()}.png")
        put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
    }

    val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    
    uri?.let { destUri ->
        context.contentResolver.openOutputStream(destUri)?.use { outputStream ->
            bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, outputStream)
        }
        
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, destUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share Image"))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompareAgesDialog(onDismiss: () -> Unit) {
    var dob1 by remember { mutableStateOf(LocalDate.of(1990, 1, 1)) }
    var dob2 by remember { mutableStateOf(LocalDate.of(1995, 1, 1)) }
    var showDob1Picker by remember { mutableStateOf(false) }
    var showDob2Picker by remember { mutableStateOf(false) }

    if (showDob1Picker) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = dob1.toEpochDay() * 24 * 60 * 60 * 1000)
        DatePickerDialog(
            onDismissRequest = { showDob1Picker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        dob1 = LocalDate.ofEpochDay(it / (24 * 60 * 60 * 1000))
                    }
                    showDob1Picker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDob1Picker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showDob2Picker) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = dob2.toEpochDay() * 24 * 60 * 60 * 1000)
        DatePickerDialog(
            onDismissRequest = { showDob2Picker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        dob2 = LocalDate.ofEpochDay(it / (24 * 60 * 60 * 1000))
                    }
                    showDob2Picker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDob2Picker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.CompareArrows, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Compare Ages")
            }
        },
        text = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { showDob1Picker = true }.padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Person 1 DOB", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(dob1.format(DateTimeFormatter.ofPattern("MMM d, yyyy")), fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Pick Date")
                }
                androidx.compose.material3.HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { showDob2Picker = true }.padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Person 2 DOB", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(dob2.format(DateTimeFormatter.ofPattern("MMM d, yyyy")), fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Pick Date")
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                val older = if (dob1.isBefore(dob2)) dob1 else dob2
                val younger = if (dob1.isBefore(dob2)) dob2 else dob1
                val period = Period.between(older, younger)
                
                Card(
                    modifier = Modifier.fillMaxWidth().animateContentSize(animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Age Gap", color = MaterialTheme.colorScheme.onSecondaryContainer, fontSize = 14.sp)
                        AnimatedContent(
                            targetState = "${period.years} years, ${period.months} months, ${period.days} days",
                            transitionSpec = {
                                (slideInVertically { height -> height / 2 } + fadeIn() togetherWith
                                        slideOutVertically { height -> -height / 2 } + fadeOut())
                            },
                            label = "ageGapValue"
                        ) { targetText ->
                            Text(
                                text = targetText,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        AnimatedContent(
                            targetState = if (dob1.isBefore(dob2)) "Person 1 is older" else if (dob2.isBefore(dob1)) "Person 2 is older" else "They are the exact same age!",
                            transitionSpec = {
                                fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
                            },
                            label = "ageGapComparison"
                        ) { targetComparison ->
                            Text(
                                text = targetComparison,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Close") }
        }
    )
}

@Composable
fun CinematicSplashLoaderScreen(
    selectedColor: Color,
    isDarkTheme: Boolean,
    onLoadingComplete: () -> Unit
) {
    val progress = remember { Animatable(0f) }
    
    // Simulate loading with variable speeds
    LaunchedEffect(Unit) {
        // Quick burst to 15%
        progress.animateTo(0.15f, animationSpec = tween(500, easing = FastOutSlowInEasing))
        delay(150)
        // Steady calibration to 50%
        progress.animateTo(0.50f, animationSpec = tween(800, easing = LinearEasing))
        delay(100)
        // High density clock indexing to 85%
        progress.animateTo(0.85f, animationSpec = tween(900, easing = FastOutSlowInEasing))
        delay(150)
        // Finalize state to 100%
        progress.animateTo(1.00f, animationSpec = tween(400, easing = LinearEasing))
        delay(250) // short buffer at 100%
        onLoadingComplete()
    }

    val infiniteTransition = rememberInfiniteTransition(label = "SplashOrbit")
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "OrbitRotation"
    )

    val counterRotationAngle by infiniteTransition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(6500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "CounterRotation"
    )

    val scalePulse by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "LogoPulse"
    )

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.15f,
        targetValue = 0.40f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "GlowingBreathing"
    )

    val backgroundBgColor = if (isDarkTheme) Color(0xFF090C16) else Color(0xFFF1F5F9)
    val textPrimaryColor = if (isDarkTheme) Color.White else Color(0xFF0F172A)
    val timelineColor = selectedColor

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBgColor),
        contentAlignment = Alignment.Center
    ) {
        // Decorative space dust coordinates
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(300.dp)
                .clip(androidx.compose.foundation.shape.CircleShape)
                .background(timelineColor.copy(alpha = glowAlpha * 0.4f))
                .blur(70.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .widthIn(max = 420.dp)
                .padding(24.dp)
        ) {
            // Elegant pulsing outer gear logo
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .scale(scalePulse),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height
                    val centerX = width / 2
                    val centerY = height / 2
                    val radius = Math.min(width, height) / 2.3f

                    // 1. Draw outer glowing celestial orbit lines
                    rotate(rotationAngle, pivot = Offset(centerX, centerY)) {
                        drawCircle(
                            brush = Brush.sweepGradient(
                                colors = listOf(timelineColor.copy(alpha = 0.1f), timelineColor, timelineColor.copy(alpha = 0.1f)),
                                center = Offset(centerX, centerY)
                            ),
                            radius = radius,
                            style = Stroke(width = 3.dp.toPx())
                        )
                        
                        // Draw dial ticks representing the chronometer
                        val numTicks = 12
                        for (i in 0 until numTicks) {
                            val angleRad = (i * (360f / numTicks) * (Math.PI / 180f)).toFloat()
                            val startX = centerX + (radius - 8.dp.toPx()) * Math.cos(angleRad.toDouble()).toFloat()
                            val startY = centerY + (radius - 8.dp.toPx()) * Math.sin(angleRad.toDouble()).toFloat()
                            val endX = centerX + (radius + 2.dp.toPx()) * Math.cos(angleRad.toDouble()).toFloat()
                            val endY = centerY + (radius + 2.dp.toPx()) * Math.sin(angleRad.toDouble()).toFloat()
                            
                            drawLine(
                                color = timelineColor.copy(alpha = 0.5f),
                                start = Offset(startX, startY),
                                end = Offset(endX, endY),
                                strokeWidth = 2.dp.toPx()
                            )
                        }
                    }

                    // 2. An opposite-rotating secondary ring for high tech depth
                    rotate(counterRotationAngle, pivot = Offset(centerX, centerY)) {
                        drawCircle(
                            color = timelineColor.copy(alpha = 0.18f),
                            radius = radius - 15.dp.toPx(),
                            style = Stroke(
                                width = 1.dp.toPx(),
                                pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                                    floatArrayOf(12.dp.toPx(), 8.dp.toPx()), 0f
                                )
                            )
                        )
                    }

                    // 3. Draw a modern continuous curves hourglass outline inside
                    val hgWidth = 22.dp.toPx()
                    val hgHeight = 30.dp.toPx()
                    
                    val glassPath = Path().apply {
                        moveTo(centerX - hgWidth, centerY - hgHeight)
                        lineTo(centerX + hgWidth, centerY - hgHeight)
                        quadraticTo(centerX + 6.dp.toPx(), centerY, centerX + 1.dp.toPx(), centerY)
                        quadraticTo(centerX + 6.dp.toPx(), centerY, centerX + hgWidth, centerY + hgHeight)
                        lineTo(centerX - hgWidth, centerY + hgHeight)
                        quadraticTo(centerX - 6.dp.toPx(), centerY, centerX - 1.dp.toPx(), centerY)
                        quadraticTo(centerX - 6.dp.toPx(), centerY, centerX - hgWidth, centerY - hgHeight)
                        close()
                    }

                    // Hourglass glass vessel filled background
                    drawPath(
                        path = glassPath,
                        color = backgroundBgColor.copy(alpha = 0.85f)
                    )

                    // Draw hourglass edge stroke
                    drawPath(
                        path = glassPath,
                        brush = Brush.linearGradient(
                            colors = listOf(timelineColor, timelineColor.copy(alpha = 0.3f), timelineColor),
                            start = Offset(centerX - hgWidth, centerY - hgHeight),
                            end = Offset(centerX + hgWidth, centerY + hgHeight)
                        ),
                        style = Stroke(width = 2.5.dp.toPx())
                    )

                    // 4. Animate the glowing physical sand particles passing through the vortex
                    val currentProgress = progress.value

                    // Top sand volume (empties gradually)
                    val topSandPath = Path().apply {
                        moveTo(centerX - hgWidth + 2.dp.toPx(), centerY - hgHeight + 2.dp.toPx())
                        lineTo(centerX + hgWidth - 2.dp.toPx(), centerY - hgHeight + 2.dp.toPx())
                        // Curving line moving down as sand drains
                        val level = -hgHeight + (hgHeight * currentProgress * 0.9f)
                        quadraticTo(centerX + 4.dp.toPx() * (1f - currentProgress), level, centerX, level)
                        quadraticTo(centerX - 4.dp.toPx() * (1f - currentProgress), level, centerX - hgWidth + 2.dp.toPx(), centerY - hgHeight + 2.dp.toPx())
                        close()
                    }

                    // Fill draining top sand
                    if (currentProgress < 0.95f) {
                        drawPath(
                            path = topSandPath,
                            color = timelineColor.copy(alpha = 0.85f * (1f - currentProgress))
                        )
                    }

                    // Bottom sand volume (fills gradually)
                    val bottomSandPath = Path().apply {
                        moveTo(centerX - hgWidth + 2.dp.toPx(), centerY + hgHeight - 2.dp.toPx())
                        lineTo(centerX + hgWidth - 2.dp.toPx(), centerY + hgHeight - 2.dp.toPx())
                        // Level rises over progress
                        val level = hgHeight - (hgHeight * currentProgress * 0.95f)
                        quadraticTo(centerX + 12.dp.toPx() * currentProgress, level, centerX, level)
                        quadraticTo(centerX - 12.dp.toPx() * currentProgress, level, centerX - hgWidth + 2.dp.toPx(), centerY + hgHeight - 2.dp.toPx())
                        close()
                    }

                    // Fill accumulating bottom sand
                    if (currentProgress > 0.05f) {
                        drawPath(
                            path = bottomSandPath,
                            color = timelineColor.copy(alpha = 0.9f)
                        )
                    }

                    // Cascade beam of falling light sand grains in-between
                    if (currentProgress in 0.02f..0.98f) {
                        drawLine(
                            color = timelineColor,
                            start = Offset(centerX, centerY - 6.dp.toPx()),
                            end = Offset(centerX, centerY + hgHeight - 5.dp.toPx()),
                            strokeWidth = 2.dp.toPx(),
                            pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                                floatArrayOf(4.dp.toPx(), 4.dp.toPx()), (rotationAngle * 0.5f) % 8.dp.toPx()
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Tech Header
            Text(
                text = "CHRONOS CORE",
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = timelineColor,
                letterSpacing = 2.5.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Temporal Positioning Engine v3.2",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = textPrimaryColor.copy(alpha = 0.45f),
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Gorgeous sleek progress slider dock
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(textPrimaryColor.copy(alpha = 0.08f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(progress.value)
                        .clip(RoundedCornerShape(3.dp))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(timelineColor.copy(alpha = 0.6f), timelineColor)
                            )
                        )
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Progressive Status Loading message row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Changing animated tech calibrating text message
                Text(
                    text = when {
                        progress.value < 0.20f -> "INITIALIZING TEMPORAL CORE..."
                        progress.value < 0.50f -> "CALIBRATING CHRONOMETER ATOMS..."
                        progress.value < 0.85f -> "LINKING HISTORICAL RECURSIVE LOGS..."
                        progress.value < 0.98f -> "SYNCHRONIZING SECONDS GRID..."
                        else -> "TEMPORAL CONSOLE ACTIVE"
                    },
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = textPrimaryColor.copy(alpha = 0.6f)
                )

                // Current Percentage value
                Text(
                    text = "${(progress.value * 100).toInt()}%",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Black,
                    color = timelineColor
                )
            }
        }
    }
}

// Appearance Custom Composables matching the premium visual design format
@Composable
fun WavyUnderline(color: Color, modifier: Modifier = Modifier) {
    androidx.compose.foundation.Canvas(modifier = modifier) {
        val path = Path()
        val width = size.width
        val height = size.height
        val amplitude = height / 3.5f
        val wavelength = 6.dp.toPx()
        path.moveTo(0f, height / 2f)
        var x = 0f
        while (x < width) {
            val y = (height / 2f) + amplitude * kotlin.math.sin((x / wavelength) * 2f * kotlin.math.PI.toFloat())
            path.lineTo(x, y)
            x += 1.dp.toPx()
        }
        drawPath(
            path = path,
            color = color,
            style = Stroke(width = 2.dp.toPx(), cap = androidx.compose.ui.graphics.StrokeCap.Round)
        )
    }
}

@Composable
fun SectionHeader(title: String, colorAccent: Color) {
    Column(modifier = Modifier.padding(bottom = 6.dp)) {
        Text(
            text = title,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface,
            letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        WavyUnderline(
            color = colorAccent,
            modifier = Modifier
                .width(42.dp)
                .height(4.dp)
        )
    }
}

@Composable
fun ModePreviewCard(
    themeMode: Int, // 0 = Light, 1 = Dark, 2 = System split
    isSelected: Boolean,
    onClick: () -> Unit,
    label: String,
    accentColor: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(96.dp)
    ) {
        val intSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
        val isPressed by intSource.collectIsPressedAsState()
        val scale by animateFloatAsState(targetValue = if (isPressed) 0.95f else 1.0f, label = "modeCardPress")
        
        Box(
            modifier = Modifier
                .size(width = 96.dp, height = 110.dp)
                .scale(scale)
                .clip(RoundedCornerShape(20.dp))
                .clickable(
                    interactionSource = intSource,
                    indication = androidx.compose.foundation.LocalIndication.current,
                    onClick = onClick
                )
                .then(
                    if (isSelected) Modifier.border(
                        2.5.dp,
                        accentColor,
                        RoundedCornerShape(20.dp)
                    ) else Modifier.border(
                        1.dp,
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                        RoundedCornerShape(20.dp)
                    )
                )
        ) {
            // Preview Mockup Background
            when (themeMode) {
                0 -> { // Light
                    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF8FAFC))) {
                        ModeMockupInternal(isDark = false, accentColor = accentColor)
                    }
                }
                1 -> { // Dark
                    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0F172A))) {
                        ModeMockupInternal(isDark = true, accentColor = accentColor)
                    }
                }
                2 -> { // System split
                    Row(modifier = Modifier.fillMaxSize()) {
                        Box(modifier = Modifier.weight(1f).fillMaxHeight().background(Color(0xFFF8FAFC))) {
                            ModeMockupInternal(isDark = false, accentColor = accentColor, isSplitLeft = true)
                        }
                        Box(modifier = Modifier.weight(1f).fillMaxHeight().background(Color(0xFF0F172A))) {
                            ModeMockupInternal(isDark = true, accentColor = accentColor, isSplitRight = true)
                        }
                    }
                }
            }
            
            // Selected checkmark badge in corner
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .size(20.dp)
                        .clip(androidx.compose.foundation.shape.CircleShape)
                        .background(accentColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
            color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ModeMockupInternal(
    isDark: Boolean,
    accentColor: Color,
    isSplitLeft: Boolean = false,
    isSplitRight: Boolean = false
) {
    val bgLineColor = if (isDark) Color(0xFF334155) else Color(0xFFE2E8F0)
    val textLineColor1 = if (isDark) Color(0xFF475569) else Color(0xFFCBD5E1)
    val textLineColor2 = if (isDark) Color(0xFF1E293B) else Color(0xFFF1F5F9)
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Mock Up Screen Top Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxWidth().height(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(accentColor)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(bgLineColor)
            )
        }
        
        // Mock List Content Lines
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(3.dp)
                    .clip(RoundedCornerShape(1.5.dp))
                    .background(textLineColor1)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(3.dp)
                    .clip(RoundedCornerShape(1.5.dp))
                    .background(textLineColor1)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(3.dp)
                    .clip(RoundedCornerShape(1.5.dp))
                    .background(textLineColor1)
            )
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Mock Bottom Element Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(textLineColor2)
                .padding(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(androidx.compose.foundation.shape.CircleShape)
                        .background(accentColor.copy(alpha = 0.8f))
                )
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Box(
                        modifier = Modifier
                            .width(18.dp)
                            .height(2.dp)
                            .clip(RoundedCornerShape(1.5.dp))
                            .background(textLineColor1)
                    )
                    Box(
                        modifier = Modifier
                            .width(10.dp)
                            .height(2.dp)
                            .clip(RoundedCornerShape(1.5.dp))
                            .background(textLineColor1)
                    )
                }
            }
        }
    }
}

@Composable
fun PaletteCardView(
    palette: PaletteDesignItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        val intSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
        val isPressed by intSource.collectIsPressedAsState()
        val scale by animateFloatAsState(targetValue = if (isPressed) 0.95f else 1.0f, label = "palettePress")
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .scale(scale)
                .clickable(
                    interactionSource = intSource,
                    indication = androidx.compose.foundation.LocalIndication.current,
                    onClick = onClick
                )
                .then(
                    if (isSelected) Modifier.border(
                        2.5.dp,
                        palette.primaryColor,
                        RoundedCornerShape(16.dp)
                    ) else Modifier.border(
                        1.dp,
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                        RoundedCornerShape(16.dp)
                    )
                ),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Top accent bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(4.2f)
                        .background(palette.barColor)
                )
                
                // Bottom content
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(5.8f)
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        .padding(6.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Mock lines
                        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .height(2.5.dp)
                                    .clip(RoundedCornerShape(1.dp))
                                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f))
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .height(2.5.dp)
                                    .clip(RoundedCornerShape(1.dp))
                                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f))
                            )
                        }
                        
                        // Dots
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(androidx.compose.foundation.shape.CircleShape)
                                    .background(palette.primaryColor)
                            )
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(androidx.compose.foundation.shape.CircleShape)
                                    .background(palette.secondaryColor)
                            )
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(6.dp))
        
        Text(
            text = palette.name,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
            color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}