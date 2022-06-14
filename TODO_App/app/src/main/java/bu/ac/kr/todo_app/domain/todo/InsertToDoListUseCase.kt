package bu.ac.kr.todo_app.domain.todo

import bu.ac.kr.todo_app.data.repository.ToDoRepository
import bu.ac.kr.todo_app.domain.UseCase

internal class InsertToDoListUseCase(
    private val toDoRepository: ToDoRepository
) : UseCase{
}