package bu.ac.kr.todoapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import bu.ac.kr.todoapp.data.TodoModel
import bu.ac.kr.todoapp.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val TAG : String = MainActivity::class.java.name

    private lateinit var todoViewModel: TodoViewModel // TodoViewModel 인스턴스를 만들고, 관찰

    private lateinit var todoListAdapter : TodoListAdapter
    private val todoItems : ArrayList<TodoModel> = ArrayList()

    private val recyclerView_List : RecyclerView by lazy {
        binding.recyclerViewList
    }
    private val btn_Add : Button by lazy{
        binding.btnAdd
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initRecyclerView()
        initBtnAdd()
    }

    private fun initViewModel() {
        todoViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(TodoViewModel::class.java)
        todoViewModel.getTodoListAll().observe(this, androidx.lifecycle.Observer {
            todoListAdapter.setTodoItems(it)
        })
    }
    private fun initRecyclerView(){
        todoListAdapter = TodoListAdapter({ todo -> deleteDialog(todo)})
        recyclerView_List.run{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = todoListAdapter
        }
    }
    private fun initBtnAdd(){
        btn_Add.setOnClickListener{
            showAddTodoDialog()
        }
    }
    private fun showAddTodoDialog(){
        val dialogView = layoutInflater.inflate(R.layout.dialog_add,null)
        val et_add_title : EditText by lazy {
            dialogView.findViewById<EditText>(R.id.et_add_title)
        }
        val et_add_content: EditText by lazy {
            dialogView.findViewById<EditText>(R.id.et_add_content)
        }
        var builder = AlertDialog.Builder(this)
        val dialog = builder.setTitle("Todo 아이템 추가하기").setView(dialogView)
            .setPositiveButton(
            "확인"
        ){
                DialogInterface, i ->
                var id : Long? = null
                val title = et_add_title.text.toString()
                val content = et_add_content.text.toString()
                val createDate = Date().time
                val todoModel = TodoModel(
                    id,
                    todoListAdapter.getItemCount() + 1,
                    title,
                    content,
                    createDate
                )
                todoViewModel.insert(todoModel)
            }
            .setNegativeButton("취소",null)
            .create()
        dialog.show()
    }
    private fun deleteDialog(todoModel: TodoModel) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(todoModel.seq.toString()+" 번 Todo 아이템을 삭제할까요? ")
            .setNegativeButton("취소") { _, _ -> }
            .setPositiveButton("확인") { _, _ ->
                todoViewModel.delete(todoModel)
            }
            .create()
        builder.show()
    }
}