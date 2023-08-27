package uz.gita.fooddeliveryumidjon.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.fooddeliveryumidjon.databinding.ItemProductBinding
import uz.gita.fooddeliveryumidjon.model.ProductData

class ProductAdapter : ListAdapter<ProductData, ProductAdapter.ProductVH>(MyDiffUtil) {
    private var onItemClickListener: ((ProductData) -> Unit)? = null

    fun setOnItemClickListener(action: (ProductData) -> Unit) {
        onItemClickListener = action
    }

    private object MyDiffUtil : DiffUtil.ItemCallback<ProductData>() {
        override fun areItemsTheSame(oldItem: ProductData, newItem: ProductData) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: ProductData, newItem: ProductData) =
            oldItem.title == newItem.title || oldItem.info == newItem.info
    }

    inner class ProductVH(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val currentItem = getItem(position)
            binding.apply {
                root.setOnClickListener {
                    onItemClickListener?.invoke(currentItem)
                }

                tvTitle.text = currentItem.title
                tvInfo.text = currentItem.info
                tvPrice.text = currentItem.price.toString()
                Glide.with(root)
                    .load(currentItem.imageUrl)
                    .into(imagePlaceholder)
                if (position == currentList.size - 1) {
                    divider.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProductVH(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ProductVH, position: Int) = holder.bind(position)
}