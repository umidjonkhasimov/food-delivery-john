package uz.gita.fooddeliveryumidjon.model

data class ProductResponse(
    val id: String,
    val categoryID: String,
    val title: String,
    val info: String,
    val price: Long,
    val imageUrl: String
)