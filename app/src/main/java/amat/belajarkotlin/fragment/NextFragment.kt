package amat.belajarkotlin.fragment


import amat.belajarkotlin.*
import amat.belajarkotlin.api.ApiRepository
import amat.belajarkotlin.detail.DetailActivity
import amat.belajarkotlin.main.NextAdapter
import amat.belajarkotlin.main.NextPresenter
import amat.belajarkotlin.main.NextView
import amat.belajarkotlin.model.NextTeam
import amat.belajarkotlin.util.invisible
import amat.belajarkotlin.util.visible
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.google.gson.Gson



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 *
 */
class NextFragment : Fragment() , NextView {


    override fun showMatchList(data: List<NextTeam>) {
        match.clear()
        match.addAll(data)
        adapter.notifyDataSetChanged()
    }


    private lateinit var listMatch: RecyclerView
    private lateinit var presenter: NextPresenter
    private lateinit var adapter: NextAdapter
    private val match : MutableList<NextTeam> = mutableListOf()
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_next, container, false)
        listMatch = view.findViewById(R.id.listNext)
        progressBar = view.findViewById(R.id.progressBar)
        listMatch.layoutManager = LinearLayoutManager(activity)
        listMatch.setHasFixedSize(true)
        adapter = NextAdapter(view.context, match) {
            val intent = Intent(view.context, DetailActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("selected_match", it)
            intent.putExtra("myBundle", bundle)
            startActivity(intent)
        }
        listMatch.adapter = adapter
        val request = ApiRepository()
        val gson = Gson()
        presenter = NextPresenter(this, request, gson)
        presenter.getMatchNext()
        super.onCreate(savedInstanceState)
        return view
    }



    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }



}
