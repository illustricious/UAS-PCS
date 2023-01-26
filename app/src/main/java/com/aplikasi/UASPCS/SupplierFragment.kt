package com.aplikasi.UASPCS

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aplikasi.UASPCS.adapter.SupplierAdapter
import com.aplikasi.UASPCS.api.BaseRetrofit
import com.aplikasi.UASPCS.response.cart.Cart
import com.aplikasi.UASPCS.response.supplier.SupplierResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates


class SupplierFragment : Fragment() {

    private val api by lazy { BaseRetrofit().endpoint }
    private lateinit var my_cart: ArrayList<Cart>
    private lateinit var total_bayar : String
    private var total_mines by Delegates.notNull<Double>()
    private var total_mines_int by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_transaksi, container, false)
        getProduk(view)

        val btnBayar = view.findViewById<Button>(R.id.btnBayar)
        btnBayar.setOnClickListener {

            val bundle = Bundle()
            bundle.putParcelableArrayList("MY_CART", my_cart)
            bundle.putString("TOTAL", total_bayar)
            findNavController().navigate(R.id.bayarSupplierFragment, bundle)
        }

        return view
    }

    fun getProduk(view: View){
        val token = LoginActivity.sessionManager.getString("TOKEN")

        api.getSupplier(token.toString()).enqueue(object : Callback<SupplierResponse> {
            override fun onResponse(
                call: Call<SupplierResponse>,
                response: Response<SupplierResponse>
            ) {
                Log.d("ProdukData", response.body().toString())

                val rv= view.findViewById<RecyclerView>(R.id.rv_transaksi)

                rv.setHasFixedSize(true)
                rv.layoutManager = LinearLayoutManager(activity)
                val rvAdapter = SupplierAdapter(response.body()!!.data.produk)
                rv.adapter = rvAdapter

                rvAdapter.callbackInterface = object : CallbackInterface{
                    override fun passResultCallback(total: String, cart: ArrayList<Cart>) {
                        val txtTotalBayar = activity?.findViewById<TextView>(R.id.txtTotalBayar)

                        val localeID =  Locale("in", "ID")
                        val numberFormat = NumberFormat.getCurrencyInstance(localeID)

                        txtTotalBayar?.setText(numberFormat.format(total.toDouble()).toString())

                        total_mines = (total.toDouble() * -1)
                        total_mines_int = total_mines.toInt()

                        total_bayar = total_mines_int.toString()
                        my_cart = cart

                        Log.d("MyCart", cart.toString())
                    }

                }
            }

            override fun onFailure(call: Call<SupplierResponse>, t: Throwable) {
                Log.e("ProdukError",t.toString())
            }

        })
    }


}