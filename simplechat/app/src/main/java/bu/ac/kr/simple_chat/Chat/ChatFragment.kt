package bu.ac.kr.simple_chat.Chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import bu.ac.kr.simple_chat.databinding.FragmentChatBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

class ChatFragment :Fragment() {
    private var _binding: FragmentChatBinding?= null
    private val binding get() = _binding!!
    private lateinit var currentUser : String  //현재 닉네임
    private val db = FirebaseFirestore.getInstance() // Firestore 인스턴스
    private lateinit var registration: ListenerRegistration // 문서수신
    private val chatList = arrayListOf<ChatLayout>()
    private lateinit var adapter : ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //LoginFragment 에서 입력한 닉네임 가져오기
        arguments?.let{
            currentUser = it.getString("nickname").toString()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChatBinding.inflate(inflater,container,false)
        val view = binding.root
/*
        Toast.makeText(context,"현재 닉네임은 ${currentUser}입니다",Toast.LENGTH_SHORT).show()
*/

        binding.rvList.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        adapter = ChatAdapter(currentUser, chatList)
        binding.rvList.adapter = adapter

        //채팅창이 공백일 경우 버튼 비활성화
        binding.etChatting.addTextChangedListener{ text ->
            binding.btnSend.isEnabled = text.toString()!=""
        }
        //입력버튼
        binding.btnSend.setOnClickListener {
            //입력데이터
            val data = hashMapOf(
                "nickname" to currentUser,
                "contents" to binding.etChatting.text.toString(),
                "time" to Timestamp.now()
            )
            db.collection("Chat").add(data)
                .addOnSuccessListener{
                    binding.etChatting.text.clear()
                    Log.w("ChatFragment","Dcument added : $it")
                }
                .addOnFailureListener{e ->
                    Toast.makeText(context,"전송하는데 실패했습니다.",Toast.LENGTH_SHORT).show()
                    Log.w("ChatFragment","Error occurs: $e")
                }
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Snackbar.make(view,"$currentUser 닉네임으로 입장했습니다!",Snackbar.LENGTH_SHORT).show()
        /*chatList.add(ChatLayout("알림","$currentUser 닉네임으로 입장했습니다.",""))*/
        val enterTime = Date(System.currentTimeMillis())

        registration = db.collection("Chat")
            .orderBy("time", Query.Direction.DESCENDING)
            .limit(1)
            .addSnapshotListener{ snapshots, e->
                //오류- 발생시
                if(e!= null){
                    return@addSnapshotListener
                }
                //원하지 않는 doc 무시
                if(snapshots!!.metadata.isFromCache)return@addSnapshotListener

                //문서수신
                for(doc in snapshots.documentChanges){
                    val timestamp = doc.document["time"] as Timestamp

                    //문서가 추가될 경우 리사이클러 뷰에 추가
                    if(doc.type == DocumentChange.Type.ADDED && timestamp.toDate()>enterTime){
                        val nickname = doc.document["nickname"].toString()
                        val contents = doc.document["contents"].toString()

                        //timestamp 를 한국시간으로 변경.
                        val sf = SimpleDateFormat("yyy/MM/dd HH:mm:ss",Locale.KOREA)
                        sf.timeZone = TimeZone.getTimeZone("Asia/Seoul")
                        val time = sf.format(timestamp.toDate())

                        val item = ChatLayout(nickname,contents,time)
                        chatList.add(item)
                    }
                    adapter.notifyDataSetChanged()

                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        registration.remove()
        _binding=null
    }
}