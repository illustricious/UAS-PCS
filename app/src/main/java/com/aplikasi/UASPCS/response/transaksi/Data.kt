package com.aplikasi.UASPCS.response.transaksi

data class Data(
    val transaksi: List<Transaksi>,
    val total: String
)