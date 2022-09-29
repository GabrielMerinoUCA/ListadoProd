package com.example.listadoprod.dataadapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listadoprod.R
import com.example.listadoprod.databinding.ActivityMainBinding
import com.example.listadoprod.databinding.ItemlistaBinding
import com.example.listadoprod.dataclass.Producto

class ProductoAdapter(val listsProd: MutableList<Producto>, var bindingMain: ActivityMainBinding) :
    RecyclerView.Adapter<ProductoAdapter.ProductoHolder>() {

    inner class ProductoHolder(
        val binding: ItemlistaBinding,
        var bindingMain2: ActivityMainBinding,
        var listaProd: MutableList<Producto>,
        var context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {

        //Metodo de inventarse el agua helada
        fun cargar(producto: Producto, position: Int) {
            with(binding) {
                tvCodProd.text = producto.id.toString()
                tvNombreProd.text = producto.nombre
                tvPrecioProd.text = producto.precio.toString()
                tvCodProd.setOnClickListener {
                    var pos = position
                    selectedPosition(pos, producto)
                }

                tvNombreProd.setOnClickListener {
                    var pos = position
                    selectedPosition(pos, producto)
                }
                tvPrecioProd.setOnClickListener {
                    var pos = position
                    selectedPosition(pos, producto)
                }
                btnEliminar.setOnClickListener {
                    var pos = position
                    confirmarEliminacion(pos).show()
                }
            }
        }

        private fun confirmarEliminacion(pos: Int): AlertDialog{
            val confirmarAlert = AlertDialog.Builder(context)
                .setTitle("ELIMINAR PRODUCTO")
                .setMessage(
                    "Esta seguro que desea eliminar el producto: \n " +
                            "${listaProd[pos].nombre} ?"
                )
                .setIcon(R.drawable.ic_warning)
                .setPositiveButton("Si") { _, _ ->

                    Toast.makeText(
                        context,
                        "Registro eliminado exitosamente",
                        Toast.LENGTH_SHORT
                    ).show()

                    listaProd.remove(listaProd[pos])
                    bindingMain2.rcvLista.layoutManager =
                        LinearLayoutManager(context) //revisar context
                    bindingMain2.rcvLista.adapter = ProductoAdapter(listaProd, bindingMain2)
                }
                .setNegativeButton("No") {_,_ ->
                    Toast.makeText(
                        context,
                        "Abortado",
                        Toast.LENGTH_SHORT
                    ).show()
                }.create()
            return confirmarAlert
        }

        private fun selectedPosition(position: Int, producto: Producto) {
            with(bindingMain2) {
                etID.setText(listsProd[position].id.toString())
                etPrecio.setText(listsProd[position].precio.toString())
                etNombreProd.setText(listsProd[position].nombre)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoHolder {
        val binding = ItemlistaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ) //Cargar el itemlista.xml
        return ProductoHolder(binding, bindingMain, listsProd, parent.context)
    }

    override fun onBindViewHolder(holder: ProductoHolder, position: Int) {
        holder.cargar(listsProd[position], position)
    }

    override fun getItemCount(): Int = listsProd.size

}