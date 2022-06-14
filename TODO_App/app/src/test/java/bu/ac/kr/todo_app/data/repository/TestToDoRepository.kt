package bu.ac.kr.todo_app.data.repository

import bu.ac.kr.todo_app.data.entity.ToDoEntity

class TestToDoRepository :ToDoRepository {

    private val toDoList : MutableList<ToDoEntity> = mutableListOf()

    override suspend fun getToDoList(): List<ToDoEntity> {
        return toDoList
    }

    override suspend fun insertToDoList(toDoList: List<ToDoEntity>) {
        this.toDoList.addAll(toDoList)
    }

}