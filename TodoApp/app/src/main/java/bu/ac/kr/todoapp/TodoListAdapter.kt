package bu.ac.kr.todoapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bu.ac.kr.todoapp.data.TodoModel
import bu.ac.kr.todoapp.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class TodoListAdapter(val deleteItemClick: (TodoModel) -> Unit):

RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(){
    private var todoItems: List<TodoModel> = listOf()

    override fun getItemCount(): Int {
        return todoItems.size // 이 adapter가 아이템을 얼마나 갖고 있는지 얻는 함수
    }
    //현재 item이 사용할 viewholder를 생성하여 반환하는 함수
    // item_list 레이아웃 사용 -> 뷰생성 -> 뷰홀더에 뷰전달 -> 생성된 뷰홀더 반환

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false)
        val viewHolder = TodoViewHolder(view)
        return viewHolder
    }
    /*
    *현재 아이템의 position에 대한 데이터 모델을 리스트에서 얻고
    * holder 객체를 TodoViewHolder 로 형변환한뒤 bind 메소드에 이 모델을 전달하여 데이터를 바인딩
     */
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todoModel = todoItems[position]
        todoModel.seq = position +1
        val todoViewHolder = holder as TodoViewHolder
        todoViewHolder.bind(todoModel)
    }
    fun setTodoItems(todoItems: List<TodoModel>){ // DB가 변경될 때마다 호출
        this.todoItems = todoItems
        notifyDataSetChanged()
    }

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tv_seq = itemView.findViewById<TextView>(R.id.tv_seq)
        private val tv_title = itemView.findViewById<TextView>(R.id.tv_title)
        private val tv_content = itemView.findViewById<TextView>(R.id.tv_content)
        private val tv_date = itemView.findViewById<TextView>(R.id.tv_date)
        private val iv_delete = itemView.findViewById<ImageView>(R.id.iv_delete)

        fun bind(todoModel: TodoModel) {

            tv_seq.text = todoModel.seq.toString()
            tv_title.text = todoModel.title
            tv_content.text = todoModel.content
            tv_date.text = todoModel.createDate.convertDateToString("yyyy.MM.dd HH:mm")

            iv_delete.setOnClickListener {
                deleteItemClick(todoModel)            }

        }

    }

}fun Long.convertDateToString(format: String): String {
    val simpleDateFormat = SimpleDateFormat(format)
    return simpleDateFormat.format(Date(this))
}