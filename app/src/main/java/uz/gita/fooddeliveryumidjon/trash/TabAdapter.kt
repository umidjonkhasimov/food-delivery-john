package uz.gita.fooddeliveryumidjon.trash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.fooddeliveryumidjon.databinding.ItemTabBinding
import uz.gita.fooddeliveryumidjon.model.CategoryData

class TabAdapter : ListAdapter<CategoryData, TabAdapter.TabVH>(MyDiffUtil) {

    private object MyDiffUtil : DiffUtil.ItemCallback<CategoryData>() {
        override fun areItemsTheSame(oldItem: CategoryData, newItem: CategoryData) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: CategoryData, newItem: CategoryData) = oldItem.title == newItem.title
    }

    inner class TabVH(private val binding: ItemTabBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val currentItem = getItem(absoluteAdapterPosition)
            binding.apply {
                tvCategoryTitle.text = currentItem.title
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TabVH(ItemTabBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TabVH, position: Int) = holder.bind()
}