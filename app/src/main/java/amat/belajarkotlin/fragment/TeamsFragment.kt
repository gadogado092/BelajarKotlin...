package amat.belajarkotlin.fragment


import amat.belajarkotlin.*
import amat.belajarkotlin.adapter.TeamsAdapter
import amat.belajarkotlin.api.ApiRepository
import amat.belajarkotlin.detail.DetailActivityTeam
import amat.belajarkotlin.view.*
import amat.belajarkotlin.model.TeamModel
import amat.belajarkotlin.presenter.TeamsPresenter
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
class TeamsFragment : Fragment(), TeamsView {

    private  var idLeague: String? = null
    private lateinit var listTeams: RecyclerView
    private lateinit var presenter: TeamsPresenter
    private lateinit var adapter: TeamsAdapter
    private var teams : MutableList<TeamModel> = mutableListOf()
    private lateinit var progressBar: ProgressBar
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_last, container, false)

        progressBar = view.findViewById(R.id.progressBar)
        listTeams = view.findViewById(R.id.listLast)
        listTeams.layoutManager = LinearLayoutManager(activity)
        listTeams.setHasFixedSize(true)
        adapter = TeamsAdapter(view.context, teams) {
            val intent = Intent(view.context, DetailActivityTeam::class.java)
            val bundle = Bundle()
            bundle.putParcelable("selected_match", it)
            intent.putExtra("myBundle", bundle)
            startActivity(intent)
        }

        listTeams.adapter = adapter
        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamsPresenter(this, request, gson)
        idLeague = arguments!!.getString("idLeague")
        presenter.getTeams(idLeague)
        return view
    }



    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showMatchList(data: List<TeamModel>?, condition: Boolean) {
        teams.clear()
        if (data != null) {
            teams.addAll(data)
        }
        adapter.notifyDataSetChanged()
    }

}
