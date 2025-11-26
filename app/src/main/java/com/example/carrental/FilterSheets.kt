package com.example.carrental

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carrental.ui.theme.PurpleGrey40

// =======================================================
//  FIXED ENUM — must be top-level
// =======================================================
enum class FilterSheet {
    MAKE_MODEL,
    PRICE,
    YEAR,
    SEATS,
    ELECTRIC,
    ALL
}

@Composable
fun SheetHeader(title: String, onClose: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "✕",
            modifier = Modifier
                .clickable { onClose() }
                .padding(16.dp),
            fontSize = 18.sp
        )
        Spacer(Modifier.weight(1f))
        Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.weight(1f))
        Spacer(Modifier.width(32.dp))
    }
}

@Composable
fun MakeModelSheet(
    cars: List<Car>,
    selectedMakes: Set<String>,
    onApply: (Set<String>) -> Unit,
    onClose: () -> Unit
) {
    var search by remember { mutableStateOf("") }
    var tempSelectedMakes by remember { mutableStateOf(selectedMakes) }

    val allMakes = cars
        .map { it.name.substringBefore(" ").trim() }
        .distinct()
        .sorted()

    val filteredMakes =
        if (search.isBlank()) allMakes
        else allMakes.filter { it.contains(search, ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {

        SheetHeader("Make & model", onClose)

        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            placeholder = { Text("Search make") }
        )

        Text("All makes", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(filteredMakes) { make ->
                val normalizedMake = make.trim()

                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            tempSelectedMakes =
                                if (tempSelectedMakes.contains(normalizedMake))
                                    tempSelectedMakes - normalizedMake
                                else tempSelectedMakes + normalizedMake
                        }
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = tempSelectedMakes.contains(normalizedMake),
                        onCheckedChange = {
                            tempSelectedMakes =
                                if (tempSelectedMakes.contains(normalizedMake))
                                    tempSelectedMakes - normalizedMake
                                else tempSelectedMakes + normalizedMake
                        }
                    )
                    Text(normalizedMake, fontSize = 16.sp)
                }
                HorizontalDivider()
            }
        }

        Button(
            onClick = { onApply(tempSelectedMakes) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            Text("Show cars")
        }
    }
}

@Composable
fun PriceSheet(
    currentRange: ClosedFloatingPointRange<Float>,
    onApply: (ClosedFloatingPointRange<Float>) -> Unit,
    onClose: () -> Unit
) {
    var tempRange by remember { mutableStateOf(currentRange) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .padding(16.dp)
    ) {
        SheetHeader("Price", onClose)

        Text(
            "$${tempRange.start.toInt()} – $${tempRange.endInclusive.toInt()}/day",
            fontSize = 16.sp
        )

        RangeSlider(
            value = tempRange,
            onValueChange = { tempRange = it },
            valueRange = 0f..200f
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { onApply(tempRange) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Show cars")
        }
    }
}

@Composable
fun YearSheet(
    currentMinYear: Int,
    onApply: (Int) -> Unit,
    onClose: () -> Unit
) {
    var tempYear by remember { mutableStateOf(currentMinYear) }
    val years = listOf(2018, 2019, 2020, 2021, 2022, 2023)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .padding(16.dp)
    ) {

        SheetHeader("Years", onClose)

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(years) { year ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { tempYear = year }
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = tempYear == year,
                        onClick = { tempYear = year }
                    )
                    Text(year.toString(), fontSize = 16.sp)
                }
                HorizontalDivider()
            }
        }

        Button(
            onClick = { onApply(tempYear) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Show cars")
        }
    }
}

@Composable
fun SeatsSheet(
    currentMinSeats: Int?,
    onApply: (Int?) -> Unit,
    onClose: () -> Unit
) {
    var tempSeats by remember { mutableStateOf(currentMinSeats) }
    val seatOptions = listOf(2, 4, 5, 7)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .padding(16.dp)
    ) {
        SheetHeader("Seats", onClose)

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(seatOptions) { seat ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            tempSeats = if (tempSeats == seat) null else seat
                        }
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = tempSeats == seat,
                        onCheckedChange = {
                            tempSeats = if (tempSeats == seat) null else seat
                        }
                    )
                    Text("$seat seats", fontSize = 16.sp)
                }
                HorizontalDivider()
            }
        }

        Button(
            onClick = { onApply(tempSeats) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Show cars")
        }
    }
}

@Composable
fun ElectricSheet(
    electricOnly: Boolean,
    onApply: (Boolean) -> Unit,
    onClose: () -> Unit
) {
    var tempElectricOnly by remember { mutableStateOf(electricOnly) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
            .padding(16.dp)
    ) {
        SheetHeader("Electric", onClose)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Switch(
                checked = tempElectricOnly,
                onCheckedChange = { tempElectricOnly = it }
            )
            Spacer(Modifier.width(12.dp))
            Text("Electric only", fontSize = 16.sp)
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { onApply(tempElectricOnly) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Show cars")
        }
    }
}

@Composable
fun AllFiltersSheet(
    selectedVehicleType: VehicleType?,
    onVehicleTypeChange: (VehicleType?) -> Unit,
    onReset: () -> Unit,
    onClose: () -> Unit
) {
    var tempType by remember { mutableStateOf(selectedVehicleType) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .padding(16.dp)
    ) {

        SheetHeader("All filters", onClose)

        Text("Vehicle type", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(Modifier.height(8.dp))

        Row {
            VehicleType.values().forEach { type ->
                AssistChip(
                    onClick = {
                        tempType = if (tempType == type) null else type
                    },
                    label = { Text(type.name) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor =
                        if (tempType == type) PurpleGrey40 else Color(0xFFEFEFEF)
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                onVehicleTypeChange(tempType)
                onClose()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Apply")
        }

        TextButton(
            onClick = onReset,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reset all filters")
        }
    }
}
