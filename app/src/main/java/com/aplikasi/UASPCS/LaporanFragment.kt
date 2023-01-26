package com.aplikasi.UASPCS

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aplikasi.UASPCS.adapter.LaporanAdapter
import com.aplikasi.UASPCS.api.BaseRetrofit
import com.aplikasi.UASPCS.response.transaksi.TransaksiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*

class LaporanFragment : Fragment() {

    private val api by lazy { BaseRetrofit().endpoint }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_laporan, container, false)
        getLaporan(view)
        return view
    }

    fun getLaporan(view: View) {
        val token = LoginActivity.sessionManager.getString("TOKEN")
        api.getLaporan(token.toString()).enqueue(object : Callback<TransaksiResponse> {
            override fun onResponse(
                    call: Call<TransaksiResponse>,
                    response: Response<TransaksiResponse>
            ) {

                val rv = view.findViewById<RecyclerView>(R.id.rv_laporan)
                val txtTotalPendapatan = view.findViewById<TextView>(R.id.txtTotalPendapatan)
                // val totalPendapatan = response.body()!!.data.total

                val localeID =  Locale("in", "ID")
                val numberFormat = NumberFormat.getCurrencyInstance(localeID)

                val transaksiList = response.body()!!.data.transaksi

                val total = transaksiList.map { it.total.toDouble() }.sum()

                txtTotalPendapatan.text = numberFormat.format(total).toString()

                rv.setHasFixedSize(true)
                rv.layoutManager = LinearLayoutManager(activity)
                val rvAdapter = LaporanAdapter(response.body()!!.data.transaksi)
                rv.adapter = rvAdapter
            }

            override fun onFailure(call: Call<TransaksiResponse>, t: Throwable) {
                Log.e("ProdukError", t.toString())
            }

        })
    }

}