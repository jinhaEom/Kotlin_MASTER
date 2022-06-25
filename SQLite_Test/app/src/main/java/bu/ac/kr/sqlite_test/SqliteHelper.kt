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
    fun selectMemo():MutableList<Memo>{ //조회 메소드
        val list= mutableListOf<Memo>() // 메소드의 가장 윗줄에서 반환할 값을 선언 / 가장 아랫줄에서 반환

        val select = "select * from memo"  // 메모 전체 데이터를 조회하는 쿼리
        val rd = readableDatabase// 읽기전용 db를 변수에

        val cursor = rd.rawQuery(select, null)  //커서 : 데이터 셋을 처리할 때 현재 위치를 포함하는 데이터 요소

        while(cursor.moveToNext()){
            val noIdx = cursor.getColumnIndex("no") // 1. 데이블에서 no 컬럼의 순서
            val contentIdx = cursor.getColumnIndex("content") // 2
            val dateIdx = cursor.getColumnIndex("datetime") //3

            val no = cursor.getLong(noIdx) // 값은 위에서 저장해 둔 컬럼의 위치로 가져옴.
            val content = cursor.getString(contentIdx)
            val datetime = cursor.getLong(dateIdx)
            list.add(Memo(no, content,datetime))

        }
        cursor.close()
        rd.close()  // close() 미호출 시 반환되지 않아 메모리 자원 낭비

        return list
    }
    fun updateMemo(memo: Memo){
        val values= ContentValues() // 수정할 값 저장
        values.put("content", memo.content)
        values.put("datetime", memo.datetime)

        val wd = writableDatabase // writableDatabase 의 Update() 메소드를 사용해 수정한 다음 close() 호출
        wd.update("memo", values, "no=${memo.no}",null)
        wd.close()
    }
}
data class Memo(var no: Long?,var content:String, var datetime: Long)
