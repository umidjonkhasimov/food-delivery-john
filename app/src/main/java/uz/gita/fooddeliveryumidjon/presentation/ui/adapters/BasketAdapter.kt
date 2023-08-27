package uz.gita.fooddeliveryumidjon.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.fooddeliveryumidjon.R
import uz.gita.fooddeliveryumidjon.databinding.BasketItemBinding
import uz.gita.fooddeliveryumidjon.model.BasketProductData
import uz.gita.fooddeliveryumidjon.model.ProductData

class BasketAdapter : ListAdapter<BasketProductData, BasketAdapter.BasketAdapterVH>(MyDiffUtil) {
    private var onCancelClick: ((BasketProductData) -> Unit)? = null
    private var onIncrementClick: ((BasketProductData) -> Unit)? = null
    private var onDecrementClick: ((BasketProductData) -> Unit)? = null

    fun setOnCancelClick(action: (BasketProductData) -> Unit) {
        onCancelClick = action
    }

    fun setOnIncrementClick(action: (BasketProductData) -> Unit) {
        onIncrementClick = action
    }

    fun setOnDecrementClick(action: (BasketProductData) -> Unit) {
        onDecrementClick = action
    }

    inner class BasketAdapterVH(private val binding: BasketItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val item = getItem(position)
            binding.apply {
                val currentItem = getItem(position)
                btnCancelBasket.setOnClickListener {
                    onCancelClick?.invoke(currentItem)
                }

                btnIncrementBasket.setOnClickListener {
                    onIncrementClick?.invoke(currentItem)
                }

                btnDecrementBasket.setOnClickListener {
                    onDecrementClick?.invoke(currentItem)
                }
            }

            binding.apply {
                Glide.with(binding.root.context)
                    .load(item.imageUrl)
                    .placeholder(R.drawable.img_placeholder)
                    .into(imgProductBasket)

                tvQuantityBasket.text = item.quantity.toString()
                tvProductTitleBasket.text = item.productTitle
                tvProductPriceBasket.text = "${item.price * item.quantity} sum"
            }
        }
    }

    private object MyDiffUtil : DiffUtil.ItemCallback<BasketProductData>() {
        override fun areItemsTheSame(oldItem: BasketProductData, newItem: BasketProductData) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: BasketProductData, newItem: BasketProductData) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketAdapterVH =
        BasketAdapterVH(BasketItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: BasketAdapterVH, position: Int) = holder.bind(position)
}