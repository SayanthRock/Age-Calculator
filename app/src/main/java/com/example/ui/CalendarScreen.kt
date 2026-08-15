package com.example.ui

import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.AgeViewModel
import com.example.getChineseZodiac
import com.example.getWesternZodiac
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Period
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: AgeViewModel,
    isDarkTheme: Boolean,
    selectedColor: Color
) {
    val context = LocalContext.current
    var currentYearMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var userDob by remember { mutableStateOf(viewModel.savedDob) }

    var calendarBgUriString by remember { mutableStateOf(viewModel.savedBackgroundImageUri) }
    var isBlurEnabled by remember { mutableStateOf(viewModel.isWallpaperBlurEnabled) }
    var blurRadius by remember { mutableStateOf(viewModel.wallpaperBlurRadius) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                calendarBgUriString = it.toString()
                viewModel.savedBackgroundImageUri = it.toString()
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter
    ) {
        // Background Image layer if present
        if (calendarBgUriString != null) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = Uri.parse(calendarBgUriString),
                    contentDescription = "Calendar Background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .then(if (isBlurEnabled) Modifier.blur(blurRadius.dp) else Modifier)
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            if (isDarkTheme) Color(0xCC0B0F19) else Color(0xBFE2E8F0)
                        )
                )
            }
        }

        Column(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            // Header Capsule Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
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
                        .padding(horizontal = 18.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(selectedColor)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                "APPLICATION CALENDAR",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = selectedColor,
                                letterSpacing = 1.2.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            "Chronos Calendar",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Background & Reset actions
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Wallpaper,
                                contentDescription = "Pick Calendar Background",
                                tint = selectedColor,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        if (calendarBgUriString != null) {
                            IconButton(
                                onClick = {
                                    calendarBgUriString = null
                                    viewModel.savedBackgroundImageUri = null
                                },
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.HideImage,
                                    contentDescription = "Clear Background",
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        IconButton(
                            onClick = {
                                currentYearMonth = YearMonth.now()
                                selectedDate = LocalDate.now()
                            },
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Today,
                                contentDescription = "Today",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            // Month Navigation Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)),
                border = androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { currentYearMonth = currentYearMonth.minusMonths(1) },
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChevronLeft,
                            contentDescription = "Previous Month",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Text(
                        text = "${currentYearMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${currentYearMonth.year}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    IconButton(
                        onClick = { currentYearMonth = currentYearMonth.plusMonths(1) },
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Next Month",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // Calendar Grid Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)),
                border = androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            selectedColor.copy(alpha = 0.3f),
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)
                        )
                    )
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Day of week headers (Sun - Sat)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
                        daysOfWeek.forEach { dayName ->
                            Text(
                                text = dayName,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (dayName == "Sun" || dayName == "Sat") selectedColor else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Month Grid Calculation
                    val firstDayOfMonth = currentYearMonth.atDay(1)
                    val daysInMonth = currentYearMonth.lengthOfMonth()
                    // DayOfWeek: MONDAY(1) ... SUNDAY(7). Convert so Sunday = 0
                    val firstDayOffset = firstDayOfMonth.dayOfWeek.value % 7

                    val totalCells = firstDayOffset + daysInMonth
                    val rows = (totalCells + 6) / 7

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        for (r in 0 until rows) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                for (c in 0 until 7) {
                                    val cellIndex = r * 7 + c
                                    val dayNum = cellIndex - firstDayOffset + 1

                                    if (dayNum in 1..daysInMonth) {
                                        val cellDate = currentYearMonth.atDay(dayNum)
                                        val isToday = cellDate == LocalDate.now()
                                        val isSelected = cellDate == selectedDate
                                        val isUserBirthday = cellDate.monthValue == userDob.monthValue && cellDate.dayOfMonth == userDob.dayOfMonth

                                        CalendarDayCell(
                                            dayNum = dayNum,
                                            isToday = isToday,
                                            isSelected = isSelected,
                                            isBirthday = isUserBirthday,
                                            accentColor = selectedColor,
                                            modifier = Modifier.weight(1f),
                                            onClick = { selectedDate = cellDate }
                                        )
                                    } else {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Selected Date Details Card
            SelectedDateDetailsCard(
                selectedDate = selectedDate,
                userDob = userDob,
                accentColor = selectedColor
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun CalendarDayCell(
    dayNum: Int,
    isToday: Boolean,
    isSelected: Boolean,
    isBirthday: Boolean,
    accentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "cellPress"
    )

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(
                when {
                    isSelected -> accentColor
                    isToday -> accentColor.copy(alpha = 0.25f)
                    else -> Color.Transparent
                }
            )
            .then(
                if (isToday && !isSelected) Modifier.border(1.5.dp, accentColor, CircleShape)
                else Modifier
            )
            .clickable(
                interactionSource = interactionSource,
                indication = androidx.compose.foundation.LocalIndication.current,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "$dayNum",
                fontSize = 13.sp,
                fontWeight = if (isSelected || isToday) FontWeight.Black else FontWeight.SemiBold,
                color = when {
                    isSelected -> Color.White
                    isToday -> accentColor
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )

            if (isBirthday) {
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) Color.White else Color(0xFFFFB74D))
                )
            }
        }
    }
}

@Composable
fun SelectedDateDetailsCard(
    selectedDate: LocalDate,
    userDob: LocalDate,
    accentColor: Color
) {
    val context = LocalContext.current
    val formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"))
    val isBirthday = selectedDate.monthValue == userDob.monthValue && selectedDate.dayOfMonth == userDob.dayOfMonth

    val period = Period.between(userDob, selectedDate)
    val isPastOrToday = !selectedDate.isBefore(userDob)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.45f)),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    accentColor.copy(alpha = 0.4f),
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)
                )
            )
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "SELECTED DATE",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black,
                        color = accentColor,
                        letterSpacing = 1.2.sp
                    )
                    Text(
                        text = formattedDate,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                if (isBirthday) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFFFB74D).copy(alpha = 0.15f))
                            .border(1.dp, Color(0xFFFFB74D).copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = "🎂 BIRTHDAY",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFFFFB74D)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isPastOrToday) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "AGE AT THIS DATE",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${period.years} years, ${period.months} months, ${period.days} days old",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = accentColor
                        )
                    }
                }
            } else {
                Text(
                    text = "This date precedes the birth epoch (${userDob.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))}).",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Zodiac info for selected date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    ZodiacOracleCard("WESTERN", getWesternZodiac(selectedDate), accentColor)
                }
                Box(modifier = Modifier.weight(1f)) {
                    ZodiacOracleCard("CHINESE", getChineseZodiac(selectedDate.year), accentColor)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Add Event / Reminder Button
            OutlinedButton(
                onClick = {
                    val intent = Intent(Intent.ACTION_INSERT).apply {
                        data = CalendarContract.Events.CONTENT_URI
                        putExtra(CalendarContract.Events.TITLE, if (isBirthday) "Birthday" else "Event Note")
                        putExtra(CalendarContract.Events.ALL_DAY, true)
                        val startMillis = selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                        putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
                        putExtra(CalendarContract.EXTRA_EVENT_END_TIME, startMillis + 24 * 60 * 60 * 1000)
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth().height(46.dp),
                shape = RoundedCornerShape(23.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, accentColor.copy(alpha = 0.5f))
            ) {
                Icon(imageVector = Icons.Default.Event, contentDescription = "Add Calendar Event", tint = accentColor, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Calendar Event / Note", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = accentColor)
            }
        }
    }
}
