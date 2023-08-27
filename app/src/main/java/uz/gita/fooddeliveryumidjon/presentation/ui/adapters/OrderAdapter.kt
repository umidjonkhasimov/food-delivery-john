package uz.gita.fooddeliveryumidjon.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.fooddeliveryumidjon.R
import uz.gita.fooddeliveryumidjon.databinding.ItemOrderBinding
import uz.gita.fooddeliveryumidjon.model.BasketProductData

class OrderAdapter : ListAdapter<BasketProductData, OrderAdapter.OrderVH>(MyDiffUtil) {

    private object MyDiffUtil : DiffUtil.ItemCallback<BasketProductData>() {
        override fun areItemsTheSame(oldItem: BasketProductData, newItem: BasketProductData) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: BasketProductData, newItem: BasketProductData) = oldItem == newItem
    }

    inner class OrderVH(private val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = getItem(position)
            binding.apply {
                Glide.with(binding.root.context)
                    .load(item.imageUrl)
                    .placeholder(R.drawable.img_placeholder)
                    .into(imgProductOrder)

                tvProductTitleOrder.text = item.productTitle
                tvProductPriceOrder.text = "${item.price} sum"
                tvQuantityOrder.text = "Quantity: ${item.quantity}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        OrderVH(ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: OrderVH, position: Int) = holder.bind(position)
}