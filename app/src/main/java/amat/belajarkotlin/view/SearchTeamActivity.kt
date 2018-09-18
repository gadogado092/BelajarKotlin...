package amat.belajarkotlin.view

import amat.belajarkotlin.R
import amat.belajarkotlin.api.ApiRepository
import amat.belajarkotlin.detail.DetailActivityTeam
import amat.belajarkotlin.adapter.TeamsAdapter
import amat.belajarkotlin.presenter.TeamsPresenter
import amat.belajarkotlin.model.TeamModel
import amat.belajarkotlin.util.invisible
import amat.belajarkotlin.util.visible
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import android.widget.Toast
import com.google.gson.Gson

class SearchTeamActivity : AppCompatActivity(),TeamsView {

    private lateinit var listTeams: RecyclerView
    private lateinit var presenter: TeamsPresenter
    private lateinit var adapter: TeamsAdapter
    private var teams : MutableList<TeamModel> = mutableListOf()
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serach_team)

        progressBar = findViewById(R.id.progressBar)
        listTeams = findViewById(R.id.listLast)
        listTeams.layoutManager = LinearLayoutManager(applicationContext)
        listTeams.setHasFixedSize(true)
        adapter = TeamsAdapter(applicationContext, teams) {
            val intent = Intent(applicationContext, DetailActivityTeam::class.java)
            val bundle = Bundle()
            bundle.putParcelable("selected_match", it)
            intent.putExtra("myBundle", bundle)
            startActivity(intent)
        }

        listTeams.adapter = adapter
        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamsPresenter(this, request, gson)

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        menu.findItem(R.id.action_favorite).isVisible = false
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchItem.expandActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.searchTeam(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.equals("")){

                }else{

                }
                presenter.searchTeam(newText)

                return false
            }

        })
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                // Do whatever you need
                return true // KEEP IT TO TRUE OR IT DOESN'T OPEN !!
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }
        })
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
        listTeams.visible()
    }

    override fun showMatchList(data: List<TeamModel>, condition: Boolean) {
        if (condition){
            teams.clear()
            teams.addAll(data)
            adapter.notifyDataSetChanged()
        }else{
            Toast.makeText(applicationContext,"Empty List", Toast.LENGTH_SHORT).show()
            teams.clear()
            adapter.notifyDataSetChanged()
        }
    }
}
