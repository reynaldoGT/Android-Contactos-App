package com.example.contactos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class acivity_detalle : AppCompatActivity() {

    var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acivity_detalle)
        val toolbar = findViewById<Toolbar>(R.id.toobal)
        setSupportActionBar(toolbar)

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        index = intent.getStringExtra("ID").toInt()
        // para poder ver el id del item seleccionado
        // Log.d("index",index.toString())
        //Recibir el contacto
        mapearDatos()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalle, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.iEditar -> {
                val intent = Intent(this, Nuevo::class.java)
                intent.putExtra("ID", index.toString())
                startActivity(intent)
                return true
            }
            R.id.iEliminar -> {
                MainActivity.eliminarContacto(index)
                finish()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun mapearDatos() {
        val contacto = MainActivity.obtenerContacto(index)

        val tvNombre = findViewById<TextView>(R.id.tvNombre)
        //val tvApellido = findViewById<TextView>(R.id.tvTItuloEmpresa)
        val tvEmpresa = findViewById<TextView>(R.id.tvApellido)
        val tvEdad = findViewById<TextView>(R.id.tvEdad)
        val tvPeso = findViewById<TextView>(R.id.tvPeso)
        val tvTelefono = findViewById<TextView>(R.id.tvTelefono)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val tvDireccion = findViewById<TextView>(R.id.tvDireccion)
        //Para la imagen
        val ivFoto = findViewById<ImageView>(R.id.imagenFoto)


        tvNombre.text = contacto.nombre + " " + contacto.apellidos
        tvEmpresa.text = contacto.empresa
        tvEdad.text = contacto.edad.toString() + " Años"
        tvPeso.text = contacto.peso.toString() + " kg"
        tvTelefono.text = contacto.telefono
        tvEmail.text = contacto.email
        tvDireccion.text = contacto.direccion

        ivFoto.setImageResource(contacto.foto)

    }

    override fun onResume() {
        super.onResume()
        mapearDatos()
    }
}