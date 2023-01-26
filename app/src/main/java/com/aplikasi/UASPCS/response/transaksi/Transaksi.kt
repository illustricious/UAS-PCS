package com.aplikasi.UASPCS.response.transaksi

data class Transaksi(
    val id : Int,
    val admin_id: String,
    val tanggal: String,
    val total: String
)