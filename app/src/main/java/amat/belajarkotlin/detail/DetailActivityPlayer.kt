package amat.belajarkotlin.detail

import amat.belajarkotlin.R
import amat.belajarkotlin.model.PlayerModel
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class DetailActivityPlayer : AppCompatActivity() {


    private lateinit var player: PlayerModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)

        val bundle = intent.getBundleExtra("myBundle")
        player  = bundle.getParcelable<PlayerModel>("selected_player") as PlayerModel

        val namePlayer = findViewById<TextView>(R.id.namePlayer)
        val deskripsiPlayer = findViewById<TextView>(R.id.deskripsiPlayer)
        val strThumb = findViewById<ImageView>(R.id.strThumb)

        namePlayer.text=player.strPlayer
        deskripsiPlayer.text=player.strDescriptionEN
        Picasso.get().load(player.strThumb).centerCrop().fit().into(strThumb)

    }

}
