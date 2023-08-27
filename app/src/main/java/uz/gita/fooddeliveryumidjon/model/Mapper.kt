package uz.gita.fooddeliveryumidjon.model

object Mapper {

    fun ProductResponse.toProductData(): ProductData {
        return ProductData(
            id = this.id,
            title = this.title,
            info = this.info,
            price = this.price,
            imageUrl = this.imageUrl
        )
    }

}