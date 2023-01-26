package com.aplikasi.UASPCS.response.itemTransaksi

data class ItemTransaksi(
    val harga_saat_transaksi: String,
    val product_id: String,
    val qty: String,
    val sub_total: String,
    val transaksi_id: String
)