package bu.ac.kr.sns_upload.photo

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bu.ac.kr.sns_upload.databinding.ViewholderImageBinding

class PhotoListAdapter(
    private val removePhotoListener: (Uri) -> Unit
): RecyclerView.Adapter<PhotoListAdapter.ProductItemViewHolder>() {

    private var imageUriList: List<Uri> = listOf()

    inner class ProductItemViewHolder(
        private val binding: ViewholderImageBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bindData(data: Uri) = with(binding){
            photoImageView.loadCenterCrop(data.toString(),8f)

        }
        fun bindViews(data:Uri)= with(binding){
            closeButton.setOnClickListener{
                removePhotoListener(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
        val view = ViewholderImageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        holder.bindData(imageUriList[position])
        holder.bindViews(imageUriList[position])
    }

    override fun getItemCount(): Int = imageUriList.size

    fun setPhotoList(imageUriList: List<Uri>){
        this.imageUriList = imageUriList
        notifyDataSetChanged()
    }
}