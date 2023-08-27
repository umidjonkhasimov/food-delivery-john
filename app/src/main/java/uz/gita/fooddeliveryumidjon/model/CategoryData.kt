package uz.gita.fooddeliveryumidjon.model

data class CategoryData(
    val id: String,
    val title: String,
    val products: List<ProductData>
)