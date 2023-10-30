package com.example.mad_assignment_2_21012011118

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoriteActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var articleList: MutableList<NewsItem> = ArrayList()
    private lateinit var adapter: NewsRecyclerAdapterFavorite
    lateinit var sqLiteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val emptyLibraryTxt: TextView = findViewById(R.id.emptyLibrary)
        val deleteAllBtn: LinearLayout = findViewById(R.id.btn_delete)

        recyclerView = findViewById(R.id.news_recycler_view)

        sqLiteHelper = SQLiteHelper(this)

        articleList = sqLiteHelper.getNews()

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NewsRecyclerAdapterFavorite(articleList)
        recyclerView.adapter = adapter

        if (articleList.size > 0)
            emptyLibraryTxt.visibility = View.INVISIBLE
        else {
            emptyLibraryTxt.visibility = View.VISIBLE
        }

        deleteAllBtn.setOnClickListener {
            var result = sqLiteHelper.deleteAllNews()
            if (result > 0) {
                Toast.makeText(this, "All news are removed successfully!", Toast.LENGTH_SHORT)
                    .show()
                articleList.clear()
                adapter.notifyDataSetChanged()
            } else
                Toast.makeText(this, "Already, all news are removed!", Toast.LENGTH_SHORT)
                    .show()
        }
    }


    override fun onBackPressed() {
        val intent = Intent(this@FavoriteActivity, MainActivity::class.java)
        startActivity(intent)
    }

}