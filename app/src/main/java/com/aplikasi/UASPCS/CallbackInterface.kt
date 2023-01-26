package com.aplikasi.UASPCS

import com.aplikasi.UASPCS.response.cart.Cart

interface CallbackInterface {
    fun passResultCallback(total:String, cart: ArrayList<Cart>)
}