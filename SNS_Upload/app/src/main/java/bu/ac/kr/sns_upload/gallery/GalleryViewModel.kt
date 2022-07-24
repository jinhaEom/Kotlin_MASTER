package bu.ac.kr.sns_upload.gallery

import androidx.lifecycle.ViewModel
import bu.ac.kr.sns_upload.snsuploadApplication.Companion.appContext

class GalleryViewModel : ViewModel() {
    private val galleryPhotoRepository by lazy { GalleryPhotoRepository(appContext!!) }
}