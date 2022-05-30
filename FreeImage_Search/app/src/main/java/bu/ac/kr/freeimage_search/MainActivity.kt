package bu.ac.kr.freeimage_search

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bu.ac.kr.freeimage_search.data.Repository
import bu.ac.kr.freeimage_search.databinding.ActivityMainBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
        bindViews()
        fetchRandomPhotos()
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    private fun initViews() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = PhotoAdapter()
    }
    private fun bindViews(){
        binding.searchEditText
            .setOnEditorActionListener { editText, actionId, event ->
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    currentFocus?.let{ view ->
                    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    inputMethodManager?.hideSoftInputFromWindow(view.windowToken,0)  //키보드 내리기

                        view.clearFocus()
                    }
                    fetchRandomPhotos(editText.text.toString())

                }
                true
        }
        binding.refreshLayout.setOnRefreshListener {
            fetchRandomPhotos(binding.searchEditText.text.toString())
        }
    }
    private fun fetchRandomPhotos(query: String?= null)= scope.launch{
        Repository.getRandomPhotos(query)?.let { photos ->
            (binding.recyclerView.adapter as? PhotoAdapter)?.apply {
                this.photos = photos
                notifyDataSetChanged()
            }
            binding.refreshLayout.isRefreshing = false
        }
    }
}