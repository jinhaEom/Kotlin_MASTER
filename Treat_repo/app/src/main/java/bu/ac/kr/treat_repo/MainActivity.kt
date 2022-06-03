package bu.ac.kr.treat_repo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bu.ac.kr.treat_repo.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity: AppCompatActivity(), CoroutineScope {

    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launch{
            val dataHob = addMockData()
        }

    }
    private suspend fun addMockData() = withContext(Dispatchers.IO){
        val mockData = (0 until  10).map{

        }

    }
}