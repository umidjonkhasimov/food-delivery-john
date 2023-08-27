package uz.gita.fooddeliveryumidjon.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.fooddeliveryumidjon.model.BasketProductData
import uz.gita.fooddeliveryumidjon.model.CategoryData
import uz.gita.fooddeliveryumidjon.model.ProductData
import uz.gita.fooddeliveryumidjon.model.ProductResponse

interface HomeRepository {
    fun getAllData(): Flow<Result<List<CategoryData>>>
    fun getAllProductsByCategory(categoryId: String): Flow<Result<List<CategoryData>>>
    fun getProductById(productId: String): Flow<Result<List<ProductData>>>

    fun isProductInTheBasket(productId: String): Flow<Result<BasketProductData?>>
    fun addProductToBasket(basketProductData: BasketProductData): Flow<Result<Unit>>
    fun getAllBasketProducts(): Flow<Result<List<BasketProductData>>>
    fun removeProductFromBasket(basketProductData: BasketProductData): Flow<Result<Unit>>
    fun removeAllProductsFromBasket(): Flow<Result<Unit>>
    fun incrementQuantityOfBasketProduct(basketProductData: BasketProductData): Flow<Result<Unit>>
    fun decrementQuantityOfBasketProduct(basketProductData: BasketProductData): Flow<Result<Unit>>

    fun searchForProducts(query: String): Flow<Result<List<ProductData>>>

    fun placeOrders(): Flow<Result<Unit>>

    fun getAllOrders(): Flow<Result<List<BasketProductData>>>
}