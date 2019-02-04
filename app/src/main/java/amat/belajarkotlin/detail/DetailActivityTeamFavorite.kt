package amat.belajarkotlin.detail

import amat.belajarkotlin.R
import amat.belajarkotlin.api.ApiRepository
import amat.belajarkotlin.dbsqlite.FavoriteTeam
import amat.belajarkotlin.dbsqlite.database
import amat.belajarkotlin.adapter.PlayerAdapter
import amat.belajarkotlin.presenter.PlayerPresenter
import amat.belajarkotlin.view.PlayerView
import amat.belajarkotlin.model.PlayerModel
import amat.belajarkotlin.util.invisible
import amat.belajarkotlin.util.visible
import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class DetailActivityTeamFavorite : AppCompatActivity(), PlayerView {

    private var isFavorite: Boolean = false
    private lateinit var listMatch: RecyclerView
    private lateinit var presenter: PlayerPresenter
    private lateinit var adapter: PlayerAdapter
    private var player : MutableList<PlayerModel> = mutableListOf()
    private lateinit var progressBar: ProgressBar
    private lateinit var id: String

    private lateinit var team: FavoriteTeam
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        val bundle = intent.getBundleExtra("myBundle")
        team  = bundle.getParcelable<FavoriteTeam>("selected_match") as FavoriteTeam
        id= team.idTeam.toString()
        val nameTeam = findViewById<TextView>(R.id.nameTeam)
        val yearTeam = findViewById<TextView>(R.id.yearTeam)
        val nameStadium = findViewById<TextView>(R.id.nameStadium)
        val teamBadage = findViewById<ImageView>(R.id.teamBadage)
        val stadium = findViewById<ImageView>(R.id.stadium)

        nameTeam.text=team.strTeam
        yearTeam.text=team.intFormedYear
        nameStadium.text=team.strStadium

        Picasso.get().load(team.teamBadge).into(teamBadage)

        Picasso.get().load(team.strStadiumThumb).centerCrop().fit().into(stadium)


        progressBar = findViewById(R.id.progressBar)
        listMatch = findViewById(R.id.listPlayer)
        listMatch.layoutManager = GridLayoutManager(applicationContext,2)
        listMatch.setHasFixedSize(true)
        adapter = PlayerAdapter(applicationContext, player) {
            val intent = Intent(applicationContext, DetailActivityPlayer::class.java)
            val bundle = Bundle()
            bundle.putParcelable("selected_player", it)
            intent.putExtra("myBundle", bundle)
            startActivity(intent)
        }

        listMatch.adapter = adapter
        val request = ApiRepository()
        val gson = Gson()
        presenter = PlayerPresenter(this, request, gson)
        presenter.getTeams(team.strTeam.toString())
        favoriteState()
    }
    private fun favoriteState(){
        database.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE)
                    .whereArgs("(ID_TEAM = {id})",
                            "id" to id)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showMatchList(data: List<PlayerModel>?) {
        player.clear()
        if (data != null) {
            player.addAll(data)
        }
        adapter.notifyDataSetChanged()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        favoriteState()
        menuInflater.inflate(R.menu.main_menu, menu)
        menu.findItem(R.id.action_search).isVisible = false
        val favoriteItem = menu?.findItem(R.id.action_favorite)
        if (isFavorite){
            favoriteItem.setIcon(R.drawable.ic_favorite_black_24dp)
        }else{
            favoriteItem.setIcon(R.drawable.ic_favorite_border_black_24dp)
        }
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()

        if (id == R.id.action_favorite) {
            if (isFavorite){
                removeFromFavorite()
                item.setIcon(R.drawable.ic_favorite_border_black_24dp)
            }else{
                addFavorite()
                item.setIcon(R.drawable.ic_favorite_black_24dp)
            }
            isFavorite=!isFavorite

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun addFavorite(){
        try {
            database.use {
                insert(FavoriteTeam.TABLE_FAVORITE,
                        FavoriteTeam.ID_TEAM to team.idTeam,
                        FavoriteTeam.STR_TEAM to team.strTeam,
                        FavoriteTeam.TEAM_BADGE to team.teamBadge,
                        FavoriteTeam.INT_FORMEDYEAR to team.intFormedYear,
                        FavoriteTeam.STR_STADIUM to team.strStadium,
                        FavoriteTeam.STR_STADIUMTHUMB to team.strStadiumThumb

                )
            }
            Toast.makeText(this,"Added to favorite", Toast.LENGTH_SHORT).show()
        }catch (e: SQLiteConstraintException){

        }
    }

    private fun removeFromFavorite(){

        try {
            database.use {
                delete(FavoriteTeam.TABLE_FAVORITE, "(ID_TEAM = {id})",
                        "id" to id )
            }
            Toast.makeText(this,"Remove from favorite", Toast.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException){
            Toast.makeText(this,e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }
}
