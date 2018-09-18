package amat.belajarkotlin.adapter

import amat.belajarkotlin.R
import amat.belajarkotlin.model.PlayerModel
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class PlayerAdapter(private val context: Context, private val player : List<PlayerModel>, private val listener: (PlayerModel) -> Unit)
    : RecyclerView.Adapter<PlayerAdapter.NextViewHolder>() {
    override fun onBindViewHolder(holder: NextViewHolder, position: Int) {
        holder.bindItem(player[position],listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = NextViewHolder(LayoutInflater.from(context).inflate(R.layout.layoutitemlistplayer, parent, false))

    override fun getItemCount(): Int =player.size




    class NextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val playerPict = view.findViewById<ImageView>(R.id.playerPict)
        private val playerName = view.findViewById<TextView>(R.id.playerName)


        fun bindItem(player: PlayerModel, listener: (PlayerModel) -> Unit){
           playerName.text=player.strPlayer
            Picasso.get().load(player.strCutout).into(playerPict)

            itemView.setOnClickListener{
                listener(player)
            }

        }


    }



}






