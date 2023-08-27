package uz.gita.fooddeliveryumidjon.trash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.fooddeliveryumidjon.databinding.ItemCategoryTabBinding
import uz.gita.fooddeliveryumidjon.model.CategoryData

class CategoryTabAdapter : ListAdapter<CategoryData, CategoryTabAdapter.CategoryTabVH>(MyDiffUtil) {

    private object MyDiffUtil : DiffUtil.ItemCallback<CategoryData>() {
        override fun areItemsTheSame(oldItem: CategoryData, newItem: CategoryData) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: CategoryData, newItem: CategoryData) = oldItem.title == newItem.title
    }

    inner class CategoryTabVH(private val binding: ItemCategoryTabBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val currentItem = getItem(absoluteAdapterPosition)
            binding.apply {
//                val adapter = TabAdapter()
//                adapter.submitList(currentList)
//                mRecyclerViewTab.adapter = adapter
//                mRecyclerViewTab.layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CategoryTabVH(ItemCategoryTabBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: CategoryTabVH, position: Int) = holder.bind()
}