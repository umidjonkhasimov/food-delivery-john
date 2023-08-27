package uz.gita.fooddeliveryumidjon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.gita.fooddeliveryumidjon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
//    private fun uploadData() {
//        binding.apply {
//            btnAddDessert.setOnClickListener {
//                val list = loadDesserts()
//                list.forEach {
//                    db.collection("desserts")
//                        .add(it)
//                        .addOnSuccessListener { documentReference ->
//                            Log.d("TTT", "${it.title} -> DocumentSnapshot added with ID: ${documentReference.id}")
//                        }
//                        .addOnFailureListener { e ->
//                            Log.w("TTT", "Error adding document for ${it.title}", e)
//                        }
//                }
//
//            }
//            btnAddDrink.setOnClickListener {
//                val list = loadDrinks()
//                list.forEach {
//                    db.collection("drinks")
//                        .add(it)
//                        .addOnSuccessListener { documentReference ->
//                            Log.d("TTT", "${it.title} -> DocumentSnapshot added with ID: ${documentReference.id}")
//                        }
//                        .addOnFailureListener { e ->
//                            Log.w("TTT", "Error adding document for ${it.title}", e)
//                        }
//                }
//
//            }
//            btnAddFastFood.setOnClickListener {
//                val list = loadFastFood()
//                list.forEach {
//                    db.collection("fast-food")
//                        .add(it)
//                        .addOnSuccessListener { documentReference ->
//                            Log.d("TTT", "${it.title} -> DocumentSnapshot added with ID: ${documentReference.id}")
//                        }
//                        .addOnFailureListener { e ->
//                            Log.w("TTT", "Error adding document for ${it.title}", e)
//                        }
//                }
//
//            }
//            btnAddSauces.setOnClickListener {
//                val list = loadSauces()
//                list.forEach {
//                    db.collection("sauces")
//                        .add(it)
//                        .addOnSuccessListener { documentReference ->
//                            Log.d("TTT", "${it.title} -> DocumentSnapshot added with ID: ${documentReference.id}")
//                        }
//                        .addOnFailureListener { e ->
//                            Log.w("TTT", "Error adding document for ${it.title}", e)
//                        }
//                }
//
//            }
//            btnAddSnacks.setOnClickListener {
//                val list = loadSnacks()
//                list.forEach {
//                    db.collection("snacks")
//                        .add(it)
//                        .addOnSuccessListener { documentReference ->
//                            Log.d("TTT", "${it.title} -> DocumentSnapshot added with ID: ${documentReference.id}")
//                        }
//                        .addOnFailureListener { e ->
//                            Log.w("TTT", "Error adding document for ${it.title}", e)
//                        }
//                }
//
//            }
//        }
//    }
//
//    private fun loadDesserts(): ArrayList<DessertData> {
//        val list = ArrayList<DessertData>()
//        list.add(DessertData("Brauni", "Dark Chocolate and chocolate bisquete", 16_000.0, ""))
//        list.add(DessertData("Tiramisu", "Cookie, mascarapone, coffee", 16_000.0, ""))
//        list.add(DessertData("San-Sebastian", "Milk chocolate, cream-cheese, bisquete", 20_000.0, ""))
//        return list
//    }
//
//    private fun loadDrinks(): ArrayList<DrinksData> {
//        val list = ArrayList<DrinksData>()
//        list.add(DrinksData("Bonaqua", "Bonaqua", 3000.0, ""))
//        list.add(DrinksData("Coca-cola", "Coca-cola", 8000.0, ""))
//        list.add(DrinksData("Black tea", "Black tea", 3000.0, ""))
//        list.add(DrinksData("Green tea", "Green tea", 3000.0, ""))
//        list.add(DrinksData("Quyma cola", "Quyma cola", 6000.0, ""))
//        list.add(DrinksData("Lemon Tea", "Lemon Tea", 6000.0, ""))
//        list.add(DrinksData("Disposable cup", "Disposable cup", 1000.0, ""))
//        list.add(DrinksData("Americano", "Americano", 10_000.0, ""))
//        list.add(DrinksData("Cappuccino", "Cappuccino", 12_000.0, ""))
//        list.add(DrinksData("Latte", "Latte", 12_000.0, ""))
//        list.add(DrinksData("Dena juice", "Dena juice", 15_000.0, ""))
//        return list
//    }
//
//    private fun loadFastFood(): ArrayList<FastFoodData> {
//        val list = ArrayList<FastFoodData>()
//        list.add(FastFoodData("Club Sandwich Chicken", "toaster bread, chicken schnitzel, fresh cucumbers", 28_000.0, ""))
//        list.add(FastFoodData("Sandwich Original", "toaster bread, chicken schnitzel, fresh cucumbers", 22_000.0, ""))
//        list.add(FastFoodData("Lavash", "lavash, donar beef meat, tomato, chips", 26_000.0, ""))
//        list.add(FastFoodData("Lavash mini", "toaster bread, chicken schnitzel, fresh cucumbers", 22_000.0, ""))
//        list.add(FastFoodData("Lavash chicken", "toaster bread, chicken schnitzel, fresh cucumbers", 24_000.0, ""))
//        list.add(FastFoodData("Shawarma standart", "donar bun, donar beef meat, pickles, tomatoes", 25_000.0, ""))
//
//        list.add(FastFoodData("Donar Kebab", "beef donar meat, rice with corn, french fries, red cabbage salad", 38_000.0, ""))
//        list.add(FastFoodData("Donar box", "donar beef meat, french fries, tomatoes, special sauce", 35_000.0, ""))
//        list.add(FastFoodData("Turkish bread", "turkish bread", 3_000.0, ""))
//
//        list.add(FastFoodData("Hamburger", "hamburger bun, pickles, tomatoes, meat patty, red onion", 20_000.0, ""))
//        list.add(FastFoodData("Cheeseburger", "hamburger bun, pickles, tomatoes, meat patty, red onion", 23_000.0, ""))
//        list.add(FastFoodData("Bigburger", "hamburger bun, pickles, 2 meat patties, tomatoes, meat patty, red onion", 32_000.0, ""))
//
//        list.add(FastFoodData("Longer", "hot-dog bun, sauce, tomatoes, canadian sausage, ketchup", 15_000.0, ""))
//        list.add(FastFoodData("Hot Dog", "hot-dog bun, sauce, tomatoes, canadian sausage, ketchup", 11_000.0, ""))
//        list.add(FastFoodData("King Dog", "hot-dog bun, sauce, tomatoes, canadian sausage, ketchup", 18_000.0, ""))
//
//        return list
//    }
//
//    private fun loadSauces(): ArrayList<SaucesData> {
//        val list = ArrayList<SaucesData>()
//        list.add(SaucesData("Ketchup", "Ketchup", 2_000.0, ""))
//        list.add(SaucesData("Cheese Sauce", "Cheese Sauce", 2_000.0, ""))
//        list.add(SaucesData("Garlic Sauce", "Garlic Sauce", 2_000.0, ""))
//        list.add(SaucesData("Chili", "Chili", 2_000.0, ""))
//        list.add(SaucesData("Ranch Sauce", "Ranch Sauce", 2_000.0, ""))
//        list.add(SaucesData("Special Sauce", "Special Sauce", 2_000.0, ""))
//
//        return list
//    }
//
//    private fun loadSnacks(): ArrayList<SnacksData> {
//        val list = ArrayList<SnacksData>()
//        list.add(SnacksData("French Fries", "French Fries 60g", 8_000.0, ""))
//        list.add(SnacksData("Rustic Potatoes", "Rustic Potatoes 100g", 13_000.0, ""))
//        list.add(SnacksData("BOX", "French Fries (100g), Naggets(5pcs)", 20_000.0, ""))
//        list.add(SnacksData("Strips", "Strips", 14_000.0, ""))
//        list.add(SnacksData("Nuggets", "Nuggets 6pcs", 12_000.0, ""))
//
//        return list
//    }
}