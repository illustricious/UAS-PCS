package com.aplikasi.UASPCS.response.supplier

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Supplier(
    val admin_id: String,
    val harga: String,
    val id: String,
    val nama: String,
    val nama_admin: String,
    val stok: String
):Parcelable
