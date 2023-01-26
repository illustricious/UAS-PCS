package com.aplikasi.UASPCS.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aplikasi.UASPCS.R
import com.aplikasi.UASPCS.response.transaksi.Transaksi
import java.text.NumberFormat
import java.util.*

class LaporanAdapter(val ListTransaksi: List<Transaksi>): RecyclerView.Adapter<LaporanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_laporan, parent, false)
        return LaporanAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaksi = ListTransaksi[position]
        holder.txtTglTransaksi.text = transaksi.tanggal
        holder.txtTransaksiNota.text = "#0000"+transaksi.id.toString()

        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)

        holder.txtTotalItemTransaksi.text = numberFormat.format(transaksi.total.toDouble()).toString()
    }

    override fun getItemCount(): Int {
        return ListTransaksi.size
    }

    class  ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val txtTglTransaksi = itemView.findViewById<TextView>(R.id.txtTglTransaksi)
        val txtTransaksiNota = itemView.findViewById<TextView>(R.id.txtTransaksiNota)
        val txtTotalItemTransaksi = itemView.findViewById<TextView>(R.id.txtTotalItemTransaksi)
    }
}