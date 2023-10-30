package com.example.mad_assignment_2_21012011118

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.Article
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var recyclerView: RecyclerView
    private var articleList: MutableList<Article> = ArrayList()
    private lateinit var adapter: NewsRecyclerAdapter
    private lateinit var progressIndicator: LinearProgressIndicator
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btn4: Button
    private lateinit var btn5: Button
    private lateinit var btn6: Button
    private lateinit var btn7: Button
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.news_recycler_view)
        progressIndicator = findViewById(R.id.progress_bar)

        btn1 = findViewById(R.id.btn_1)
        btn2 = findViewById(R.id.btn_2)
        btn3 = findViewById(R.id.btn_3)
        btn4 = findViewById(R.id.btn_4)
        btn5 = findViewById(R.id.btn_5)
        btn6 = findViewById(R.id.btn_6)
        btn7 = findViewById(R.id.btn_7)

        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)
        btn4.setOnClickListener(this)
        btn5.setOnClickListener(this)
        btn6.setOnClickListener(this)
        btn7.setOnClickListener(this)

        searchView = findViewById(R.id.search_view)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                getNews("GENERAL", query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                getNews("GENERAL", newText)
                return true
            }
        })

        setupRecyclerView()
        getNews("GENERAL", null)

        val btn8 : LinearLayout = findViewById(R.id.btn_8)
        btn8.setOnClickListener {
            startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NewsRecyclerAdapter(articleList, this)
        recyclerView.adapter = adapter
    }

    private fun changeInProgress(show: Boolean) {
        progressIndicator.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    private fun getNews(category: String, query: String?) {
        changeInProgress(true)
        val newsApiClient = NewsApiClient("7738ebc6cf084b5b8743c3e8519a87c2")
        newsApiClient.getTopHeadlines(
            TopHeadlinesRequest.Builder()
                .language("en")
                .category(category)
                .q(query)
                .build(),
            object : NewsApiClient.ArticlesResponseCallback {
                override fun onSuccess(response: ArticleResponse) {
                    runOnUiThread {
                        changeInProgress(false)
                        articleList = response.articles
                        adapter.updateData(articleList)
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(throwable: Throwable) {
                    Log.i("GOT FAILURE", throwable.message.toString())
                }
            }
        )
    }

    override fun onClick(v: View) {
        val btn = v as Button
        val category = btn.text.toString()
        getNews(category, null)
    }
}