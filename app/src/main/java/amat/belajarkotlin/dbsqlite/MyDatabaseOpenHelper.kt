package amat.belajarkotlin.dbsqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper (ctx: Context) : ManagedSQLiteOpenHelper(ctx,"FavoriteMatch.db",null,1) {
    companion object {
        private var instances: MyDatabaseOpenHelper? = null
        @Synchronized
        fun getInstance(ctx: Context):MyDatabaseOpenHelper{
            if (instances==null){
                instances = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instances as MyDatabaseOpenHelper
        }

    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(Favorite.TABLE_FAVORITE,true,
                Favorite.ID_EVENT to TEXT+ PRIMARY_KEY,
                Favorite.HOME_TEAM to TEXT,
                Favorite.AWAY_TEAM to TEXT,
                Favorite.DATE_MATCH to TEXT,
                Favorite.TIME_MATCH to TEXT,
                Favorite.HOME_SCORE to TEXT,
                Favorite.AWAY_SCORE to TEXT,
                Favorite.AWAY_GOALS to TEXT,
                Favorite.AWAY_GK to TEXT,
                Favorite.AWAY_MIDFIELD to TEXT,
                Favorite.AWAY_DEFENSE to TEXT,
                Favorite.AWAY_FORWARD to TEXT,
                Favorite.AWAY_SUBTITUTES to TEXT,
                Favorite.HOME_GOALS to TEXT,
                Favorite.HOME_GK to TEXT,
                Favorite.HOME_MIDFIELD to TEXT,
                Favorite.HOME_DEFENSE to TEXT,
                Favorite.HOME_FORWARD to TEXT,
                Favorite.HOME_SUBTITUTES to TEXT
        )
        db?.createTable(FavoriteTeam.TABLE_FAVORITE, true,
                FavoriteTeam.ID_TEAM to TEXT,
                FavoriteTeam.STR_TEAM to TEXT,
                FavoriteTeam.INT_FORMEDYEAR to TEXT,
                FavoriteTeam.STR_STADIUM to TEXT,
                FavoriteTeam.STR_STADIUMTHUMB to TEXT,
                FavoriteTeam.TEAM_BADGE to TEXT
                )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(Favorite.TABLE_FAVORITE,true)
    }

}
    val Context.database: MyDatabaseOpenHelper
        get() = MyDatabaseOpenHelper.getInstance(applicationContext)