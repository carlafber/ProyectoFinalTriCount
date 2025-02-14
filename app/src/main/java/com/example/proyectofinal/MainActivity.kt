package com.example.proyectofinal


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.adaptadores.ActividadAdaptador
import com.example.proyectofinal.modelos.Actividad
import com.example.proyectofinal.modelos.Participante
import com.example.proyectofinal.proveedores.ActividadProveedor
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var recyclerViewGrupos: RecyclerView
    lateinit var adaptador: ActividadAdaptador
    lateinit var toolbar: Toolbar
    private val participantes = mutableListOf<Participante>()
    private val vistaParticipantes = mutableListOf<String>()
    //Definir boton
    lateinit var fabNuevoGrupo: FloatingActionButton

    //Atributos para gestionar el action mode
    companion object {
        public var actionMode_activo = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        inicializarRecyclerView()

        //DEFINO EL TOOLBAR
        toolbar = findViewById(R.id.toolbarMain)
        setSupportActionBar(findViewById(R.id.toolbarMain))

        this.fabNuevoGrupo = findViewById(R.id.fabNuevoGrupo)

        fabNuevoGrupo.setOnClickListener{
            //inflar el diseño del dialogo
            val dialogoCreateView = layoutInflater.inflate(R.layout.dialogo_crear_actividad, null)

            //crear el dialogo
            val dialogo = AlertDialog.Builder(this)
                .setView(dialogoCreateView)
                .create()

            //obtener los datos del formulario
            val nombreActividad = dialogoCreateView.findViewById<EditText>(R.id.txtNombreNuevaActividad)
            val nompreParticipante = dialogoCreateView.findViewById<EditText>(R.id.txtParticipante)
            val btNuevoParticipante = dialogoCreateView.findViewById<Button>(R.id.btNuevoParticipante)
            val tvListaParticipantes = dialogoCreateView.findViewById<TextView>(R.id.tvListaNuevosParticipantes)
            val btGuardar = dialogoCreateView.findViewById<Button>(R.id.btGuardar)
            val btCancelar = dialogoCreateView.findViewById<Button>(R.id.btCancelar)

            fun actualizarListaParticipantes() {
                tvListaParticipantes.text = if (participantes.isEmpty()) {
                    "No hay participantes"
                } else {
                    vistaParticipantes.joinToString("\n")
                }
            }

            btNuevoParticipante.setOnClickListener {
                val participante = nompreParticipante.text.toString().trim()
                val part: Participante
                if (participante.isNotEmpty()) {
                    part = Participante(participante, "${participante.lowercase()}@email.com")
                    participantes.add(part)
                    vistaParticipantes.add(part.nombre)
                    nompreParticipante.text.clear()
                    actualizarListaParticipantes()
                } else {
                    Toast.makeText(this, "Por favor ingrese un nombre para el participante", Toast.LENGTH_SHORT).show()
                }
            }

            //asignar la acción al botón
            btGuardar.setOnClickListener{
                var actividad = nombreActividad.text.toString()
                if (actividad.isEmpty() && participantes.isEmpty()) {
                    Toast.makeText(this, "ERROR: Por favor ingrese el nombre y al menos un participante", Toast.LENGTH_SHORT).show()
                } else if(ActividadProveedor.listaActividades.any{it.nombre == actividad}) {
                    Toast.makeText(this, "Ya existe una actividad con ese nombre", Toast.LENGTH_LONG).show()
                    actividad = ""
                }else {
                    //agregar actividad a la lista
                    ActividadProveedor.listaActividades.add(Actividad(actividad, participantes.toMutableList()))
                    //notificar cambios
                    adaptador.notifyItemInserted(ActividadProveedor.listaActividades.size - 1)

                    // Limpiar la lista de participantes después de guardar
                    participantes.clear()
                    vistaParticipantes.clear()

                    //cerrar el diálogo cuando se presiona en botón
                    dialogo.dismiss()
                }
            }
            btCancelar.setOnClickListener {
                dialogo.dismiss()
            }
            //mostrar el diálogo
            dialogo.show()
        }
    }

    private fun limpiarActionMode() {
        actionMode_activo = false
        //Limpio el menu
        toolbar.menu.clear()
        //Inflo el menu principal
        toolbar.inflateMenu(R.menu.menu_principal)
        //Desaparece la flecha hacia Arriba
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        //Pongo el titulo de la aplicacion
        toolbar.title = "GESTOR DE GASTOS"
        //Borro la lista de elementos seleccionados
        ActividadProveedor.actividades_seleccionadas.clear()
    }

    private fun inicializarRecyclerView() {
        this.recyclerViewGrupos = findViewById(R.id.recyclerViewGrupos)
        //Sirve para indicar que el tamaño del recyclerview no depende del contenido del adaptador, se optimiza
        recyclerViewGrupos.setHasFixedSize(true)
        //Definimos el tipo de recyclerView
        val manager = LinearLayoutManager(this)
        //Definimos el tipo de recyclerView
        recyclerViewGrupos.layoutManager = manager
        //Fijar el adaptador
        adaptador = ActividadAdaptador(
            ActividadProveedor.listaActividades,
            ActividadProveedor.actividades_seleccionadas,
            { pos -> onClickLargo(pos) },  // Pasamos la función onClickLargo
            { pos -> onClickCorto(pos) },  // Pasamos la función onClickCorto
        )
        recyclerViewGrupos.adapter = adaptador
    }

    // Función para manejar el click corto (abre el menú de selección)
    private fun onClickLargo(pos: Int) {
        if (!ActividadProveedor.actividades_seleccionadas.contains(pos)) {
            ActividadProveedor.actividades_seleccionadas.add(pos)
        }

        toolbar.inflateMenu(R.menu.menu_contextual_grupo)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.item_edit -> {
                    if (ActividadProveedor.actividades_seleccionadas.size == 1) {
                        val grupo_indice = ActividadProveedor.actividades_seleccionadas[0]
                        val grupo_seleccionado = ActividadProveedor.listaActividades[grupo_indice]

                        DialogoEditarActividad(
                            nombreActividad = grupo_seleccionado.nombre,
                            participantes = grupo_seleccionado.participantes.toMutableList()
                        ) { nuevoNombre, nuevosParticipantes ->
                            if (nuevoNombre.isEmpty()) {
                                Toast.makeText(this, "ERROR, complete el campo", Toast.LENGTH_LONG).show()
                            } else if (ActividadProveedor.listaActividades.any { it.nombre == nuevoNombre && it != grupo_seleccionado }) {
                                Toast.makeText(this, "Ya existe una actividad con ese nombre", Toast.LENGTH_LONG).show()
                            } else {
                                grupo_seleccionado.nombre = nuevoNombre
                                grupo_seleccionado.participantes = nuevosParticipantes
                                adaptador.notifyItemChanged(grupo_indice)
                                limpiarActionMode()
                            }
                        }.show(supportFragmentManager, "EditarActividadDialog")
                    }
                }

                R.id.item_delete -> {
                    actionMode_activo = false
                    ActividadProveedor.listaActividades.removeIf {
                        ActividadProveedor.actividades_seleccionadas.contains(ActividadProveedor.listaActividades.indexOf(it))
                    }
                    adaptador.notifyDataSetChanged()
                    limpiarActionMode()
                }
            }
            true
        }
    }


    // Función para manejar el click corto (abre DetalleGrupoActivity solo si el grupo tiene 0 participantes)
    private fun onClickCorto(pos: Int): Boolean {
        val actividad = ActividadProveedor.listaActividades[pos]

        val intent = Intent(this, DetalleActividadActivity::class.java)
        intent.putExtra("actividad_nombre", actividad.nombre)
        startActivity(intent)
        return true
    }

    override fun onResume() {
        super.onResume()
        adaptador.notifyDataSetChanged() // Notifica al RecyclerView que hay cambios
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            adaptador.notifyDataSetChanged() // Solo actualiza si el usuario guardó
        }
    }
}