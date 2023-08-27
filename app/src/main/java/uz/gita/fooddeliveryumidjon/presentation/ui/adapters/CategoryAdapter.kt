package uz.gita.fooddeliveryumidjon.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.fooddeliveryumidjon.databinding.ItemCategoryBinding
import uz.gita.fooddeliveryumidjon.model.CategoryData
import uz.gita.fooddeliveryumidjon.model.ProductData

class CategoryAdapter : ListAdapter<CategoryData, CategoryAdapter.CategoryVH>(MyDiffUtil) {
    private var onItemClickListener: ((ProductData) -> Unit)? = null

    fun setOnItemClickListener(action: (ProductData) -> Unit) {
        onItemClickListener = action
    }

    private object MyDiffUtil : DiffUtil.ItemCallback<CategoryData>() {
        override fun areItemsTheSame(oldItem: CategoryData, newItem: CategoryData) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: CategoryData, newItem: CategoryData) = oldItem.title == newItem.title
    }

    inner class CategoryVH(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val currentItem = getItem(adapterPosition)
            binding.apply {
                tvCategory.text = currentItem.title
                val adapter = ProductAdapter()
                adapter.submitList(currentItem.products)

                adapter.setOnItemClickListener { productData ->
                    onItemClickListener?.invoke(productData)
                }

                mRecyclerViewCat.adapter = adapter
                mRecyclerViewCat.layoutManager = LinearLayoutManager(root.context)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH =
        CategoryVH(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: CategoryVH, position: Int) {
        holder.bind()
    }
}