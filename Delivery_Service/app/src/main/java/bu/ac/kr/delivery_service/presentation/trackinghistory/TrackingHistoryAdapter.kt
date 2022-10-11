package bu.ac.kr.delivery_service.presentation.trackinghistory

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bu.ac.kr.delivery_service.databinding.ItemTrackingHistoryBinding
import bu.ac.kr.delivery_service.entity.TrackingDetail
import bu.ac.kr.delivery_service.presentation.trackingitems.TrackingItemsAdapter

class TrackingHistoryAdapter : RecyclerView.Adapter<TrackingHistoryAdapter.ViewHolder>(){

    var data : List<TrackingDetail> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        ItemTrackingHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder : ViewHolder, position : Int){
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    inner class ViewHolder(private val binding : ItemTrackingHistoryBinding):
        RecyclerView.ViewHolder(binding.root){

            @SuppressLint("SetTextI18n")
            fun bind(info: TrackingDetail) {
                    binding.timeStampTextView.text = info.timeString
                    binding.stateTextView.text = info.kind
                    binding.locationTextView.text = "@${info.where}"


        }
    }
}