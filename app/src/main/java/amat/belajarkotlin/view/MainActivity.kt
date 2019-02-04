package amat.belajarkotlin.view

import amat.belajarkotlin.R
import amat.belajarkotlin.adapter.MatchAdapter
import amat.belajarkotlin.api.ApiRepository
import amat.belajarkotlin.api.MatchApi
import amat.belajarkotlin.model.LeagueModel
import amat.belajarkotlin.model.LeagueResponse
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main2.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter

import amat.belajarkotlin.detail.DetailActivity
import amat.belajarkotlin.fragment.*
import amat.belajarkotlin.model.MatchModel
import amat.belajarkotlin.presenter.MatchPresenter
import amat.belajarkotlin.util.invisible
import amat.belajarkotlin.util.visible
import android.view.Menu
import android.widget.Toast
import android.content.Intent
import android.support.v7.widget.SearchView
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView


class MainActivity : AppCompatActivity(),MatchView{



    private var conditionLeague:Int =1
    private var idLeague: String? = null
    private var idLeague2: String? = null
    private var league : MutableList<LeagueModel> = mutableListOf()

    private val  spinnerMap = HashMap<Int, String>()
    private val  spinnerMap2 = HashMap<Int, String>()
    private lateinit var listMatch: RecyclerView
    private lateinit var presenter: MatchPresenter
    private lateinit var adapter: MatchAdapter
    private var match : MutableList<MatchModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val arraySpinner = arrayOf("Match", "Team")
        val adapter2 = ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_item, arraySpinner)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = adapter2
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent!!.getItemAtPosition(position).toString()
                if (selectedItem == "Match") {
                    val fragment = FavoriteFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.fragment_container, fragment)
                    fragmentTransaction.commit()
                }else{
                    val fragment = FavoriteTeamFragment()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.fragment_container, fragment)
                    fragmentTransaction.commit()
                }
            }

        }


        listMatch = findViewById(R.id.listMatch)
        listMatch.layoutManager = LinearLayoutManager(applicationContext)
        listMatch.setHasFixedSize(true)
        adapter = MatchAdapter(applicationContext, match) {
            val intent = Intent(applicationContext, DetailActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("selected_match", it)
            intent.putExtra("myBundle", bundle)
            startActivity(intent)
        }
        listMatch.adapter = adapter
        val request = ApiRepository()
        val gson = Gson()
        presenter = MatchPresenter(this, request, gson)
        listMatch.invisible()
        progressBar.invisible()

        getListLeague()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        spinner.setSelection(0)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (conditionLeague==1){
                    idLeague = spinnerMap.get(position).toString()
                    idLeague2 = spinnerMap2.get(position).toString()
                    val fragment = LastFragment()
                    val args = Bundle()
                    args.putString("idLeague", idLeague)
                    fragment.setArguments(args)
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.fragment_container, fragment)
                    fragmentTransaction.commit()
                }else if (conditionLeague==2){
                    idLeague = spinnerMap.get(position).toString()
                    idLeague2 = spinnerMap2.get(position).toString()
                    val fragment = NextFragment()
                    val args = Bundle()
                    args.putString("idLeague", idLeague)
                    fragment.setArguments(args)
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.fragment_container, fragment)
                    fragmentTransaction.commit()
                }else if (conditionLeague==3){
                    idLeague = spinnerMap.get(position).toString()
                    idLeague2 = spinnerMap2.get(position).toString()
                    val fragment = TeamsFragment()
                    val args = Bundle()
                    args.putString("idLeague", idLeague2)
                    fragment.setArguments(args)
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.fragment_container, fragment)
                    fragmentTransaction.commit()
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_last -> {
                spinner.visible()
                spinner2.invisible()
                conditionLeague=1
                val fragment = LastFragment()
                val args = Bundle()
                args.putString("idLeague", idLeague)
                fragment.setArguments(args)
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, fragment)
                fragmentTransaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_next -> {
                spinner.visible()
                spinner2.invisible()
                conditionLeague=2
                val fragment = NextFragment()
                val args = Bundle()
                args.putString("idLeague", idLeague)
                fragment.setArguments(args)
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, fragment)
                fragmentTransaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorit -> {
                spinner.invisible()
                spinner2.visible()
                val fragment = FavoriteFragment()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, fragment)
                fragmentTransaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_team -> {
                spinner.visible()
                spinner2.invisible()
                conditionLeague=3
                val fragment = TeamsFragment()
                val args = Bundle()
                args.putString("idLeague", idLeague2)
                fragment.setArguments(args)
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, fragment)
                fragmentTransaction.commit()
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }
    private fun getListLeague() {
        val gson = Gson()
        val apiRepository = ApiRepository()
        doAsync {

            val data = gson.fromJson(apiRepository
                    .doRequest(MatchApi.getLeague()),
                    LeagueResponse::class.java
            )

            uiThread {
                league = data.leagues as MutableList<LeagueModel>
                val spinnerArray = arrayOfNulls<String>(league.size)
                for (i in 0 until league.size){
                    spinnerArray[i]=league.get(i).strLeague
                    spinnerMap.put(i,league.get(i).idLeague.toString())
                    spinnerMap2.put(i,league.get(i).strLeague.toString())
                }

                val adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_item, spinnerArray)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        menu.findItem(R.id.action_favorite).isVisible = false
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                        presenter.searchMatch(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.equals("")){

                    }else
                    presenter.searchMatch(newText)

                return false
            }

        })
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                // Do whatever you need
                return true // KEEP IT TO TRUE OR IT DOESN'T OPEN !!
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                spinner.visible()
                navigation.visible()
                fragment_container.visible()
                listMatch.invisible()
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.getItemId()

        if (id == R.id.action_search) {
            if (conditionLeague==3){
                val intent = Intent(applicationContext, SearchTeamActivity::class.java)
                startActivity(intent)
                finish()
                return false
            }
            listMatch.invisible()
            spinner.invisible()
            navigation.invisible()
            fragment_container.invisible()


            return true
        } else if (id == R.id.action_favorite) {
            /*val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)*/
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
        listMatch.visible()
    }

    override fun showMatchList(data: List<MatchModel>?, condition: Boolean) {
            if (condition){
                match.clear()
                if (data != null) {
                    match.addAll(data)
                }
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(applicationContext,"Empty List",Toast.LENGTH_SHORT).show()
                match.clear()
                adapter.notifyDataSetChanged()
            }


    }

}
