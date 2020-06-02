package com.example.contactos

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.GridView
import android.widget.ListView
import android.widget.Switch
import android.widget.ViewSwitcher
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    var lista: ListView? = null
    var grid: GridView? = null
    var viewSwitcher: ViewSwitcher? = null

    companion object {
        var contactos: ArrayList<Contacto>? = null
        var adatador: AdapterCustom? = null
        var adaptadorGrid: AdaptadorCustomGrid? = null
        fun agregarContacto(contacto: Contacto) {
            adatador?.addItem(contacto)
        }

        fun obtenerContacto(index: Int): Contacto {
            return adatador?.getItem(index) as Contacto
        }

        fun eliminarContacto(index: Int) {
            adatador?.removeItem(index)
        }

        fun actualizarContaco(index: Int, nuevoContacto: Contacto) {
            adatador?.updateItem(index, nuevoContacto)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //anidando nuestro toolbar

        val toolbar = findViewById<Toolbar>(R.id.toobal)
        setSupportActionBar(toolbar)
        contactos = ArrayList()
        contactos?.add(
            Contacto(
                "Juan",
                "Mamani",
                "tamaholipas",
                25,
                70.0F,
                "El alto la paz",
                "rrey_gato10@gmail.com",
                R.drawable.foto_01,
                "789487"
            )
        )
        contactos?.add(
            Contacto(
                "Larz",
                "DOsan",
                "No lo se rick",
                26,
                70.0F,
                "El alto la paz",
                "rrey_gato10@gmail.com",
                R.drawable.foto_02,
                "789487"
            )
        )
        contactos?.add(
            Contacto(
                "Juan",
                "Mamani",
                "tamaholipas",
                25,
                70.0F,
                "El alto la paz",
                "rrey_gato10@gmail.com",
                R.drawable.foto_04,
                "789487"
            )
        )
        contactos?.add(
            Contacto(
                "Juan",
                "Mamani",
                "tamaholipas",
                25,
                70.0F,
                "El alto la paz",
                "rrey_gato10@gmail.com",
                R.drawable.foto_04,
                "789487"
            )
        )
        contactos?.add(
            Contacto(
                "Juan",
                "Mamani",
                "tamaholipas",
                25,
                70.0F,
                "El alto la paz",
                "rrey_gato10@gmail.com",
                R.drawable.foto_04,
                "789487"
            )
        )
        contactos?.add(
            Contacto(
                "Juan",
                "Mamani",
                "tamaholipas",
                25,
                70.0F,
                "El alto la paz",
                "rrey_gato10@gmail.com",
                R.drawable.foto_04,
                "789487"
            )
        )
        lista = findViewById(R.id.lista)
        grid = findViewById(R.id.grid)
        adatador = AdapterCustom(this, contactos!!)
        adaptadorGrid = AdaptadorCustomGrid(this, contactos!!)
        viewSwitcher = findViewById(R.id.viewSwicher)

        lista?.adapter = adatador
        grid?.adapter = adaptadorGrid

        lista?.setOnItemClickListener { parent, view, position, id ->
            intent = Intent(this, acivity_detalle::class.java)
            intent.putExtra("ID", position.toString())
            startActivity(intent)

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val itemBusqueda = menu?.findItem(R.id.searh_view)
        val searchView = itemBusqueda?.actionView as SearchView

        //para el item swtich
        val itemSwitch = menu.findItem(R.id.swicth_view)
        itemSwitch.setActionView(R.layout.switch_item)
        val SwitchView = itemSwitch.actionView?.findViewById<Switch>(R.id.sCambiaVista)


        //Habilitar el servicio
        searchView.setSearchableInfo(searManager.getSearchableInfo(componentName))
        searchView.queryHint = "Buscar Contacto ..."

        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            //Preparar datos
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {

                adatador?.filtrar(newText!!)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                //Filtrar
                return true
            }
        })
        //Aplicando los metodos para el switch
        SwitchView?.setOnCheckedChangeListener { buttonView, isChecked ->
            viewSwitcher?.showNext()
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.iCrearNuevo -> {
                val intent = Intent(this, Nuevo::class.java)
                startActivity(intent)
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        adatador?.notifyDataSetChanged()
    }
}
