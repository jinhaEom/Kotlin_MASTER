package bu.ac.kr.todo_app.data.repository

import bu.ac.kr.todo_app.data.entity.ToDoEntity


/**
 * 1. insertToDoList
 * 2. getToDoList
 */
interface ToDoRepository {
    suspend fun getToDoList(): List<ToDoEntity>

    suspend fun insertToDoList(toDoList : List<ToDoEntity>)

}