package bu.ac.kr.bestceller_book

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import bu.ac.kr.bestceller_book.adapter.BookAdapter
import bu.ac.kr.bestceller_book.adapter.HistoryAdapter
import bu.ac.kr.bestceller_book.api.BookService
import bu.ac.kr.bestceller_book.databinding.ActivityMainBinding
import bu.ac.kr.bestceller_book.model.BestSellerDto
import bu.ac.kr.bestceller_book.model.SearchBookDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : BookAdapter
    private lateinit var historyAdapter : HistoryAdapter

    private lateinit var bookService : BookService

    private lateinit var db : AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initHistoryRecyclerView()
        initBookRecyclerView()

        db= getAppDatabase(this)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://book.interpark.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        bookService = retrofit.create(BookService::class.java)

        bookService.getBestSellerBooks(getString(R.string.interparkAPIKey))
            .enqueue(object:Callback<BestSellerDto>{
                override fun onResponse(
                    call: Call<BestSellerDto>,
                    response: Response<BestSellerDto>
                ) {

                    hideHistoryView()
                    if(response.isSuccessful.not()){
                        Log.e(TAG,"NOT!!SUCCESS")
                        return
                    }
                    response.body()?.let{
                        it.books.forEach{ book ->
                            Log.d(TAG,book.toString())

                        }
                        adapter.submitList(it.books)
                    }

                }

                override fun onFailure(call: Call<BestSellerDto>, t: Throwable) {
                    //todo 실패처리
                }

            })

    }
    private fun search(keyword: String) {
        bookService.getBooksByName(getString(R.string.interparkAPIKey), keyword)
            .enqueue(object:Callback<SearchBookDto>{
                override fun onResponse(
                    call: Call<SearchBookDto>,
                    response: Response<SearchBookDto>
                ) {
                    saveSearchKeyword(keyword)
                    if(response.isSuccessful.not()){
                        Log.e(TAG,"NOT!!SUCCESS")
                        return
                    }
                    adapter.submitList(response.body()?.books.orEmpty())
                    response.body()?.let{
                        adapter.submitList(it.books)
                    }

                }

                override fun onFailure(call: Call<SearchBookDto>, t: Throwable) {
                    //todo 실패처리
                    hideHistoryView()
                    Log.e(TAG,t.toString())
                }

            })
    }
    private fun initBookRecyclerView(){
        adapter = BookAdapter(itemClickedListener = {
            val intent = Intent(this,DetailActivity::class.java)
            intent.putExtra("bookModel",it)
            startActivity(intent)
        })

        binding.bookRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.bookRecyclerView.adapter = adapter
    }
    private fun initHistoryRecyclerView(){
        historyAdapter = HistoryAdapter(historyDeleteClickListener = {
            deleteSearchKeyword(it)
        })
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.historyRecyclerView.adapter = historyAdapter
        initSearchEditText()
    }
    private fun initSearchEditText(){
        binding.searchEditText.setOnKeyListener { v, keyCode, event ->
            if(keyCode== KeyEvent.KEYCODE_ENTER && event.action == MotionEvent.ACTION_DOWN){
                search(binding.searchEditText.text.toString())
                return@setOnKeyListener true

            }
            return@setOnKeyListener false

        }
        binding.searchEditText.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN){
                showHistoryView()

            }
            return@setOnTouchListener false
        }
    }
    private fun showHistoryView(){
        Thread{
            val keywords = db.historyDao().getAll().reversed()

            runOnUiThread {
                binding.historyRecyclerView.isVisible = true
                historyAdapter.submitList(keywords.orEmpty())
            }
        }.start()
        binding.historyRecyclerView.isVisible= true
    }
    private fun hideHistoryView(){
        binding.historyRecyclerView.isVisible = false
    }

    private fun saveSearchKeyword(keyword: String){
        Thread{
            db.historyDao().insertHistory(History(null,keyword))
        }.start()
    }
    private fun deleteSearchKeyword(keyword: String){
        Thread{
            db.historyDao().delete(keyword)
            showHistoryView()
        }.start()
    }

    companion object{
        private const val TAG="MainActivity"

    }

}