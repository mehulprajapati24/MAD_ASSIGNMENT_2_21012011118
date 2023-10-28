package com.example.mad_assignment_2_21012011118

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class NewsRecyclerAdapterFavorite(private val articleList: MutableList<NewsItem>) : RecyclerView.Adapter<NewsRecyclerAdapterFavorite.NewsViewHolder>() {

    lateinit var sqLiteHelper: SQLiteHelper
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_recycler_view, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val article = articleList[position]
        holder.titleTextView.text = article.title
        holder.sourceTextView.text = article.source

        Picasso.get()
            .load(article.img)
            .resize(300, 300)
            .error(R.drawable.baseline_image_not_supported_24)
            .placeholder(R.drawable.baseline_image_not_supported_24)
            .into(holder.imageView)


        holder.linearLayout.setOnClickListener {
            val intent = Intent(it.context, NewsFullActivity::class.java)
            intent.putExtra("url", article.url)
            it.context.startActivity(intent)
        }

        holder.favImg.setImageResource(R.drawable.baseline_remove_circle_outline_24)
        holder.favImg.setOnClickListener {
            sqLiteHelper = SQLiteHelper(it.context)
            var result = sqLiteHelper.deleteNews(article.title)
            if (result > 0)
            {
                Toast.makeText(it.context, "Successfully removed from library!", Toast.LENGTH_SHORT).show()
                articleList.removeAt(position)
                notifyDataSetChanged()
            }
            else
                Toast.makeText(it.context, "Error", Toast.LENGTH_LONG).show()
        }
    }


    override fun getItemCount(): Int {
        return articleList.size
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.article_title)
        val sourceTextView: TextView = itemView.findViewById(R.id.article_source)
        val imageView: ImageView = itemView.findViewById(R.id.article_image_view)
        val linearLayout: LinearLayout = itemView.findViewById(R.id.article_raw)

        val favImg : ImageView = itemView.findViewById(R.id.article_favorite)
    }
}