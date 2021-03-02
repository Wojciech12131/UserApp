package pl.pk.myUserApp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.pk.myUserApp.R
import pl.pk.myUserApp.data.CouponDetails
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CouponsAdapter(private var couponsList: List<CouponDetails>) :
    RecyclerView.Adapter<CouponsAdapter.Companion.CouponsViewHolder>() {
    var dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")

    companion object {
        class CouponsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            public var couponNumber: TextView = itemView.findViewById(R.id.numerKarty)
            public var couponStatus: TextView = itemView.findViewById(R.id.nazwaStatusu)
            public var activeTo: TextView = itemView.findViewById(R.id.activeTo)
            public var useLimit: TextView = itemView.findViewById(R.id.useTime)
            public var description: TextView = itemView.findViewById(R.id.description)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coupon_item, parent, false)
        return CouponsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CouponsViewHolder, position: Int) {
        val coupon = couponsList[position]

        holder.couponNumber.text = coupon.code
        holder.couponStatus.text = coupon.status
        holder.activeTo.text = LocalDateTime.parse(coupon.couponType.activeTo,dateTimeFormatter).toLocalDate().toString()
        holder.useLimit.text = coupon.useTime.toString() + "/" + coupon.useLimit
        holder.description.text = coupon.couponType.description

    }

    override fun getItemCount(): Int {
        return couponsList.size
    }
}