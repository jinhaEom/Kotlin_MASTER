package bu.ac.kr.todo_app.viewmodel.todo

import bu.ac.kr.todo_app.presentation.list.ListViewModel
import bu.ac.kr.todo_app.viewmodel.ViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.koin.test.inject

/**
 * [ListViewModel]을 테스트하기 위한 Unit Test Class
 *
 * 1. initData()
 * 2. test viewMdel fetch
 * 3. test Item Update
 * 4. test Item Delete All
 */


@ExperimentalCoroutinesApi
internal class ListViewModelTest : ViewModelTest(){

    private val viewModel : ListViewModel by inject()
    /**
     * 필요한 UseCase들
     * 1. InsertToDoListUseCase
     * 2. GetToDoItemUseCase
     */
    @Before
    fun init(){
        initData()
    }
    private fun initData() =
}