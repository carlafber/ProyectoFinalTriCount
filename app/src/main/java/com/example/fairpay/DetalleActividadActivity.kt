package com.example.fairpay

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.fairpay.databinding.ActivityDetalleActividadBinding
import com.example.fairpay.fragmentos.FragmentoGastos
import com.example.fairpay.fragmentos.FragmentoSaldos
import com.example.fairpay.modelos.Actividad
import com.example.fairpay.proveedores.ActividadProveedor
import com.google.android.material.bottomnavigation.BottomNavigationView

class DetalleActividadActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    lateinit var binding: ActivityDetalleActividadBinding

    private var nombreActividad: String? = null
    private var actividadSeleccionada: Actividad? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetalleActividadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bnv: BottomNavigationView = binding.bnv

        // Inicializa las vistas
        toolbar = findViewById(R.id.toolbarMain)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Recupera el nombre de la actividad desde el Intent
        nombreActividad = intent.getStringExtra("actividad_nombre")

        // Cargar los datos de la actividad
        cargarActividad()

        // Manejador de la selección en el BottomNavigationView
        bnv.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment? = when (item.itemId) {
                R.id.gastos -> {
                    actividadSeleccionada?.let {
                        FragmentoGastos.nuevaInstancia(it)
                    }
                }
                R.id.saldos -> {
                    actividadSeleccionada?.let {
                        FragmentoSaldos.nuevaInstancia(it)
                    }
                }
                else -> null
            }

            selectedFragment?.let {
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, it).commit()
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun cargarActividad() {
        actividadSeleccionada = ActividadProveedor.listaActividades.find { it.nombre == nombreActividad }

        actividadSeleccionada?.participantes?.forEach { it.balance = 0.0 }

        for (gasto in actividadSeleccionada?.gastos!!) {
            gasto.distribuirGasto(actividadSeleccionada!!)  // Distribuye el gasto y actualiza los balances
            actividadSeleccionada!!.participantes.forEach {
                Log.i("Balance", "Participante: ${it.nombre}, Balance: ${it.balance}")
            }
        }

        actividadSeleccionada?.let {
            toolbar.title = it.nombre

            // Iniciar el primer fragmento
            if (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, FragmentoGastos.nuevaInstancia(it))
                    .commit()
            }
        }
    }
}
