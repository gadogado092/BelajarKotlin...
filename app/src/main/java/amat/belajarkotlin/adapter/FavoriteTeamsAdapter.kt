package amat.belajarkotlin.adapter

import amat.belajarkotlin.R
import amat.belajarkotlin.dbsqlite.FavoriteTeam
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class FavoriteTeamsAdapter(private val context: Context, private val teams : List<FavoriteTeam>, private val listener: (FavoriteTeam) -> Unit)
    : RecyclerView.Adapter<FavoriteTeamsAdapter.NextViewHolder>() {
    override fun onBindViewHolder(holder: NextViewHolder, position: Int) {
        holder.bindItem(teams[position],listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = NextViewHolder(LayoutInflater.from(context).inflate(R.layout.layoutitemlistteam, parent, false))

    override fun getItemCount(): Int =teams.size




    class NextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val logoTeam = view.findViewById<ImageView>(R.id.teamLogo)
        private val nameTeam = view.findViewById<TextView>(R.id.teamName)


        fun bindItem(teams: FavoriteTeam, listener: (FavoriteTeam) -> Unit){
           nameTeam.text=teams.strTeam
            Picasso.get().load(teams.teamBadge).into(logoTeam)

            itemView.setOnClickListener{
                listener(teams)
            }

        }


    }



}






