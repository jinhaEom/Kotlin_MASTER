package bu.ac.kr.room_test.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import bu.ac.kr.room_test.RoomMemo

/*
Dao : Data Access Object의 약어로 Db에 접근해서 DML 쿼라(SELECT,UPDATE,DELETE)를 실행하는 메소드 모음.
*/

@Dao
interface RoomMemoDao {
        @Query("select * from room_memo" )
        fun getAll() : List<RoomMemo>

        @Insert(onConflict = REPLACE)
        fun insert(memo:RoomMemo)

        @Delete
        fun delete(memo : RoomMemo)
}