package com.soopeach.practiceForRetrofit

import Repository
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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


class MainActivity : AppCompatActivity(), OnItemClickListener{
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        CoroutineScope(Dispatchers.Main).launch {
            adapter = CustomAdapter(this@MainActivity)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        }

        // 레트로핏 객체 생성
        val retrofit = RetrofitConnection.getInstance()

        binding.buttonRequest.setOnClickListener {
            // 에딧 텍스트에 입력할 아이디


            CoroutineScope(Dispatchers.IO).launch {
                val path = binding.pathId.text.toString()
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

    override fun onClick(position: Int) {
        val address = adapter.userList?.get(position)?.html_url
        Toast.makeText(this, "$address", Toast.LENGTH_SHORT).show()
        val builder : AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("주소")
        builder.setMessage("$address \n 위의 주소로 이동하시겠습니까")
        builder.setNegativeButton("아니요", null)
        builder.setPositiveButton("이동하겠습니다.",
            object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$address"))
                    startActivity(intent)
                }
            }
        )
        builder.show()

    }

}

interface GithubService{

    @GET("users/rnjstmdals6/repos")
    fun users(): Call<Repository>
}