package bu.ac.kr.search_map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bu.ac.kr.search_map.databinding.ActivityMainBinding
import bu.ac.kr.search_map.databinding.ActivityMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapBinding
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupGoogleMap()
    }
    private fun setupGoogleMap(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {

    }

}





