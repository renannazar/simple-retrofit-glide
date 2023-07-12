package com.example.testapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.adapters.PhotoAdapter
import com.example.testapplication.models.Photo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerPhoto: RecyclerView = findViewById(R.id.rv_main_photos)

        val apiNetwork = ApiNetwork().apiInit().create(ApiService::class.java)

        apiNetwork.getAllPhotos(10).enqueue(object : Callback<List<Photo>> {
            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                Log.d("Response", "Code : ${response.code()}")
                if (response.isSuccessful) {
                    val photoList = response.body()
                    photoList?.let {
                        Log.d("Response", "Success Data")
                        val adapterPhoto = PhotoAdapter(photoList)
                        recyclerPhoto.layoutManager = GridLayoutManager(this@MainActivity, 2)
                        recyclerPhoto.adapter = adapterPhoto
                    }
                }
            }

            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_SHORT).show()
            }

        })

    }

    class ApiNetwork {
        private val apiUrl = "https://jsonplaceholder.typicode.com/"

        fun apiInit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(apiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    interface ApiService {
        @GET("photos")
        fun getAllPhotos(@Query("_end") total: Int): Call<List<Photo>>
    }
}