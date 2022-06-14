package bu.ac.kr.todo_app.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import bu.ac.kr.todo_app.di.appTestModule
import bu.ac.kr.todo_app.livedata.LiveDataTestObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@ExperimentalCoroutinesApi
internal abstract class ViewModelTest : KoinTest{
    @get: Rule
    val mockitoRule : MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var context: Application

    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        startKoin {
            androidContext(context)
            modules(appTestModule)
        }
        Dispatchers.setMain(dispatcher)
    }
    @After
    fun tearDown(){
        stopKoin()
        Dispatchers.resetMain()  // MainDispatcher를 초기화 해야 메모리 누수가 발생하지 않음.
    }
    protected fun <T> LiveData<T>.test() : LiveDataTestObserver<T>{
        val testObserver = LiveDataTestObserver<T>()
        observeForever(testObserver)
        return testObserver
    }
}