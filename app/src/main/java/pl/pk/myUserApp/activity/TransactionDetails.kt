package pl.pk.myUserApp.activity

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.pk.myUserApp.R
import pl.pk.myUserApp.adapter.BasketItemAdapter
import pl.pk.myUserApp.adapter.TrnCouponAdapter
import pl.pk.myUserApp.data.Transactions
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TransactionDetails : Activity() {

    private lateinit var closeButton: ImageView
    private lateinit var transaction: Transactions
    private lateinit var basketItemRecyclerView: RecyclerView
    private lateinit var basketItemAdapter: BasketItemAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var couponsRecyclerView: RecyclerView
    private lateinit var couponsAdapter: TrnCouponAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transaction_details_activity)
        transaction = intent.getSerializableExtra("transaction") as Transactions

        val displayMetrics: DisplayMetrics = DisplayMetrics()
        applicationContext.display?.getMetrics(displayMetrics)
        val width: Int = displayMetrics.widthPixels
        val height: Int = displayMetrics.heightPixels
        window.setLayout((width * .8).toInt(), (height * .7).toInt())
        val params = window.attributes
        params.gravity = Gravity.CENTER
        params.x = 0
        params.y = -20
        window.attributes = params

        setUpButtonAndTextField()

    }

    private fun setUpButtonAndTextField() {
        closeButton = findViewById(R.id.closeButton)
        closeButton.setOnClickListener {
            finish()
        }
        findViewById<TextView>(R.id.trnId).text = transaction.id.toString()
        findViewById<TextView>(R.id.trnData).text =
            LocalDateTime.parse(transaction.createdOn).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .toString()
        findViewById<TextView>(R.id.trnType).text = transaction.trnType
        findViewById<TextView>(R.id.trnTotalPrice).text = transaction.totalPrice.toString()
        findViewById<TextView>(R.id.trnDiscount).text = transaction.discount.toString()
        findViewById<TextView>(R.id.finalPrice).text = transaction.finalAmount.toString()
        findViewById<TextView>(R.id.grantedPoints).text = transaction.grantedPoints.toString()
        findViewById<TextView>(R.id.issuedPoints).text = transaction.issuedPoints.toString()

        basketItemRecyclerView = findViewById(R.id.basketList)
        basketItemRecyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(baseContext)
        basketItemAdapter = BasketItemAdapter(transaction.basket)
        basketItemRecyclerView.layoutManager = layoutManager
        basketItemRecyclerView.adapter = basketItemAdapter

        couponsRecyclerView = findViewById(R.id.TrnCoupons)
        couponsRecyclerView.setHasFixedSize(true)
        couponsAdapter = TrnCouponAdapter(transaction.trnCoupons)
        couponsRecyclerView.layoutManager = LinearLayoutManager(baseContext)
        couponsRecyclerView.adapter = couponsAdapter


    }

}
