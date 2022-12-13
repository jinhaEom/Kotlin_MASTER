package bu.ac.kr.todoapp

import android.app.Application
import androidx.lifecycle.LiveData
import bu.ac.kr.todoapp.data.TodoDao
import bu.ac.kr.todoapp.data.TodoDatabase
import bu.ac.kr.todoapp.data.TodoModel



 // ViewModel 에서는  Repository 를 통해 데이터를 얻는다

class TodoRepository(application: Application) {

    //database, dao todoItems 초기화
    private var todoDatabase: TodoDatabase = TodoDatabase.getInstance(application)!!
    private var todoDao: TodoDao = todoDatabase.todoDao()
    private var todoItems: LiveData<List<TodoModel>> = todoDao.getTodoListAll()

    fun getTodoListAll(): LiveData<List<TodoModel>> {
        return todoItems
    }

    fun insert(todoModel: TodoModel) {
        try {
            val thread =
                Thread(Runnable {  //별도의 스레드를 통해 연산 수행 -> 연산시간이 오래걸리기 때문에 런타임 에러 발생하기 때문
                    todoDao.insert(todoModel)
                }).start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun delete(todoModel: TodoModel) {
        try {
            val thread = Thread(Runnable {
                todoDao.delete(todoModel)
            })
            thread.start()
        } catch (e: Exception) {
        }
    }

}