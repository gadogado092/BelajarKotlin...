package amat.belajarkotlin.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import amat.belajarkotlin.R
import amat.belajarkotlin.dbsqlite.Favorite
import amat.belajarkotlin.dbsqlite.database
import amat.belajarkotlin.detail.DetailActivity
import amat.belajarkotlin.detail.DetailActivityFavorite
import amat.belajarkotlin.main.FavoritetAdapter
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteFragment : Fragment() {
    private lateinit var listFavorite: RecyclerView
    private lateinit var adapter: FavoritetAdapter
    private var favorites: MutableList<Favorite> = mutableListOf()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =inflater.inflate(R.layout.fragment_favorite, container, false)
        adapter= FavoritetAdapter(view.context,favorites){
            val intent = Intent(view.context, DetailActivityFavorite::class.java)
            var bundle = Bundle()
            bundle.putParcelable("selected_match", it)
            intent.putExtra("myBundle", bundle)
            startActivity(intent)
        }
        listFavorite = view.findViewById(R.id.listFavorite)
        listFavorite.layoutManager = LinearLayoutManager(activity)
        listFavorite.setHasFixedSize(true)
        listFavorite.adapter=adapter
        return view
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    private fun showFavorite(){
        context?.database?.use {
        val result = select(Favorite.TABLE_FAVORITE)
            val favorite= result.parseList(classParser<Favorite>())
            favorites.clear()
            favorites.addAll(favorite)
            if (favorites.size.equals(0)){
                Toast.makeText(activity,"Favorite Empty", Toast.LENGTH_SHORT).show()
            }
            adapter.notifyDataSetChanged()
        }
    }


}
