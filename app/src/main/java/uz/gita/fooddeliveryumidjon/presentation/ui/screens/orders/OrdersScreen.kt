package uz.gita.fooddeliveryumidjon.presentation.ui.screens.orders

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.fooddeliveryumidjon.R
import uz.gita.fooddeliveryumidjon.databinding.ScreenOrdersBinding
import uz.gita.fooddeliveryumidjon.model.BasketProductData
import uz.gita.fooddeliveryumidjon.presentation.ui.adapters.OrderAdapter
import uz.gita.fooddeliveryumidjon.presentation.ui.screens.orders.viewmodel.OrdersScreenViewModel

class OrdersScreen : Fragment(R.layout.screen_orders) {
    private val binding: ScreenOrdersBinding by viewBinding(ScreenOrdersBinding::bind)
    private val viewModel by viewModels<OrdersScreenViewModel>()
    private lateinit var adapter: OrderAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = OrderAdapter()

        binding.apply {
            rvOrders.adapter = adapter
            rvOrders.layoutManager = LinearLayoutManager(context)
        }

        viewModel.showProgressLD.observe(viewLifecycleOwner, showProgressObserver)
        viewModel.allOrders.observe(viewLifecycleOwner, allOrdersObserver)
        viewModel.errorHandler.observe(viewLifecycleOwner, errorHandlerObserver)
    }

    private val allOrdersObserver = Observer<List<BasketProductData>> {
        if (it.isEmpty()) {
            binding.imagePlaceholderOrder.visibility = View.VISIBLE
        } else {
            binding.imagePlaceholderOrder.visibility = View.GONE
            adapter.submitList(it)
        }
    }

    private val errorHandlerObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private val showProgressObserver = Observer<Boolean> {
        if (it)
            binding.progressBarOrder.visibility = View.VISIBLE
        else
            binding.progressBarOrder.visibility = View.GONE
    }
}