package uz.gita.fooddeliveryumidjon.model

data class BasketProductData(
    val id: String = "",
    val productId: String = "",
    val quantity: Int = -1,
    val productTitle: String = "",
    val imageUrl: String = "",
    val price: Int = -1
)