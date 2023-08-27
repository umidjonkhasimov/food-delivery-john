package uz.gita.fooddeliveryumidjon.presentation.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.fooddeliveryumidjon.R
import uz.gita.fooddeliveryumidjon.databinding.ItemSearchBinding
import uz.gita.fooddeliveryumidjon.model.ProductData

class SearchAdapter : ListAdapter<ProductData, SearchAdapter.SearchVH>(MyDiffUtil) {
    private var onItemClickListener: ((ProductData) -> Unit)? = null

    fun setOnItemClickListener(action: (ProductData) -> Unit) {
        onItemClickListener = action
    }

    private object MyDiffUtil : DiffUtil.ItemCallback<ProductData>() {
        override fun areItemsTheSame(oldItem: ProductData, newItem: ProductData) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: ProductData, newItem: ProductData) = oldItem == newItem
    }

    inner class SearchVH(private val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val item = getItem(position)

                root.setOnClickListener {
                    onItemClickListener?.invoke(item)
                }

                Glide.with(binding.root.context)
                    .load(item.imageUrl)
                    .placeholder(R.drawable.img_placeholder)
                    .into(imgProductSearch)

                tvTitleSearch.text = item.title
                tvPriceSearch.text = "${item.price} sum"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVH =
        SearchVH(ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: SearchVH, position: Int) = holder.bind(position)

}