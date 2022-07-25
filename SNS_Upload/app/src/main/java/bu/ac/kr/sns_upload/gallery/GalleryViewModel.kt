package bu.ac.kr.sns_upload.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bu.ac.kr.sns_upload.snsuploadApplication.Companion.appContext
import kotlinx.coroutines.launch

class GalleryViewModel : ViewModel() {
    private val galleryPhotoRepository by lazy { GalleryPhotoRepository(appContext!!) }

    private lateinit var photoList : MutableList<GalleryPhoto>

    val galleryStateLiveData = MutableLiveData<GalleryState>(GalleryState.Uninitialized)

    fun fetchData() = viewModelScope.launch {
        setState(
            GalleryState.Loading
        )
        photoList = galleryPhotoRepository.getAllPhotos()
        setState(
            GalleryState.Success(
                photoList
            )
        )
    }
    private fun setState(state: GalleryState){
        galleryStateLiveData.postValue(state)
    }
}