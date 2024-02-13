package com.example.manishweatherapp

import android.app.DownloadManager.Query
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import com.example.manishweatherapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Objects
import java.util.concurrent.locks.Condition

// Api id : 9662ff93641ba9c2672d9d25905993d0

class MainActivity : AppCompatActivity() {
    private val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fetchWeatherData("surat")
        Searchcity()

    }

    private fun Searchcity() {
        val searchview = binding.searchView
        searchview.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    fetchWeatherData(p0)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })

    }

    private fun fetchWeatherData(cityName:String) {
       val ratrofit = Retrofit.Builder()
           .addConverterFactory(GsonConverterFactory.create())
           .baseUrl("https://api.openweathermap.org/data/2.5/")
           .build().create(Apiintrface::class.java)

        val response = ratrofit.getWeatherData(cityName,"9662ff93641ba9c2672d9d25905993d0","metric")
        response.enqueue(object : Callback<Manishweather>{
            override fun onResponse(call: Call<Manishweather>, response: Response<Manishweather>) {
                val responsebody = response.body()
                if (response.isSuccessful && responsebody!=null)
                {
                    val temperature = responsebody.main.temp.toString()

                    val humidity =responsebody.main.humidity
                    val windspeed= responsebody.wind.speed
                    val sunRise= responsebody.sys.sunrise.toLong()
                    val sunSet= responsebody.sys.sunrise.toLong()
                    val seaLevel = responsebody.main.pressure
                    val condition = responsebody.weather.firstOrNull()?.main?:"unknown"
                    val maxtemp = responsebody.main.temp_max
                    val mintemp= responsebody.main.temp_min

                    binding.temperature.text= "$temperature °C"
                    binding.weather.text= condition
                    binding.maxtemp.text="Max Temp : $maxtemp °C"
                    binding.mintemp.text="Min Temp : $mintemp °C"
                    binding.humidity.text="$humidity %"
                    binding.windSpeed.text="$windspeed m/s"
                    binding.sunrise.text="${time(sunRise)}"
                    binding.sunset.text="${time(sunSet)}"
                    binding.sea.text="$seaLevel hPa"
                    binding.condition.text=condition

                    binding.day.text=dayName(System.currentTimeMillis())
                        binding.date.text=date()
                        binding.cityname.text="$cityName"


                   //  Log.d("TAG", "onResponse: $temperature")

                    changeImagesAccordingToWeatherCondiion(condition)
                }
            }




            override fun onFailure(call: Call<Manishweather>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun changeImagesAccordingToWeatherCondiion(conditions: String) {

        when(conditions)
        {
            "Clear Sky", "Sunny","Clear" ->{
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView.setAnimation(R.raw.sun)
            }
            "Partly Clouds", "Clouds","Overcast","Mist", "Smoke","Foggy" ->{
                binding.root.setBackgroundResource(R.drawable.colud_background)
                binding.lottieAnimationView.setAnimation(R.raw.cloud)
            }
            "Light Rain", "Drizzle","Moderate Rain","Showers", "Heavy Rain" ->{
                binding.root.setBackgroundResource(R.drawable.rain_background)
                binding.lottieAnimationView.setAnimation(R.raw.rain)
            }
            "Light Snow", "Moderate Snow","Heavy Snow","Blizzard" ->{
                binding.root.setBackgroundResource(R.drawable.snow_background)
                binding.lottieAnimationView.setAnimation(R.raw.snow)
            }
            else -> {
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView.setAnimation(R.raw.sun)
            }
        }
        binding.lottieAnimationView.playAnimation()
    }

    private fun date(): String {
        val sdf= SimpleDateFormat("dd-MM", Locale.getDefault())
        return sdf.format((Date()))

    }
    private fun time(timestamp:Long): String {
        val sdf= SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format((Date(timestamp*1000)))

    }


    fun dayName(timestamp:Long):String{
        val sdf= SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format((Date()))

    }
}

