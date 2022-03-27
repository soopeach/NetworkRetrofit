package com.soopeach.practiceForRetrofit

import Repository
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.soopeach.practiceForRetrofit.databinding.ActivityMainBinding
import com.soopeach.practiceForRetrofit.retrogit.RetrofitConnection
import com.soopeach.practiceForRetrofit.retrogit.TestService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET


class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = CustomAdapter()
        CoroutineScope(Dispatchers.Main).launch {
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        }

        // 레트로핏 객체 생성
        val retrofit = RetrofitConnection.getInstance()

        binding.buttonRequest.setOnClickListener {
            // 에딧 텍스트에 입력할 아이디
            val path = binding.pathId.text.toString()

            CoroutineScope(Dispatchers.IO).launch {

                val githubService = retrofit.create(TestService::class.java)
                // *** path 값이 없을 땐 어찌해야할까용
                githubService.users(path).enqueue(object: Callback<Repository>{

                    override fun onResponse(call: Call<Repository>, response: Response<Repository>)
                    {
                        Log.d("Tag", "${response.body() as Repository}")
                        adapter.userList = response.body() as Repository
                        adapter.notifyDataSetChanged()

                    }

                    override fun onFailure(call: Call<Repository>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
            }
        }


    }
}

interface GithubService{

    @GET("users/rnjstmdals6/repos")
    fun users(): Call<Repository>
}