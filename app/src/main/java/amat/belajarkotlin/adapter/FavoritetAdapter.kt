package amat.belajarkotlin.adapter


import amat.belajarkotlin.R
import amat.belajarkotlin.dbsqlite.Favorite
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class FavoritetAdapter(private val context: Context, private val favorite : List<Favorite>, private val listener: (Favorite) -> Unit)
    : RecyclerView.Adapter<FavoritetAdapter.NextViewHolder>() {
    override fun onBindViewHolder(holder: NextViewHolder, position: Int) {
        holder.bindItem(favorite[position],listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = NextViewHolder(LayoutInflater.from(context).inflate(R.layout.layoutitemlistnext, parent, false))

    override fun getItemCount(): Int =favorite.size




    class NextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val clubAway = view.findViewById<TextView>(R.id.awayClub)
        private val clubHome = view.findViewById<TextView>(R.id.homeClub)
        private val dateMatch = view.findViewById<TextView>(R.id.dateMatch)
        private val timeMatch = view.findViewById<TextView>(R.id.timeMatch)
        private val vs = view.findViewById<TextView>(R.id.Vs)

        fun bindItem(favorite: Favorite, listener: (Favorite) -> Unit){
            clubAway.text=favorite.awayTeam
            clubHome.text=favorite.homeTeam
            dateMatch.text=parseDateToView(favorite.dateMatch.toString())
            timeMatch.text=favorite.timeMatch.toString().substring(0, 5)

            if (favorite.awayScore!=null){
                vs.text=favorite.awayScore+"\t\tVS\t\t"+favorite.homeScore
            }
            itemView.setOnClickListener{
                listener(favorite)
            }

        }

        private fun parseDateToView(time: String): String? {
            val inputPattern = "yyyy-MM-dd"
            val outputPattern = "EEE-d-MM-yyyy"
            val inputFormat = SimpleDateFormat(inputPattern)
            val outputFormat = SimpleDateFormat(outputPattern)

            var date: Date? = null
            var str: String? = null

            try {
                date = inputFormat.parse(time)
                str = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return str
        }
    }



}






