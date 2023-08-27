package uz.gita.fooddeliveryumidjon.presentation.ui.screens.details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import uz.gita.fooddeliveryumidjon.R
import uz.gita.fooddeliveryumidjon.databinding.ScreenDetailsBinding
import uz.gita.fooddeliveryumidjon.model.BasketProductData
import uz.gita.fooddeliveryumidjon.presentation.ui.screens.details.viewmodel.DetailViewModel

class DetailsScreen : Fragment(R.layout.screen_details) {
    private val binding: ScreenDetailsBinding by viewBinding(ScreenDetailsBinding::bind)
    private val viewModel by viewModels<DetailViewModel>()
    private val navArgs by navArgs<DetailsScreenArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = navArgs.productData
        viewModel.quantity.observe(viewLifecycleOwner, quantityObserver)
        viewModel.showProgressLiveData.observe(viewLifecycleOwner, showProgressObserver)
        viewModel.isProductInTheBasketLD.observe(viewLifecycleOwner, isInTheBasketObserver)

        viewModel.isProductInTheBasket(data)

        setClickListeners()

        binding.apply {
            Glide.with(requireContext())
                .load(data.imageUrl)
                .into(imgProduct)
            tvProductTitle.text = data.title
            tvTitleProduct.text = data.title
            tvInfoProduct.text = data.info
            tvPriceProduct.text = data.price.toString()
            tvPriceDetail.text = data.price.toString()
        }
    }

    private val isInTheBasketObserver = Observer<BasketProductData?> {
        it?.let { basketProductData ->
            binding.apply {
                tvQuantityDetail.text = basketProductData.quantity.toString()
                tvPriceDetail.text = (basketProductData.quantity * navArgs.productData.price).toString()
                btnAddToCartDetail.text = "Go to basket"
                btnAddToCartDetail.setOnClickListener {

                }
            }
        }
    }

    private val quantityObserver = Observer<Int> {
        binding.tvQuantityDetail.text = it.toString()
        binding.tvPriceDetail.text = (it * navArgs.productData.price).toString()
    }

    private val showProgressObserver = Observer<Boolean> {
        if (it)
            binding.progressBarDetail.visibility = View.VISIBLE
        else
            binding.progressBarDetail.visibility = View.GONE
    }

    private fun setClickListeners() {
        binding.apply {
            btnBackDetail.setOnClickListener {
                findNavController().popBackStack()
            }

            btnAddToCartDetail.setOnClickListener {
                viewModel.addProductToBasket(navArgs.productData)
            }

            btnIncrementDetail.setOnClickListener {
                viewModel.incrementQuantity()
            }

            btnDecrementDetail.setOnClickListener {
                viewModel.decrementQuantity()
            }
        }
    }
}