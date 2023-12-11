package com.example.bankapp
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
// import your.package.name.R

class MapsActivity : FragmentActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val loginButton = findViewById<Button>(R.id.exitButton)

        loginButton.setOnClickListener {
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap = googleMap

        val atms = listOf(
            LatLng(38.9806514,-76.9381931),
            LatLng(38.9836675,-76.9436574),
            LatLng(38.9881645,-76.9447202),
            LatLng(38.9874561,-76.9490001)
        )

        for (atm in atms) {
            mMap.addMarker(MarkerOptions().position(atm).title("ATM at UMD"))
        }

        val umdCampus = LatLng(38.9869, -76.9426)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(umdCampus, 15f))
    }
}
