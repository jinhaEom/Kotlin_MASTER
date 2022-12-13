package bu.ac.kr.todoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoModel::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object {     //DB 인스턴스를 싱글톤으로 사용하기 위해 companion object 사용

        private var INSTANCE: TodoDatabase? = null

        fun getInstance(context: Context): TodoDatabase? {         //여러 스레드가 접근하지 못하도록 synchronized로 설정
            if (INSTANCE == null) {
                synchronized(TodoDatabase::class) {
                    INSTANCE = Room.databaseBuilder(  //Room.databaseBuilder 로 인스턴스를 생성
                        context.applicationContext,
                        TodoDatabase::class.java,
                        "tb_todo"
                    )
                        .fallbackToDestructiveMigration()  //데이터베이스가 갱신될 때 기존의 테이블을 버리고 새로 사용하도록 설정
                        .build()
                }
            }
            return INSTANCE
        }
    }
}