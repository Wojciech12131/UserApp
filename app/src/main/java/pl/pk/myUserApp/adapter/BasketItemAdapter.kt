package pl.pk.myUserApp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.pk.myUserApp.R
import pl.pk.myUserApp.data.BasketItem

class BasketItemAdapter(private var basketItemList: List<BasketItem>) :
    RecyclerView.Adapter<BasketItemAdapter.Companion.BasketItemViewHolder>() {

    companion object {
        class BasketItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var productName: TextView = itemView.findViewById(R.id.prdName)
            var productUnitPriceAndQuantity: TextView = itemView.findViewById(R.id.price_x_quantity)
            var productTotalPrice: TextView = itemView.findViewById(R.id.totalPrice)
            var productDescription: TextView = itemView.findViewById(R.id.prdDescription)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.basket_item, parent, false)
        return BasketItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: BasketItemViewHolder, position: Int) {
        val basketItem = basketItemList[position]

        holder.productName.text = basketItem.name
        holder.productUnitPriceAndQuantity.text = basketItem.price.divide(basketItem.quantity).toString() + " X " + basketItem.quantity.toString()
        holder.productTotalPrice.text = basketItem.price.toString()
        holder.productDescription.text = basketItem.description
    }

    override fun getItemCount(): Int {
        return basketItemList.size
    }
}