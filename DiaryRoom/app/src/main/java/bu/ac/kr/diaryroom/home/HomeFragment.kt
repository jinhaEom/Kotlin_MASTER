package bu.ac.kr.diaryroom.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import bu.ac.kr.diaryroom.*
import bu.ac.kr.diaryroom.base.BaseFragment
import bu.ac.kr.diaryroom.data.WeatherModel
import bu.ac.kr.diaryroom.databinding.FragmentHomeBinding
import bu.ac.kr.diaryroom.diary.data.DiaryDatabase
import bu.ac.kr.diaryroom.viewModel.DiaryViewModel
import bu.ac.kr.diaryroom.viewModel.Factory.DiaryViewModelFactory
import bu.ac.kr.diaryroom.viewModel.WeatherViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val layoutResourceId: Int = R.layout.fragment_home

    private lateinit var viewModel: WeatherViewModel
    private lateinit var diaryViewModel: DiaryViewModel

    @SuppressLint("SetTextI18n")
    override fun aboutBinding() {
        viewDataBinding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        val application = requireNotNull(this.activity).application
        val dataSource = DiaryDatabase.getInstance(application).diaryItemDao()
        val viewModelFactory = DiaryViewModelFactory(dataSource)
        diaryViewModel = ViewModelProvider(this, viewModelFactory).get(DiaryViewModel::class.java)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        viewDataBinding.weatherRecyclerView.layoutManager = layoutManager
        viewModel.setWeather()

        diaryViewModel.todayDiary.observe(viewLifecycleOwner) { diary ->
            if (diary != null) {
                viewDataBinding.adviceTx.text = getString(R.string.already_writting_diary)
            } else {
                viewDataBinding.adviceTx.text = getString(R.string.advice_writting_diary)
            }
        }

    }

    @SuppressLint("SetTextI18n")
    override fun observeData() {
        viewModel.weatherData.observe(viewLifecycleOwner, Observer { weather ->
            if (weather.isNotEmpty()) {
                val weatherArr = arrayOf(
                    WeatherModel(),
                    WeatherModel(),
                    WeatherModel(),
                    WeatherModel(),
                    WeatherModel(),
                    WeatherModel()
                )
                var index = 0

                for (i in weather.indices) {
                    index %= 6

                    when (weather[i].category) {
                        "PTY" -> weatherArr[index].rainType = weather[i].fcstValue     // 강수 형태
                        "REH" -> weatherArr[index].humidity = weather[i].fcstValue     // 습도
                        "SKY" -> weatherArr[index].sky = weather[i].fcstValue          // 하늘 상태
                        "T1H" -> weatherArr[index].temp = weather[i].fcstValue              // 기온
                        else -> continue
                    }
                    index++
                }

                for (i in 0..5) weatherArr[i].fcstTime = weather[i].fcstTime


                var weatherList = weatherArr.toList()
                weatherList = weatherList.drop(1) // 첫 번째 index 제거(현재 날씨는 상단에 보이기 때문)
                viewDataBinding.weatherRecyclerView.adapter = WeatherAdapter(weatherList.toTypedArray())

                // 가장 최근의 날씨 데이터
                val recentWeather = weatherArr[0]
                viewDataBinding.nowTvTemp.text = "${recentWeather.temp}°"
                viewDataBinding.nowTvSky.text = recentWeather.sky
                viewDataBinding.nowTvHumidity.text = "${recentWeather.humidity}%"

                val ootdTemp : Int = recentWeather.temp.toInt()
                when {
                    ootdTemp < -20 -> {
                        // -20도 미만
                        viewDataBinding.clothImg1.setImageResource(R.drawable.ic_longpadding)
                        viewDataBinding.clothImg2.setImageResource(R.drawable.ic_scarf)
                    }
                    ootdTemp in -20 until 0 -> {
                        // -20도 이상, 0도 미만
                        viewDataBinding.clothImg1.setImageResource(R.drawable.ic_shortpadding)
                        viewDataBinding.clothImg2.setImageResource(R.drawable.ic_earplug)

                    }
                    ootdTemp in 0 until 20 -> {
                        // 0도 이상, 20도 미만
                        viewDataBinding.clothImg1.setImageResource(R.drawable.ic_jacket)
                        viewDataBinding.clothImg2.setImageResource(R.drawable.ic_tshirts)
                    }
                    else -> {
                        // 20도 이상
                        viewDataBinding.clothImg1.setImageResource(R.drawable.ic_tshirts)
                        viewDataBinding.clothImg2.setImageResource(R.drawable.ic_shortpants)

                    }
                }



                when (recentWeather.sky) {
                    "1" -> {
                        viewDataBinding.nowTvSky.text = requireContext().getString(R.string.sun)
                        viewDataBinding.nowWeatherIv.setImageResource(R.drawable.sun)
                    }

                    "3" -> {
                        viewDataBinding.nowTvSky.text = requireContext().getString(R.string.cloudy)
                        viewDataBinding.nowWeatherIv.setImageResource(R.drawable.cloudy)
                    }

                    "4" -> {
                        viewDataBinding.nowTvSky.text = requireContext().getString(R.string.blur)
                        viewDataBinding.nowWeatherIv.setImageResource(R.drawable.blur)
                    }

                    else -> {
                        viewDataBinding.nowTvSky.text = requireContext().getString(R.string.none)
                        viewDataBinding.nowWeatherIv.setImageResource(R.drawable.ic_gps_off)
                    }
                }
            }
            Log.d("HomeFragment", "Updating UI with weather data ${weather}")
        })

//        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
//            Log.e("HomeFragment", "Updating UI with error message $error")
//        })
    }
}
