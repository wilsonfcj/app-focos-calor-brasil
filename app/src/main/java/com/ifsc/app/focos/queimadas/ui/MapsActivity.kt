package com.ifsc.app.focos.queimadas.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.edu.ifsc.cancontrol.utilidades.BaseActivty
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.clustering.ClusterManager
import com.ifsc.app.focos.queimadas.R
import com.ifsc.app.focos.queimadas.props.EEstados
import com.ifsc.app.focos.queimadas.resouces.focos.model.FocoRequest
import com.ifsc.app.focos.queimadas.resouces.focos.model.FocoResponse
import com.ifsc.app.focos.queimadas.ui.bottonsheet.EstadosDialogFragment
import com.ifsc.app.focos.queimadas.ui.model.MapsViewModel
import com.ifsc.app.focos.queimadas.ui.model.MapsViewModelFactory
import com.ifsc.app.focos.queimadas.ui.model.MyItem
import com.ifsc.app.focos.queimadas.utilidades.ConnectionUtil
import com.ifsc.app.focos.queimadas.utilidades.DialogUtil
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : BaseActivty(), OnMapReadyCallback, EstadosDialogFragment.Listener {

    private lateinit var mMap: GoogleMap
    private lateinit var clusterManager: ClusterManager<MyItem>
    val brasil = LatLng(-14.235004, -51.92528)
    var estadosDialogFragment : EstadosDialogFragment? = null

    private val viewModel by lazy {
        ViewModelProviders
            .of(this@MapsActivity, MapsViewModelFactory(this@MapsActivity))
            .get(MapsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setDisplayHomeAs(false)

        if(ConnectionUtil.isNetworkAvailable(this@MapsActivity).not()) {
            DialogUtil.showDialogGenericMessageOk(this@MapsActivity, R.drawable.ic_signal_wifi_off,
                "Aviso", "Sem conexão com a internet, tente novamente mais tarde!",
                View.OnClickListener {
                    finish()
                }).show()
        }

        viewModel.focoQuery.observe(this@MapsActivity, Observer { ij ->
            if (ij.data.isNullOrEmpty()) {
                if(ConnectionUtil.isNetworkAvailable(this@MapsActivity)) {
                    DialogUtil.showDialogGenericMessageOk(this@MapsActivity, R.drawable.ic_pin_drop,
                        "Aviso", ij.message,null).show()
                }
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(brasil, 4f))
            } else {
                createClusteringIntes(ij.data)
                Toast.makeText(this@MapsActivity, ij.message, Toast.LENGTH_LONG).show()
            }
            hideLoading()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_filter, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.config_filter -> {
                estadosDialogFragment = EstadosDialogFragment.newInstance()
                estadosDialogFragment?.show(supportFragmentManager, "Estados")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.moveCamera(CameraUpdateFactory.newLatLng(brasil))
        clusterManager = ClusterManager(this@MapsActivity, mMap)

        mMap.setOnCameraIdleListener(clusterManager)
        mMap.setOnMarkerClickListener(clusterManager)
        requestFocos(null)
    }

    fun createClusteringIntes(it : MutableList<FocoResponse.Result>) {
        val builder = LatLngBounds.Builder()
        for (focos in it) {
            val foco = LatLng(focos.properties!!.latitude!!, focos.properties!!.longitude!!)
            builder.include(foco)
            val offsetItem = MyItem(foco, "Estado ${focos.properties!!.estado}", "Município ${focos.properties!!.municipio}")
            clusterManager.addItem(offsetItem)
        }
        val bounds = builder.build()
        val padding = 10 // offset from edges of the map in pixels
        val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        mMap.animateCamera(cu)
    }

    override fun onItemClicked(eEstados: EEstados) {
        estadosDialogFragment?.dismiss()
        requestFocos(eEstados.estadoId)
    }

    fun requestFocos(estadoId : Long?) {
        showLoading("Buscando informações")
        mMap.clear()
        clusterManager.clearItems()
        var request = FocoRequest.Query(paisId = 33, estadoId = estadoId, satelite = "AQUA_M-T")
        viewModel.consultarFocosQueimadas(request)
    }
}
