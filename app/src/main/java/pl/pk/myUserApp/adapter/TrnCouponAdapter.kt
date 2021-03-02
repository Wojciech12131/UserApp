package pl.pk.myUserApp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import kotlinx.android.synthetic.main.transaction_coupon_item.view.*
import pl.pk.myUserApp.R
import pl.pk.myUserApp.data.Coupons

class TrnCouponAdapter(private var trnCouponList: List<Coupons>) :
    Adapter<TrnCouponAdapter.Companion.TrnCouponViewHolder>() {

    companion object {
        class TrnCouponViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var cpnNumber: TextView = itemView.findViewById(R.id.cpnNumber)
            var useTime: TextView = itemView.findViewById(R.id.trnCouponUseTime)
            var cpnDetails: TextView = itemView.findViewById(R.id.cpnDetails)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrnCouponViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_coupon_item, parent, false)
        return TrnCouponViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrnCouponViewHolder, position: Int) {
        val coupons = trnCouponList[position]

        holder.cpnNumber.text = coupons.couponCode.toString()
        if (coupons.action.equals("NOT_EXISTED")) {
            holder.useTime.text = null
        } else {
            holder.useTime.text = "Kupon użytko ${coupons.useTime} razy"
        }
        when (coupons.action) {
            "ISSUE" -> holder.cpnDetails.text = "Zdobyto nowy kupon"
            "USED" -> holder.cpnDetails.text = "Kupon został użyty"
            "NOT_USED" -> holder.cpnDetails.text = "Kupon nie został użyty"
            "NOT_EXISTED" -> holder.cpnDetails.text = "Kupon o podanym numerze nie istnieje"
            "EXPIRED" -> holder.cpnDetails.text = "Kupon nieważny"
            "GENERATED" -> holder.cpnDetails.text = "Kupon nie przypisany do konta"
            "ALREADY_USED" -> holder.cpnDetails.text = "Kupon już został wykorzystany"
            else -> {
                holder.cpnDetails.text = "Nierozpoznana akcja"
            }
        }

    }

    override fun getItemCount(): Int {
        return trnCouponList.size
    }
}