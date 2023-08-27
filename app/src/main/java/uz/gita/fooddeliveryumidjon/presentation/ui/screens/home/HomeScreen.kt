package uz.gita.fooddeliveryumidjon.presentation.ui.screens.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.get
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import uz.gita.fooddeliveryumidjon.R
import uz.gita.fooddeliveryumidjon.databinding.ItemTabBinding
import uz.gita.fooddeliveryumidjon.databinding.ScreenHomeBinding
import uz.gita.fooddeliveryumidjon.model.CategoryData
import uz.gita.fooddeliveryumidjon.model.ProductData
import uz.gita.fooddeliveryumidjon.presentation.ui.adapters.CategoryAdapter
import uz.gita.fooddeliveryumidjon.presentation.ui.adapters.SearchAdapter
import uz.gita.fooddeliveryumidjon.presentation.ui.screens.home.viewmodel.HomeScreenViewModel
import uz.gita.fooddeliveryumidjon.presentation.ui.screens.main.MainScreenDirections

class HomeScreen : Fragment(R.layout.screen_home) {
    private val binding by viewBinding(ScreenHomeBinding::bind)
    private val viewModel: HomeScreenViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var searchAdapter: SearchAdapter
    private var isFirstTime = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachObservers()
        setTabLayout()
        viewModel.getCategories()
        categoryAdapter = CategoryAdapter()

        categoryAdapter.setOnItemClickListener { productData ->
            val action = MainScreenDirections.actionScreenHomeToDetailsScreen(productData)
            findNavController().navigate(action)
        }

        val adapter = ConcatAdapter(categoryAdapter)

        binding.apply {
            mRecyclerView.adapter = adapter
            mRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            setUpSearch()
        }
    }

    private fun setUpSearch() {
        binding.apply {
            etSearchHome.doAfterTextChanged { text ->
                if (!text.isNullOrBlank() && text.length > 2) {
                    tabLayout.visibility = View.GONE
                    searchAdapter = SearchAdapter()
                    searchAdapter.setOnItemClickListener {
                        val action = MainScreenDirections.actionScreenHomeToDetailsScreen(it)
                        findNavController().navigate(action)
                    }
                    mRecyclerView.adapter = searchAdapter
                    viewModel.searchForProduct(text.toString())
                } else {
                    tabLayout.visibility = View.VISIBLE
                    mRecyclerView.adapter = categoryAdapter
                    viewModel.getCategories()
                }
            }
        }
    }

    private fun setTabLayout() {
        binding.apply {
            val item = ItemTabBinding.inflate(LayoutInflater.from(requireContext()))
            item.tvCategoryTitle.text = "All"
            tabLayout.addTab(tabLayout.newTab().setCustomView(item.root))
            tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab?.tag != null) {
                        val categoryId = tab.tag as String
                        viewModel.getCategoriesById(categoryId)
                    } else {
                        viewModel.getCategories()
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    tabLayout.getTabAt(0)?.select()
                    viewModel.getCategories()
                }
            })

        }
    }

    private fun attachObservers() {
        viewModel.categoriesLD.observe(viewLifecycleOwner, categoriesObserver)
        viewModel.showProgressBarLD.observe(viewLifecycleOwner, showProgressBarObserver)
        viewModel.errorHandlerLD.observe(viewLifecycleOwner, errorHandlingObserver)
        viewModel.searchProductsLD.observe(viewLifecycleOwner, searchProductsObserver)
    }

    private val searchProductsObserver = Observer<List<ProductData>?> { list ->
        if (list != null) {
            searchAdapter.submitList(list)
        }
    }

    private val categoriesObserver = Observer<List<CategoryData>> { list ->
        if (isFirstTime) {
            isFirstTime = false
            list.forEach { category ->
                binding.apply {
                    val item = ItemTabBinding.inflate(LayoutInflater.from(requireContext()))
                    val tab = tabLayout.newTab().setCustomView(item.root)
                    tab.tag = category.id
                    item.tvCategoryTitle.text = category.title
                    tabLayout.addTab(tab)
                }
            }
        }
        categoryAdapter.submitList(list)
    }

    private val showProgressBarObserver = Observer<Boolean> { isShown ->
        if (isShown) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private val errorHandlingObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }
}