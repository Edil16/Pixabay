package com.example.pixabay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pixabay.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var imageAdapter = PixaAdapter(arrayListOf())
    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initClicker()
    }

    private fun initClicker() {
        binding.requestBtn.setOnClickListener {
            doRequest(page)
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    doRequest(++page)
                    Toast.makeText(applicationContext, "Change Page", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.changePageBtn.setOnClickListener {
            doRequest(++page)
            Toast.makeText(applicationContext, "Change Page", Toast.LENGTH_SHORT).show()
        }
    }

    private fun doRequest(page : Int) {
        App.api.getImage(q = binding.keyWordEd.text.toString(), page = page)
            .enqueue(object : Callback<PixabayModel> {
                override fun onResponse(
                    call: Call<PixabayModel>,
                    response: Response<PixabayModel>
                ) {
                    response.body()?.hits?.let {
                        imageAdapter = PixaAdapter(it)
                        binding.recyclerView.adapter = imageAdapter
                    }

                    Log.e("ololo", "onResponse: ${response.body()?.hits}")
                }

                override fun onFailure(call: Call<PixabayModel>, t: Throwable) {
                    Log.e("ololo", "onFailure: ${t.message}")
                }
            })
    }
}