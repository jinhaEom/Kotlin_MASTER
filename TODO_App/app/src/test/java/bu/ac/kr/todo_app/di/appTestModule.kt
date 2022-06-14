package bu.ac.kr.todo_app.di

import bu.ac.kr.todo_app.data.repository.TestToDoRepository
import bu.ac.kr.todo_app.data.repository.ToDoRepository
import bu.ac.kr.todo_app.domain.todo.GetToDoListUseCase
import bu.ac.kr.todo_app.domain.todo.InsertToDoListUseCase
import org.koin.dsl.module

internal val appTestModule = module{
    //UseCase
    factory { GetToDoListUseCase(get())}
    factory { InsertToDoListUseCase(get()) }
    // Repository

    single<ToDoRepository> {TestToDoRepository()}
}