package pl.pk.myUserApp.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_coupons.*
import pl.pk.myUserApp.MainViewModel
import pl.pk.myUserApp.MainViewModelFactory
import pl.pk.myUserApp.R
import pl.pk.myUserApp.adapter.CouponsAdapter
import pl.pk.myUserApp.data.CouponDetails
import pl.pk.myUserApp.repo.Repository

private const val ARG_TOKEN = "token"
private const val ARG_REPOSITORY = "repo"


class CouponsFragment : Fragment() {
    private lateinit var token: String
    private lateinit var repo: Repository
    private lateinit var coupons: List<CouponDetails>
    private lateinit var recyclerView: RecyclerView
    private lateinit var couponsAdapter: RecyclerView.Adapter<CouponsAdapter.Companion.CouponsViewHolder>
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
        return inflater.inflate(R.layout.fragment_coupons, container, false)
    }

    override fun onStart() {
        super.onStart()

        recyclerView = couponsList
        recyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)

        val viewModelFactory = MainViewModelFactory(repo)
        var viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getMyCoupons(token)
        viewModel.coupons.observe(this, Observer { response ->
            if(response.isSuccessful){
                coupons = response.body()!!
                couponsAdapter = CouponsAdapter(coupons)
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = couponsAdapter
            }else{
                AlertDialog.Builder(context)
                    .setTitle("Błąd przy pobieraniu kuponów")
                    .setMessage("Wystąpił błąd przy pobieraniu transakcji, prosimy spróbować póżniej. Przepraszamy za wszelkie problemy")
                    .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, which -> dialog.dismiss()})
            }
        })

    }

    companion object {
        @JvmStatic
        fun newInstance(token: String, repo: Repository) =
            CouponsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TOKEN, token)
                    putSerializable(ARG_REPOSITORY, repo)
                }
            }
    }
}