package bu.ac.kr.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import bu.ac.kr.todoapp.data.TodoModel

class MainActivity : AppCompatActivity() {

    val TAG : String = MainActivity::class.java.name

    private lateinit var todoViewModel: TodoViewModel // TodoViewModel 인스턴스를 만들고, 관찰

    private lateinit var todoListAdapter : TodoListAdapter
    private val todoItems : ArrayList<TodoModel> = ArrayList()

    private val recyclerView_list : RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.recyclerview_list)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}