package bu.ac.kr.search_map

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import bu.ac.kr.search_map.MapActivity.Companion.SEARCH_RESULT_EXTRA_KEY
import bu.ac.kr.search_map.databinding.ActivityMainBinding
import bu.ac.kr.search_map.model.LocationLatLngEntity
import bu.ac.kr.search_map.model.SearchResultEntity
import bu.ac.kr.search_map.response.search.Poi
import bu.ac.kr.search_map.response.search.Pois
import bu.ac.kr.search_map.utility.RetrofitUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : SearchRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        job = Job()

        initAdapter()
        initViews()
        initData()
        bindViews()
    }



    private fun initViews() = with(binding){
        emptyResultTextView.isVisible = false
        recyclerView.adapter = adapter
    }
    private fun bindViews()= with(binding){
        searchButton.setOnClickListener {
            searchKeyword(searchBarInputView.text.toString())
        }
    }
    private fun initAdapter() {
        adapter = SearchRecyclerAdapter()
    }
    private fun initData(){
        adapter.notifyDataSetChanged()
    }
    private fun setData(pois: Pois){
        val dataList = (pois.poi).map{
            SearchResultEntity(
                name = it.name ?: "빌딩명 없음",
                fullAddress = makeMainAdress(it),
                locationLatLng= LocationLatLngEntity(
                    it.noorLat,
                    it.noorLon
                )
            )
        }
        adapter.setSearchResult(dataList){
            Toast.makeText(this,"빌딩이름: ${it.name} 주소: ${it.fullAddress} 위도/경도: ${it.locationLatLng}",Toast.LENGTH_SHORT).show()
            startActivity(
                Intent(this, MapActivity::class.java).apply {
                    putExtra(SEARCH_RESULT_EXTRA_KEY,it)
                }
            )
        }
    }
    private fun searchKeyword(keywordString:String){
        launch(coroutineContext) {
            try {
                withContext(Dispatchers.IO){
                    val response = RetrofitUtil.apiService.getSearchLocation(
                        keyword = keywordString
                    )
                    if(response.isSuccessful){
                        val body = response.body()
                        withContext(Dispatchers.Main){
                            Log.e("response",body.toString())
                            body?.let{ searchResponse ->
                                setData(searchResponse.searchPoiInfo.pois)
                            }
                        }
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(this@MainActivity,"검색하는 도중 에러가 발생했습니다. : ${e.message}",Toast.LENGTH_SHORT).show()

                }
            }
        }
    private fun makeMainAdress(poi: Poi): String =
        if (poi.secondNo?.trim().isNullOrEmpty()) {
            (poi.upperAddrName?.trim() ?: "") + " " +
                    (poi.middleAddrName?.trim() ?: "") + " " +
                    (poi.lowerAddrName?.trim() ?: "") + " " +
                    (poi.detailAddrName?.trim() ?: "") + " " +
                    poi.firstNo?.trim()
        } else {
            (poi.upperAddrName?.trim() ?: "") + " " +
                    (poi.middleAddrName?.trim() ?: "") + " " +
                    (poi.lowerAddrName?.trim() ?: "") + " " +
                    (poi.detailAddrName?.trim() ?: "") + " " +
                    (poi.firstNo?.trim() ?: "") + " " +
                    poi.secondNo?.trim()
        }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
    }
