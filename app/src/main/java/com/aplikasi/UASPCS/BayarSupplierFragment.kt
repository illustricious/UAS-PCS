package com.aplikasi.UASPCS

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aplikasi.UASPCS.api.BaseRetrofit
import com.aplikasi.UASPCS.response.cart.Cart
import com.aplikasi.UASPCS.response.itemTransaksi.ItemTransasksiResponsePost
import com.aplikasi.UASPCS.response.transaksi.TransaksiResponsePost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*

class BayarSupplierFragment : Fragment() {

    private val api by lazy { BaseRetrofit().endpoint }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_bayar_supplier, container, false)

        val token = LoginActivity.sessionManager.getString("TOKEN")
        val adminId = LoginActivity.sessionManager.getString("ADMIN_ID")

        val total = arguments?.getString("TOTAL")
        val my_cart = arguments?.getParcelableArrayList<Cart>("MY_CART")

        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)

        val txtTotalTransaksiBayar = view.findViewById<TextView>(R.id.txtTotalTransaksiBayar)
        txtTotalTransaksiBayar.setText(numberFormat.format(total.toString().toDouble()).toString())

        val btnKonfirmasi = view.findViewById<Button>(R.id.btnKonfirmasi)
        btnKonfirmasi.setOnClickListener {
            api.postTransaksi(token.toString(), adminId.toString().toInt(), total.toString().toInt()).enqueue(object : Callback<TransaksiResponsePost>{
                override fun onResponse(call: Call<TransaksiResponsePost>, response: Response<TransaksiResponsePost>) {
                    val id_transaksi = response.body()!!.data.transaksi.id
                    Log.e("id_transaksi", id_transaksi.toString())

                    for (item in my_cart!!){
                        var itemqtymin = (item.qty * -1)
                        Log.e("qty", itemqtymin.toString())
                        api.postItemTransaksi(token.toString(), id_transaksi.toString().toInt(), item.id, itemqtymin, item.harga).enqueue(object : Callback<ItemTransasksiResponsePost>{
                            override fun onResponse(call: Call<ItemTransasksiResponsePost>, response: Response<ItemTransasksiResponsePost>) {
                            }

                            override fun onFailure(call: Call<ItemTransasksiResponsePost>, t: Throwable) {
                                Log.e("Error",t.toString())
                            }

                        })
                    }

                    Toast.makeText(view.context,"Data transaksi disimpan", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.supplierFragment)

                }

                override fun onFailure(call: Call<TransaksiResponsePost>, t: Throwable) {
                    Log.e("Error",t.toString())
                }

            })
        }

        return view
    }


}