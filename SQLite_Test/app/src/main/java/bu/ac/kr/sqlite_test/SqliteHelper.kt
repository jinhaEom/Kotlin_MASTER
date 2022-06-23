package bu.ac.kr.sqlite_test

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper(context: Context, name: String, version: Int) :
    //SQLiteOpenHelper는 생성시에 Context,DB명,factory,version정보 필요(팩토리는 미사용가능)
    SQLiteOpenHelper(context, name, null, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        //테이블 생성쿼리를 문자열로 입력.
        val create = "create table memo("+
                "no integer primary key,"+
                "content text," +
                "datetime integer"+
                ")"
        db?.execSQL(create)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        //SqliteHelper에 전달되는 버전 정보가 변경되었을때 현재 생성되어있는 DB버전과 비교해서 더 높으면 호출됨.
    }
    fun insertMemo(memo: Memo){
        val values = ContentValues()
        values.put("content",memo.content)
        values.put("datetime", memo.datetime)
        val wd = writableDatabase
        wd.insert("memo",null,values)
        wd.close()
    }
}
data class Memo(var no: Long?,var content:String, var datetime: Long)