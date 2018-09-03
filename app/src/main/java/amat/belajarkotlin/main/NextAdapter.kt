package amat.belajarkotlin.main

import amat.belajarkotlin.model.NextTeam
import amat.belajarkotlin.R
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class NextAdapter(private val context: Context, private val teams : List<NextTeam>, private val listener: (NextTeam) -> Unit)
    : RecyclerView.Adapter<NextAdapter.NextViewHolder>() {
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

        fun bindItem(teams: NextTeam, listener: (NextTeam) -> Unit){
            clubAway.text=teams.awayTeam
            clubHome.text=teams.homeTeam
            dateMatch.text=parseDatetoview(teams.dateMatch.toString())
            timeMatch.text=teams.timeMatch.toString().substring(0, 5)

            if (teams.awayScore!=null){
                vs.text=teams.awayScore+"\t\tVS\t\t"+teams.homeScore
            }
            itemView.setOnClickListener{
                listener(teams)
            }

        }

        fun parseDatetoview(time: String): String? {
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






