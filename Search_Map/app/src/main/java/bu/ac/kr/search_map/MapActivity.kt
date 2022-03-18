package bu.ac.kr.search_map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bu.ac.kr.search_map.databinding.ActivityMainBinding
import bu.ac.kr.search_map.databinding.ActivityMapBinding
import bu.ac.kr.search_map.model.SearchResultEntity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapBinding
    private lateinit var map: GoogleMap
    private var currentSelectMarker: Marker?= null

    private lateinit var searchResult: SearchResultEntity

    companion object{
        const val SEARCH_RESULT_EXTRA_KEY = "SEARCH_RESULT_EXTRA_KEY"
        const val CAMERA_ZOOM_LEVEL = 17f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(!::searchResult.isInitialized){
            intent?.let{
                searchResult = it.getParcelableExtra(SEARCH_RESULT_EXTRA_KEY) ?: throw Exception("데이터가 존재하지 않습니다.")
                setupGoogleMap()
            }
        }

        setupGoogleMap()
    }
    private fun setupGoogleMap(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        currentSelectMarker = setUpMarker(searchResult)

        currentSelectMarker?.showInfoWindow()
    }
    private fun setUpMarker(searchResult:SearchResultEntity) : Marker{
        val positionLatLng = LatLng(
            searchResult.locationLatLng.latitude.toDouble(), searchResult.locationLatLng.longitude.toDouble()
        )
        val markerOptions = MarkerOptions().apply {
            position(positionLatLng)
            title(searchResult.name)
            snippet(searchResult.fullAddress)

        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(positionLatLng, CAMERA_ZOOM_LEVEL))

        return map.addMarker(markerOptions)!!
    }
}





