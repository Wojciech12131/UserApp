package pl.pk.myUserApp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import pl.pk.myUserApp.R
import pl.pk.myUserApp.data.Transactions
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TransactionAdapter(private var transactions: List<Transactions>) :
    Adapter<TransactionAdapter.Companion.TransactionViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    public interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    companion object {
        class TransactionViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
            public var transactionData: TextView = itemView.findViewById(R.id.transactionDate)
            public var transactionType: TextView = itemView.findViewById(R.id.transactioType)
            public var grantedPoints: TextView = itemView.findViewById(R.id.grantedPoints)
            public var finalAmount: TextView = itemView.findViewById(R.id.finalAmount)

            init {
                itemView.setOnClickListener(View.OnClickListener {
                    if (listener != null) {
                        var position: Int = adapterPosition
                        if (position != RecyclerView.NO_POSITION) {
                                listener.onItemClick(position)
                        }
                    }
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_item, parent, false)
        return TransactionViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]

        holder.transactionData.text =
            LocalDateTime.parse(transaction.createdOn)
                .format(
                    DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")
                )
                .toString()
        holder.transactionType.text = transaction.trnType
        holder.grantedPoints.text = transaction.grantedPoints.toString()
        holder.finalAmount.text = transaction.finalAmount.toString()
    }

    override fun getItemCount(): Int {
        return transactions.size
    }
}