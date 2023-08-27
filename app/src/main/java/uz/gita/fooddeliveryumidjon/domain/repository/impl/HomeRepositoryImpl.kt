package uz.gita.fooddeliveryumidjon.domain.repository.impl

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import uz.gita.fooddeliveryumidjon.domain.repository.HomeRepository
import uz.gita.fooddeliveryumidjon.model.BasketProductData
import uz.gita.fooddeliveryumidjon.model.CategoryData
import uz.gita.fooddeliveryumidjon.model.CategoryResponse
import uz.gita.fooddeliveryumidjon.model.Mapper.toProductData
import uz.gita.fooddeliveryumidjon.model.ProductData
import uz.gita.fooddeliveryumidjon.model.ProductResponse

class HomeRepositoryImpl : HomeRepository {
    private val fb = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    companion object {
        private var instance: HomeRepositoryImpl? = null

        fun getInstance(): HomeRepository {
            if (instance == null) {
                instance = HomeRepositoryImpl()
            }
            return instance!!
        }
    }

    override fun getAllData(): Flow<Result<List<CategoryData>>> = callbackFlow {
        val categories = ArrayList<CategoryResponse>()
        getAllCategories()
            .onSuccess {
                categories.addAll(it)
            }
            .onFailure {
                trySend(Result.failure(it))
            }

        val products = ArrayList<ProductResponse>()
        getAllProducts()
            .onSuccess {
                products.addAll(it)
            }
            .onFailure {
                trySend(Result.failure(it))
            }

        val result = ArrayList<CategoryData>()
        categories.forEach {
            result.add(
                CategoryData(
                    id = it.id,
                    title = it.title,
                    products = products.filter { product ->
                        product.categoryID == it.id
                    }.map { product ->
                        product.toProductData()
                    }
                )
            )
        }
        trySend(Result.success(result))
        awaitClose()
    }

    override fun getAllProductsByCategory(categoryId: String): Flow<Result<List<CategoryData>>> = callbackFlow {
        val catList = ArrayList<CategoryData>()
        var categoryTitle = ""
        getCategoryTitle(categoryId)
            .onSuccess { categoryTitle = it }
            .onFailure { trySend(Result.failure(it)) }

        fb.collection("products")
            .whereEqualTo("category_id", categoryId)
            .get()
            .addOnSuccessListener { snapshot ->
                val list = ArrayList<ProductResponse>()
                snapshot.forEach {
                    list.add(
                        ProductResponse(
                            id = it.id,
                            categoryID = it.get("category_id").toString(),
                            title = it.get("title").toString(),
                            info = it.get("info").toString(),
                            price = it.get("price") as Long,
                            imageUrl = it.get("imageUrl").toString()
                        )
                    )
                }

                val categoryData = CategoryData(categoryId, categoryTitle, list.map { it.toProductData() })
                catList.add(categoryData)
                trySend(Result.success(catList))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }

    override fun getProductById(productId: String): Flow<Result<List<ProductData>>> = callbackFlow {

        awaitClose()
    }

    override fun isProductInTheBasket(productId: String): Flow<Result<BasketProductData?>> = callbackFlow {
        val query = fb.collection("basket")
            .whereEqualTo("user_id", auth.currentUser?.uid)
            .whereEqualTo("product_id", productId)
            .get()
            .await()

        if (query.isEmpty) {
            trySend(Result.success(null))
        } else {
            var data: BasketProductData? = null
            query.forEach { doc ->
                data = doc.toObject(BasketProductData::class.java)
            }
            trySend(Result.success(data))
        }

        awaitClose()
    }

    override fun addProductToBasket(basketProductData: BasketProductData): Flow<Result<Unit>> = callbackFlow {
        val data = hashMapOf(
            "product_id" to basketProductData.id,
            "quantity" to basketProductData.quantity,
            "user_id" to auth.currentUser?.uid
        )

        fb.collection("basket")
            .add(data)
            .addOnSuccessListener {
                trySend(Result.success(Unit))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }

    override fun getAllBasketProducts(): Flow<Result<List<BasketProductData>>> = callbackFlow {
        val user = auth.currentUser

        try {
            val basketSnapshot = fb.collection("basket")
                .whereEqualTo("user_id", user?.uid)
                .get()
                .await()

            val basketDataList = mutableListOf<BasketProductData>()

            basketSnapshot.forEach { doc ->

                val productId = doc.getString("product_id")
                val quantity = doc.getLong("quantity")?.toInt()

                if (productId != null && quantity != null) {
                    val productSnapshot = fb.collection("products")
                        .document(productId)
                        .get()
                        .await()

                    val productTitle = productSnapshot.getString("title")
                    val imageUrl = productSnapshot.getString("imageUrl")
                    val price = productSnapshot.getLong("price")?.toInt()

                    if (productTitle != null && imageUrl != null && price != null) {

                        val basketData = BasketProductData(
                            doc.id,
                            productId,
                            quantity,
                            productTitle,
                            imageUrl,
                            price
                        )
                        basketDataList.add(basketData)
                    }
                }
            }
            trySend(Result.success(basketDataList))

        } catch (e: Exception) {
            trySend(Result.failure(e))
        }
        awaitClose()
    }

    override fun removeProductFromBasket(basketProductData: BasketProductData): Flow<Result<Unit>> = callbackFlow {
        fb.collection("basket")
            .document(basketProductData.id)
            .delete()
            .addOnSuccessListener {
                trySend(Result.success(Unit))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }

        awaitClose()
    }

    override fun removeAllProductsFromBasket(): Flow<Result<Unit>> = callbackFlow {

        awaitClose()
    }

    override fun incrementQuantityOfBasketProduct(basketProductData: BasketProductData): Flow<Result<Unit>> = callbackFlow {
        val documentRef = fb.collection("basket")
            .document(basketProductData.id)

        var quantity: Long?

        documentRef
            .get()
            .addOnSuccessListener {
                quantity = it.getLong("quantity")
                quantity?.let {
                    val incremented = it + 1
                    documentRef.update("quantity", incremented)
                        .addOnSuccessListener {
                            trySend(Result.success(Unit))
                        }
                        .addOnFailureListener {
                            trySend(Result.failure(it))
                        }
                }
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }

        awaitClose()
    }

    override fun decrementQuantityOfBasketProduct(basketProductData: BasketProductData): Flow<Result<Unit>> = callbackFlow {
        val documentRef = fb.collection("basket")
            .document(basketProductData.id)

        var quantity: Long?

        documentRef
            .get()
            .addOnSuccessListener {
                quantity = it.getLong("quantity")
                quantity?.let { quantity ->
                    val incremented = if (quantity > 1) quantity - 1 else 1
                    documentRef.update("quantity", incremented)
                        .addOnSuccessListener {
                            trySend(Result.success(Unit))
                        }
                        .addOnFailureListener {
                            trySend(Result.failure(it))
                        }
                }
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }

        awaitClose()
    }

    private suspend fun getAllCategories(): Result<List<CategoryResponse>> {
        val deferred = CompletableDeferred<Result<List<CategoryResponse>>>()
        fb.collection("categories")
            .get()
            .addOnSuccessListener { snapshot ->
                val list = ArrayList<CategoryResponse>()
                snapshot.forEach { document ->
                    list.add(
                        CategoryResponse(
                            id = document.id,
                            title = document.get("title") as String
                        )
                    )
                }
                deferred.complete(Result.success(list))
            }
            .addOnFailureListener {
                deferred.complete(Result.failure(it))
            }
        return deferred.await()
    }

    private suspend fun getAllProducts(): Result<List<ProductResponse>> {
        val deferred = CompletableDeferred<Result<List<ProductResponse>>>()
        fb.collection("products")
            .get()
            .addOnSuccessListener { snapshot ->
                val list = ArrayList<ProductResponse>()
                snapshot.forEach { document ->
                    list.add(
                        ProductResponse(
                            id = document.id,
                            categoryID = document.get("category_id") as String,
                            title = document.get("title") as String,
                            info = document.get("info") as String,
                            price = document.get("price") as Long,
                            imageUrl = document.get("imageUrl").toString()
                        )
                    )
                }
                deferred.complete(Result.success(list))
            }
            .addOnFailureListener {
                deferred.complete(Result.failure(it))
            }
        return deferred.await()
    }

    private suspend fun getCategoryTitle(categoryId: String): Result<String> {
        val deferred = CompletableDeferred<Result<String>>()
        fb.collection("categories").get().addOnSuccessListener {
            it.forEach { doc ->
                if (doc.id == categoryId) {
                    deferred.complete(Result.success(doc.get("title").toString()))
                }
            }
        }.addOnFailureListener {
            deferred.complete(Result.failure(it))
        }
        return deferred.await()
    }

    override fun searchForProducts(query: String): Flow<Result<List<ProductData>>> = callbackFlow {
        val uppercaseQuery = query.replaceFirstChar { it.uppercase() }
        fb.collection("products")
            .whereGreaterThanOrEqualTo("title", uppercaseQuery)
            .whereLessThanOrEqualTo("title", uppercaseQuery + "\uf8ff")
            .get()
            .addOnSuccessListener { query ->
                val list = ArrayList<ProductData>()
                query.forEach { doc ->
                    list.add(
                        ProductData(
                            id = doc.id,
                            title = doc.get("title").toString(),
                            info = doc.get("info").toString(),
                            price = doc.get("price") as Long,
                            imageUrl = doc.get("imageUrl").toString()
                        )
                    )
                }
                trySend(Result.success(list))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }

    override fun placeOrders(): Flow<Result<Unit>> = callbackFlow {
        val sourceRef = fb.collection("basket")
        val destinationRef = fb.collection("orders")

        sourceRef
            .get()
            .addOnSuccessListener { query ->
                query.forEach { doc ->
                    val data = doc.data
                    destinationRef.add(data)
                }
                sourceRef.get()
                    .addOnSuccessListener { querySource ->
                        querySource.forEach { docSource ->
                            docSource.reference.delete()
                        }
                        trySend(Result.success(Unit))
                    }
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }

        awaitClose()
    }

    override fun getAllOrders(): Flow<Result<List<BasketProductData>>> = callbackFlow {
        val user = auth.currentUser

        try {
            val orderSnapshot = fb.collection("orders")
                .whereEqualTo("user_id", user?.uid)
                .get()
                .await()

            val basketDataList = mutableListOf<BasketProductData>()

            orderSnapshot.forEach { doc ->

                val productId = doc.getString("product_id")
                val quantity = doc.getLong("quantity")?.toInt()

                if (productId != null && quantity != null) {
                    val productSnapshot = fb.collection("products")
                        .document(productId)
                        .get()
                        .await()

                    val productTitle = productSnapshot.getString("title")
                    val imageUrl = productSnapshot.getString("imageUrl")
                    val price = productSnapshot.getLong("price")?.toInt()

                    if (productTitle != null && imageUrl != null && price != null) {

                        val basketData = BasketProductData(
                            doc.id,
                            productId,
                            quantity,
                            productTitle,
                            imageUrl,
                            price
                        )
                        basketDataList.add(basketData)
                    }
                }
            }
            trySend(Result.success(basketDataList))

        } catch (e: Exception) {
            trySend(Result.failure(e))
        }
        awaitClose()
        awaitClose()
    }
}