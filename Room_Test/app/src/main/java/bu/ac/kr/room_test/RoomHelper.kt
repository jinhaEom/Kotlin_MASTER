package bu.ac.kr.room_test

import androidx.room.Database
import androidx.room.RoomDatabase
import bu.ac.kr.room_test.Dao.RoomMemoDao

/*
* entities : Room 라이브러리가 사용할 테이블(entities) 클래스 목록
* version : DB의 버전
* exportSchema : true 면 스키마 정보를 파일로 출력
* */
@Database(entities = arrayOf(RoomMemo::class), version = 1, exportSchema = false)

abstract class RoomHelper: RoomDatabase() {
    abstract fun roomMemoDao() : RoomMemoDao
}

//비어있는 코드지만 작성해둔 것만으로도 Room 라이브러리를 통해 미리 만들어져 있는 코드를 사용할 수 있게 됨.