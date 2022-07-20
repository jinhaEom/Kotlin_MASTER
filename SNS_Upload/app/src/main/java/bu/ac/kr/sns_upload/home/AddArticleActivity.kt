package bu.ac.kr.sns_upload.home

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import bu.ac.kr.sns_upload.CameraActivity
import bu.ac.kr.sns_upload.DBKey.Companion.DB_ARTICLES
import bu.ac.kr.sns_upload.databinding.ActivityAddArticleBinding
import bu.ac.kr.sns_upload.photo.PhotoListAdapter

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class AddArticleActivity : AppCompatActivity() {
    companion object {
        const val PERMISSION_REQUEST_CODE = 1000
        const val GALLERY_REQUEST_CODE = 1001
        const val  CAMERA_REQUEST_CODE = 1002
        private const val URI_LIST_KEY = "uriList"
    }

    private var imageUriList: ArrayList<Uri> = arrayListOf()

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }
    private val storage: FirebaseStorage by lazy {
        Firebase.storage
    }
    private val articleDB: DatabaseReference by lazy {
        Firebase.database.reference.child(DB_ARTICLES)
    }
    private val photoListAdapter = PhotoListAdapter{ uri -> removePhoto(uri)}
    private lateinit var binding: ActivityAddArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }


    private fun initViews() = with(binding){
        photoRecyclerview.adapter = photoListAdapter

        imageAddButton.setOnClickListener {
           showPictureUploadDialog()
        }
        submitButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val sellerId = auth.currentUser?.uid.orEmpty()

            showProgress()
            //중간에 이미지가 있으면 업로드 과정 추가
            if(imageUriList.isNotEmpty()){
                uploadPhoto(imageUriList.first(),
                successHandler = { uri ->
                    uploadArticle(sellerId,title,content,uri)

                    },
                    errorHandler = {
                        Toast.makeText(this@AddArticleActivity,"사진 업로드에  실패하였습니다.",Toast.LENGTH_SHORT).show()
                        hideProgress()
                    }
                )

            }else{
                uploadArticle(sellerId,title,content,"")
            }

    }
    }
    private fun uploadPhoto(uri :Uri, successHandler:(String) -> Unit, errorHandler: () ->Unit){
        val fileName = "${System.currentTimeMillis()}.png"
        storage.reference.child("article/photo").child(fileName)
            .putFile(uri)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    storage.reference.child("article/photo").child(fileName).downloadUrl
                        .addOnSuccessListener { uri ->
                            successHandler(uri.toString())
                        }.addOnFailureListener{
                            errorHandler()
                        }
                }else{
                    errorHandler()
                }
            }
    }
    private fun uploadArticle(sellerId:String, title:String, content:String, imageUrl:String){
        val model = ArticleModel(sellerId, title, System.currentTimeMillis(),"$content 원",imageUrl)
        articleDB.push().setValue(model)

        hideProgress()
        finish()
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startContentProvider()
                } else {
                    Toast.makeText(this, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
                }

        }
    }
    private fun startGalleryScreen(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type="image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }
    private fun startCameraScreen(){
        startActivityForResult(
            CameraActivity.newIntent(this),
            CAMERA_REQUEST_CODE
        )
    }

    private fun startContentProvider() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }
    private fun showProgress(){
        binding.progressBar.isVisible=true

    }
    private fun hideProgress(){
        binding.progressBar.isVisible=false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            GALLERY_REQUEST_CODE -> {
                val uri = data?.data
                if (uri != null) {
                    imageUriList.add(uri)
                    photoListAdapter.setPhotoList(imageUriList)
                } else {
                    Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            CAMERA_REQUEST_CODE -> {
                data?.let{ intent ->
                    val uriList = intent.getParcelableArrayListExtra<Uri>(URI_LIST_KEY)
                    uriList?.let{ list ->
                        imageUriList.addAll(list)
                        photoListAdapter.setPhotoList(imageUriList)

                    }
                }
            }
            else -> {
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("사진을 가져오기 위해 필요합니다.")
            .setPositiveButton("동의") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)

            }
            .create()
            .show()
    }
    private fun checkExternalStoragePermission(uploadAction:() -> Unit){
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                uploadAction()
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                showPermissionContextPopup()
            }
            else -> {
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }
    private fun showPictureUploadDialog() {
        AlertDialog.Builder(this)
            .setTitle("사진첨부")
            .setMessage("사진첨부할 방식을 선택하세요")
            .setPositiveButton("카메라"){ _, _ ->
                checkExternalStoragePermission {
                    startCameraScreen()
                }
            }
            .setNegativeButton("갤러리"){_,_ ->

                checkExternalStoragePermission{
                    startGalleryScreen()
                    }
            }
            .create()
            .show()
    }
    private fun removePhoto(uri: Uri){
        imageUriList.remove(uri)
        photoListAdapter.setPhotoList(imageUriList)
    }
}