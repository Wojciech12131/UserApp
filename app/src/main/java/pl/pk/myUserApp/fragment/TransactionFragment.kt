package pl.pk.myUserApp.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_transaction.*
import pl.pk.myUserApp.MainViewModel
import pl.pk.myUserApp.MainViewModelFactory
import pl.pk.myUserApp.R
import pl.pk.myUserApp.activity.TransactionDetails
import pl.pk.myUserApp.adapter.TransactionAdapter
import pl.pk.myUserApp.adapter.TransactionAdapter.OnItemClickListener
import pl.pk.myUserApp.data.Transactions
import pl.pk.myUserApp.repo.Repository
import java.lang.Exception
import java.math.BigDecimal

private const val ARG_TOKEN = "token"
private const val ARG_REPOSITORY = "repo"


class TransactionFragment : Fragment() {
    private lateinit var token: String
    private lateinit var repo: Repository
    private lateinit var transactions: List<Transactions>
    private lateinit var recyclerView: RecyclerView
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            token = it.getString(ARG_TOKEN) as String
            repo = it.getSerializable(ARG_REPOSITORY) as Repository
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

    override fun onStart() {
        super.onStart()

        recyclerView = transactionList
        recyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)

        val viewModelFactory = MainViewModelFactory(repo)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getMyTransactions(token)
        viewModel.transactions.observe(this, Observer { response ->
            if (response.isSuccessful) {
                transactions = response.body()!!
                transactionAdapter = TransactionAdapter(transactions)
                recyclerView.layoutManager = layoutManager

                recyclerView.adapter = transactionAdapter
                transactionAdapter.setOnItemClickListener(object : OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        val intent: Intent = Intent(context, TransactionDetails::class.java)
                        intent.putExtra("transaction", transactions[position])
                        startActivity(intent)
                    }
                })

            } else {
                AlertDialog.Builder(context)
                    .setTitle("Błąd przy pobieraniu transakcji")
                    .setMessage("Wystąpił błąd przy pobieraniu transakcji, prosimy spróbować póżniej. Przepraszamy za wszelkie problemy")
                    .setPositiveButton(
                        android.R.string.ok,
                        DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
            }
        })

    }

    companion object {
        @JvmStatic
        fun newInstance(token: String, repo: Repository) =
            TransactionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TOKEN, token)
                    putSerializable(ARG_REPOSITORY, repo)
                }
            }
    }
}