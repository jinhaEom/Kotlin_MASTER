package bu.ac.kr.room_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import bu.ac.kr.room_test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    var helper : RoomHelper? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        helper = Room.databaseBuilder(this, RoomHelper::class.java, "room_memo")
            .allowMainThreadQueries()
            //Room은 서브 스레드에서 동작하도록 설계되어있어서 allowMainThreadQueries()가 없으면 앱동작 멈춤.
            .build()

        val adapter = RecyclerAdapter()
        adapter.helper = helper //helper 를 adapter 에 전달

        adapter.listData.addAll(helper?.roomMemoDao()?.getAll()?: listOf())
        binding.recyclerMemo.adapter = adapter  //리사이클러 뷰 위젯에 adapter 연결 후 레이아웃 매니저 설정
        binding.recyclerMemo.layoutManager = LinearLayoutManager(this)

        binding.buttonSave.setOnClickListener {
            if(binding.editMemo.text.toString().isNotEmpty()){
                val memo = RoomMemo(binding.editMemo.text.toString() , System.currentTimeMillis())
                helper?.roomMemoDao()?.insert(memo)
                adapter.listData.clear()  //어댑터의 데이터 초기화

                adapter.listData.addAll(helper?.roomMemoDao()?.getAll()?: listOf())
                adapter.notifyDataSetChanged()
                binding.editMemo.setText("")
            }
        }

    }
}