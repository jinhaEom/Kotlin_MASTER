package bu.ac.kr.todoapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import bu.ac.kr.todoapp.data.TodoModel

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val todoRepository = TodoRepository(application)
    private var todoItems =
        todoRepository.getTodoListAll()  //액티비티(View) 에서 ViewModel 의 todoItems 리스트를 observe 하고 리스트를 갱신

    /* repsitory 에 넘겨 viewModel 의 기능 함수 구현 */

    fun getTodoListAll(): LiveData<List<TodoModel>> {
        return todoItems
    }

    fun insert(todoModel: TodoModel) {
        todoRepository.insert(todoModel)
    }

    fun delete(todoModel: TodoModel) {
        todoRepository.delete(todoModel)
    }
}