package bu.ac.kr.sqlite_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import bu.ac.kr.sqlite_test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    val helper = SqliteHelper(this, "memo",1)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = RecyclerAdapter()
        adapter.helper = helper //helper 를 adapter 에 전달

        adapter.listData.addAll(helper.selectMemo()) //adapter의 listdata에 데이터베이스에서 가져온 데이터 세팅
        binding.recyclerMemo.adapter = adapter  //리사이클러 뷰 위젯에 adapter 연결 후 레이아웃 매니저 설정
        binding.recyclerMemo.layoutManager = LinearLayoutManager(this)

    binding.buttonSave.setOnClickListener {
        if(binding.editMemo.text.toString().isNotEmpty()){
            val memo = Memo(null, binding.editMemo.text.toString() , System.currentTimeMillis())
            helper.insertMemo(memo)
            adapter.listData.clear()  //어댑터의 데이터 초기화

            adapter.listData.addAll(helper.selectMemo())   //번호 갱신을 위해 새로운 데이터 세팅
            adapter.notifyDataSetChanged()
            binding.editMemo.setText("")
        }
    }

    }
}