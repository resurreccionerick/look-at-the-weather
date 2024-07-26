package com.example.look_at_the_weather


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.look_at_the_weather.network.model.WeatherResponseModel
import com.example.look_at_the_weather.viewmodel.ResponseCallback
import com.example.look_at_the_weather.viewmodel.WeatherViewModel

@Composable
fun WeatherPage(weatherViewModel: WeatherViewModel) {
    var inputCity by rememberSaveable { mutableStateOf("") }
    val weatherResult by weatherViewModel.weatherResultLiveData.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 50.dp, 8.dp, 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        SearchBar(inputCity) { city ->
            inputCity = city
            weatherViewModel.getWeatherData(city)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        when (val result = weatherResult) {
            is ResponseCallback.Error -> Text(text = result.msg)
            is ResponseCallback.Success -> CityDetails(result.data)
            is ResponseCallback.Loading -> CircularProgressIndicator()
            null -> {}
        }
    }
}

@Composable
fun SearchBar(inputCity: String, onSearch: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = inputCity,
            onValueChange = onSearch,
            label = { Text(text = "Input city name") }
        )
    }
}

@Composable
fun CityDetails(data: WeatherResponseModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LocationRow(data.location.name, data.location.country)
        Spacer(modifier = Modifier.height(16.dp))
        WeatherCondition(data.current.condition.icon, data.current.condition.text)
        Spacer(modifier = Modifier.height(26.dp))
        TemperatureRow(data.current.temp_c, data.current.temp_f)
        AdditionalInfo(data.current.wind_kph, data.current.humidity, data.current.last_updated)
    }
}

@Composable
fun LocationRow(cityName: String, countryName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "location icon",
            modifier = Modifier.size(50.dp),
            tint = Color.Blue
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$cityName, $countryName",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun TemperatureRow(tempC: Double, tempF: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "$tempC°C",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(50.dp))
            Text(
                text = "$tempF°F",
                fontSize = 30.sp
            )
        }
    }
}


@Composable
fun WeatherCondition(iconUrl: String, conditionText: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier.size(120.dp),
            model = "https:$iconUrl".replace("64x64", "128x128"),
            contentDescription = "condition icon"
        )
        Text(
            text = conditionText,
            fontSize = 30.sp,
            fontWeight = FontWeight.Thin
        )
    }
}

@Composable
fun AdditionalInfo(windKph: Double, humidity: Int, lastUpdated: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            InfoRow(label = "Wind KPH", value = windKph.toString())
            InfoRow(label = "Humidity", value = humidity.toString())
            InfoRow(label = "Last Updated", value = lastUpdated)
        }
    }
}


@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = "$label: $value",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    }

}