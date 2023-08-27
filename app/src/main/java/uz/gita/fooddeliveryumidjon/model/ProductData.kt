package uz.gita.fooddeliveryumidjon.model

import java.io.Serializable

data class ProductData(
    val id: String = "",
    val title: String = "",
    val info: String = "",
    val price: Long = -1,
    val imageUrl: String = ""
) : Serializable
