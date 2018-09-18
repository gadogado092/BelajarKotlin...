package amat.belajarkotlin.adapter

import amat.belajarkotlin.R
import amat.belajarkotlin.model.MatchModel
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MatchAdapter(private val context: Context, private val teams : List<MatchModel>, private val listener: (MatchModel) -> Unit)
    : RecyclerView.Adapter<MatchAdapter.NextViewHolder>() {
    override fun onBindViewHolder(holder: NextViewHolder, position: Int) {
        holder.bindItem(teams[position],listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = NextViewHolder(LayoutInflater.from(context).inflate(R.layout.layoutitemlistnext, parent, false))

    override fun getItemCount(): Int =teams.size




    class NextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val clubAway = view.findViewById<TextView>(R.id.awayClub)
        private val clubHome = view.findViewById<TextView>(R.id.homeClub)
        private val dateMatch = view.findViewById<TextView>(R.id.dateMatch)
        private val timeMatch = view.findViewById<TextView>(R.id.timeMatch)
        private val vs = view.findViewById<TextView>(R.id.Vs)

        fun bindItem(match: MatchModel, listener: (MatchModel) -> Unit){
            if (match.timeMatch !=null ){
                clubAway.text=match.awayTeam
                clubHome.text=match.homeTeam
                dateMatch.text=parseDateToView(match.dateMatch.toString())
                timeMatch.text=match.timeMatch.toString().substring(0, 5)
            }


            if (match.awayScore!=null){
                vs.text=match.homeScore+"\t\tVS\t\t"+match.awayScore
            }
            itemView.setOnClickListener{
                listener(match)
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






