package amat.belajarkotlin.adapter

import amat.belajarkotlin.R
import amat.belajarkotlin.model.TeamModel
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class TeamsAdapter(private val context: Context, private val teams : List<TeamModel>, private val listener: (TeamModel) -> Unit)
    : RecyclerView.Adapter<TeamsAdapter.NextViewHolder>() {
    override fun onBindViewHolder(holder: NextViewHolder, position: Int) {
        holder.bindItem(teams[position],listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = NextViewHolder(LayoutInflater.from(context).inflate(R.layout.layoutitemlistteam, parent, false))

    override fun getItemCount(): Int =teams.size




    class NextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val logoTeam = view.findViewById<ImageView>(R.id.teamLogo)
        private val nameTeam = view.findViewById<TextView>(R.id.teamName)


        fun bindItem(teams: TeamModel, listener: (TeamModel) -> Unit){
           nameTeam.text=teams.strTeam
            Picasso.get().load(teams.strTeamBadge).into(logoTeam)

            itemView.setOnClickListener{
                listener(teams)
            }

        }


    }



}






