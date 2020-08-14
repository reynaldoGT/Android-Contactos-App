package com.example.contactos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar

class Nuevo : AppCompatActivity() {

    var fotoIndex: Int = 0
    var foto: ImageView? = null
    val fotos = arrayOf(
        R.drawable.foto_01,
        R.drawable.foto_02,
        R.drawable.foto_03,
        R.drawable.foto_04,
        R.drawable.foto_05,
        R.drawable.foto_06
    )
    var index: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo)
        val toolbar = findViewById<Toolbar>(R.id.toobal)
        setSupportActionBar(toolbar)
        //para habilitar el boton para ir atras
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        foto = findViewById(R.id.imagenFoto)
        foto?.setOnClickListener {
            seleccionarPhoto()
        }
        //Reconocer accion de nuevo vs editar
        if (intent.hasExtra("ID")) {
            index = intent.getStringExtra("ID").toInt()
            rellenarDatos(index)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_nuevo, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.iCrearNuevo -> {

                val nombre = findViewById<EditText>(R.id.tvNombre)
                val apellidos = findViewById<EditText>(R.id.tvApellido)
                val empresa = findViewById<EditText>(R.id.tvApellido)
                val edad = findViewById<EditText>(R.id.tvEdad)
                val peso = findViewById<EditText>(R.id.tvPeso)
                val telefono = findViewById<EditText>(R.id.tvTelefono)
                val email = findViewById<EditText>(R.id.tvEmail)
                val direccion = findViewById<EditText>(R.id.tvDireccion)
                // vallidar campos
                val campos = ArrayList<String>()
                campos.add(nombre.text.toString())
                campos.add(apellidos.text.toString())
                campos.add(empresa.text.toString())
                campos.add(edad.text.toString())
                campos.add(peso.text.toString())
                campos.add(direccion.text.toString())
                campos.add(email.text.toString())
                campos.add(direccion.text.toString())
                var flag = false
                for (campo in campos) {
                    if (campo.isEmpty()) {
                        flag = true
                    }
                }

                if (flag) {
                    Toast.makeText(this, "Por favor rellena todos los campos", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    if (index > -1) {
                        MainActivity.actualizarContacto(
                            index, Contacto(
                                nombre.text.toString(),
                                apellidos.text.toString(),
                                empresa.text.toString(),
                                edad.text.toString().toInt(),
                                peso.text.toString().toFloat(),
                                direccion.text.toString(),
                                email.text.toString(),
                                obtenerFoto(fotoIndex),
                                telefono.text.toString()
                            )
                        )
                        finish()
                    } else {
                        MainActivity.agregarContacto(
                            Contacto(
                                nombre.text.toString(),
                                apellidos.text.toString(),
                                empresa.text.toString(),
                                edad.text.toString().toInt(),
                                peso.text.toString().toFloat(),
                                direccion.text.toString(),
                                email.text.toString(),
                                obtenerFoto(fotoIndex),
                                telefono.text.toString()
                            )
                        )
                        finish()
                    }
                }
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun seleccionarPhoto() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona Imagen de perfil")

        val adaptadorDialogo =
            ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item)
        adaptadorDialogo.add("foto 1")
        adaptadorDialogo.add("foto 2")
        adaptadorDialogo.add("foto 3")
        adaptadorDialogo.add("foto 4")
        adaptadorDialogo.add("foto 5")
        adaptadorDialogo.add("foto 6")
        builder.setAdapter(adaptadorDialogo) { dialog, which ->
            fotoIndex = which
            foto?.setImageResource(obtenerFoto(fotoIndex))

        }
        builder.setNegativeButton("Cancelar") { dialog, which ->
            fotoIndex = which
            dialog.dismiss()
        }
        builder.show()
    }

    private fun obtenerFoto(index: Int): Int {
        return fotos.get(index)
    }

    private fun rellenarDatos(index: Int) {

        val contacto = MainActivity.obtenerContacto(index)

        val tvNombre = findViewById<EditText>(R.id.tvNombre)
        val tvApellido = findViewById<TextView>(R.id.tvApellido)
        val tvEmpresa = findViewById<EditText>(R.id.tvEmpresa)
        val tvEdad = findViewById<EditText>(R.id.tvEdad)
        val tvPeso = findViewById<EditText>(R.id.tvPeso)
        val tvTelefono = findViewById<EditText>(R.id.tvTelefono)
        val tvEmail = findViewById<EditText>(R.id.tvEmail)
        val tvDireccion = findViewById<EditText>(R.id.tvDireccion)
        //Para la imagen
        val ivFoto = findViewById<ImageView>(R.id.imagenFoto)


        tvNombre.setText(contacto.nombre, TextView.BufferType.EDITABLE)
        tvApellido.setText(contacto.apellidos,TextView.BufferType.EDITABLE)
        tvEmpresa.setText(contacto.empresa, TextView.BufferType.EDITABLE)
        tvEdad.setText(contacto.edad.toString(), TextView.BufferType.EDITABLE)
        tvPeso.setText(contacto.peso.toString(), TextView.BufferType.EDITABLE)
        tvTelefono.setText(contacto.telefono, TextView.BufferType.EDITABLE)
        tvEmail.setText(contacto.email, TextView.BufferType.EDITABLE)
        tvDireccion.setText(contacto.direccion, TextView.BufferType.EDITABLE)

        ivFoto.setImageResource(contacto.foto)

        for((posicion, foto) in fotos.withIndex()) {
            if (contacto.foto == foto) {
                fotoIndex = posicion
            }
        }

    }
}