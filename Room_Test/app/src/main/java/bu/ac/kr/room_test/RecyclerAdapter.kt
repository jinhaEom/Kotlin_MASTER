package bu.ac.kr.room_test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bu.ac.kr.room_test.databinding.ItemRecyclerBinding
import java.text.SimpleDateFormat

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.Holder>(){
    var listData = mutableListOf<Memo>()
    var helper : SqliteHelper? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val memo = listData.get(position)
        holder.setMemo(memo)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class Holder(val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root){
        //holder는 한 화면에 그려지는 갯수만큼 만든 후 재사용 함.

        var mMemo: Memo? = null

        init {
            binding.buttonDelete.setOnClickListener {
                helper?.deleteMemo(mMemo!!)  // deleteMemo는 null허용하지 않았는데 mMemo는 null허용을 했으므로 !!로 강제사용
                listData.remove(mMemo)
                notifyDataSetChanged()

            }

        }
        fun setMemo(memo: Memo){
            binding.textNo.text ="${memo.no}"
            binding.textContent.text = memo.content
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            // 날짜 포맷은 simpleDateFormate 으로 설정
            binding.textDatetime.text = "${sdf.format(memo.datetime)}"
            this.mMemo = memo

        }
    }
}