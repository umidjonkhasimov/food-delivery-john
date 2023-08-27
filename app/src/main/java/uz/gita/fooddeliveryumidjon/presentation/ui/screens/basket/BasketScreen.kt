package uz.gita.fooddeliveryumidjon.presentation.ui.screens.basket

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.fooddeliveryumidjon.R
import uz.gita.fooddeliveryumidjon.databinding.ScreenBasketBinding
import uz.gita.fooddeliveryumidjon.model.BasketProductData
import uz.gita.fooddeliveryumidjon.presentation.ui.adapters.BasketAdapter
import uz.gita.fooddeliveryumidjon.presentation.ui.screens.basket.viewmodel.BasketViewModel

class BasketScreen : Fragment(R.layout.screen_basket) {
    private val binding: ScreenBasketBinding by viewBinding(ScreenBasketBinding::bind)
    private val viewModel: BasketViewModel by viewModels()
    private lateinit var adapter: BasketAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = BasketAdapter()
        viewModel.allBasketProductsLiveData.observe(viewLifecycleOwner, allBasketProductsObserver)
        viewModel.showProgressLiveData.observe(viewLifecycleOwner, showProgressObserver)

        setOnClickListeners()

        binding.apply {
            rvBasket.adapter = adapter
            rvBasket.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setOnClickListeners() {
        adapter.setOnCancelClick { basketProductData ->
            viewModel.removeProductFromBasket(basketProductData)
        }

        adapter.setOnIncrementClick { basketProductData ->
            viewModel.incrementQuantityOfBasketProduct(basketProductData)
        }

        adapter.setOnDecrementClick { basketProductData ->
            viewModel.decrementQuantityOfBasketProduct(basketProductData)
        }

        binding.apply {
            btnPlaceOrder.setOnClickListener {
                viewModel.placeOrders()
            }
        }
    }

    private val allBasketProductsObserver = Observer<List<BasketProductData>> { list ->
        if (list.isEmpty()) {
            binding.apply {
                imagePlaceholderBasket.visibility = View.VISIBLE
                rvBasket.visibility = View.GONE
                barPlaceOrder.visibility = View.GONE
            }
        } else {
            binding.apply {
                imagePlaceholderBasket.visibility = View.GONE
                rvBasket.visibility = View.VISIBLE
                barPlaceOrder.visibility = View.VISIBLE
                calculateOverallPrice(list)
                adapter.submitList(list)
            }
        }
    }

    private fun calculateOverallPrice(list: List<BasketProductData>) {
        var overallPrice = 0
        list.forEach { basketProductData ->
            overallPrice += basketProductData.price * basketProductData.quantity
        }
        binding.tvOverallPriceBasket.text = overallPrice.toString()
    }

    private val showProgressObserver = Observer<Boolean> {
        if (it)
            binding.progressBarBasket.visibility = View.VISIBLE
        else
            binding.progressBarBasket.visibility = View.GONE
    }
}