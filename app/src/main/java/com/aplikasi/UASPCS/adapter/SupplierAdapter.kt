package com.aplikasi.UASPCS.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aplikasi.UASPCS.CallbackInterface
import com.aplikasi.UASPCS.R
import com.aplikasi.UASPCS.response.cart.Cart
import com.aplikasi.UASPCS.response.supplier.Supplier
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class SupplierAdapter(val listProduk: List<Supplier>): RecyclerView.Adapter<SupplierAdapter.ViewHolder>() {

    var callbackInterface: CallbackInterface? = null
    var total: Int = 0
    var cart: ArrayList<Cart> = arrayListOf<Cart>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupplierAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_supplier, parent, false)
        return SupplierAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SupplierAdapter.ViewHolder, position: Int) {
        val supplier = listProduk[position]
        holder.txtNamaProduk.text = supplier.nama

        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)

        holder.txtHarga.text = numberFormat.format(supplier.harga.toDouble()).toString()

        holder.btnPlus.setOnClickListener {
            val old_value = holder.txtQty.text.toString().toInt()
            val new_value = old_value+1

            holder.txtQty.setText(new_value.toString())

            total = total+supplier.harga.toString().toInt()

            val index = cart.indexOfFirst { it.id == supplier.id.toInt() }.toInt()
            if(index!=-1){
                cart.removeAt(index)
            }

            val itemCart = Cart(supplier.id.toInt(), supplier.harga.toInt(), new_value)
            cart.add(itemCart)
            callbackInterface?.passResultCallback(total.toString(), cart)


        }

        holder.btnMinus.setOnClickListener {
            val old_value = holder.txtQty.text.toString().toInt()
            val new_value = old_value-1


            val index = cart.indexOfFirst { it.id == supplier.id.toInt() }.toInt()
            if(index!=-1){
                cart.removeAt(index)
            }

            if(new_value>=0){
                holder.txtQty.setText(new_value.toString())
                total = total-supplier.harga.toString().toInt()


            }

            if (new_value>=1){
                val itemCart = Cart(supplier.id.toInt(), supplier.harga.toInt(), new_value)
                cart.add(itemCart)
            }


            callbackInterface?.passResultCallback(total.toString(), cart)

        }
    }

    override fun getItemCount(): Int {
        return listProduk.size
    }

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val txtNamaProduk = itemView.findViewById<TextView>(R.id.txtNamaProduk)
        val txtHarga = itemView.findViewById<TextView>(R.id.txtHarga)
        val txtQty = itemView.findViewById<TextView>(R.id.txtQty)
        val btnPlus = itemView.findViewById<ImageButton>(R.id.btnPlus)
        val btnMinus = itemView.findViewById<ImageButton>(R.id.btnMinus)
    }
}