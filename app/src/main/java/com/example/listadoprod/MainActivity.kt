package com.example.listadoprod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listadoprod.dataadapter.ProductoAdapter
import com.example.listadoprod.databinding.ActivityMainBinding
import com.example.listadoprod.dataclass.Producto

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var listaProd = ArrayList<Producto>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniciar()

    }

    private fun limpiar() {
        with(binding) {
            etID.setText("")
            etNombreProd.setText("")
            etPrecio.setText("")
            etID.requestFocus()
        }
    }

    private fun agregarProd() {
        with(binding) {
            try {
                val id: Int = etID.text.toString().toInt()
                val nombre: String = etNombreProd.text.toString()
                val precio: Double = etPrecio.text.toString().toDouble()
                val prod = Producto(id, nombre, precio)
                listaProd.add(prod)
            }catch (ex: Exception){
                Toast.makeText(this@MainActivity, "Error: ${ex.toString()} ",
                    Toast.LENGTH_LONG).show()
            }
            rcvLista.layoutManager = LinearLayoutManager(this@MainActivity)
            rcvLista.adapter = ProductoAdapter(listaProd, binding)   //crea un nuevo Producto Adapter, permite
                                                            // llamada nueva de onCreateViewHolder
            limpiar()

        }
    }

    private fun editarProd() {
        try{
            var index: Int = 0
            with(binding) {
                if (etID.text.toString() != "" && etNombreProd.text.toString() != "" &&
                    etNombreProd.text.toString() != "") {
                    for(p in listaProd){
                        if(p.id == etID.text.toString().toInt()) {
                            listaProd[index].nombre = etNombreProd.text.toString()
                            listaProd[index].precio = etPrecio.text.toString().toDouble()
                            rcvLista.layoutManager = LinearLayoutManager(this@MainActivity)
                            rcvLista.adapter = ProductoAdapter(listaProd, binding)
                            limpiar()
                            break
                        }
                        index++
                    }

                }
                if(index == listaProd.size){
                    throw Exception("El registro no existe")
                }
            }
        }catch (ex: Exception){
            Toast.makeText(this@MainActivity, "Error: ${ex.toString()} ",
                Toast.LENGTH_LONG).show()
        }
    }

    private fun iniciar() {
        binding.btnAgregar.setOnClickListener {
            agregarProd()
        }
        binding.btnLimpiar.setOnClickListener {
            limpiar()
        }
        binding.btnEditar.setOnClickListener {
            editarProd()
        }
    }


}