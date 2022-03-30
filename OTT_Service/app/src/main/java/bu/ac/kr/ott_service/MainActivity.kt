package bu.ac.kr.ott_service

import android.app.Activity
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bu.ac.kr.ott_service.databinding.ActivityMainBinding
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    val binding by lazy{ ActivityMainBinding.inflate(layoutInflater)}

    private var isGatheringMotionAnimating: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        makeStatusBarTransparent() // statusbar 투명으로 바꾸기
        initAppBar()
        initInsetMargin()

        binding.scrollView.viewTreeObserver.addOnScrollChangedListener {
            if(binding.scrollView.scrollY > 150f.dpToPx(this).toInt()){
                if(isGatheringMotionAnimating.not()){
                    binding.gatheringDigitalThingsLayout.transitionToEnd()
                    binding.buttonShownMotionLayout.transitionToEnd()
                }

            }else{
                if(!isGatheringMotionAnimating){
                    binding.gatheringDigitalThingsLayout.transitionToStart()
                    binding.buttonShownMotionLayout.transitionToStart()
                }
            }
        }
        binding.gatheringDigitalThingsLayout.setTransitionListener(object : MotionLayout.TransitionListener{
            override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {
                isGatheringMotionAnimating = true
            }

            override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                isGatheringMotionAnimating = false
            }

            override fun onTransitionTrigger(motionLayout: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float) =Unit

        })

    }
    private fun initInsetMargin() = with(binding) {
        ViewCompat.setOnApplyWindowInsetsListener(coordinator) { v: View, insets: WindowInsetsCompat ->
            val params = v.layoutParams as ViewGroup.MarginLayoutParams
            params.bottomMargin = insets.systemWindowInsetBottom
            toolbarContainer.layoutParams = (toolbarContainer.layoutParams as ViewGroup.MarginLayoutParams).apply {
                setMargins(0, insets.systemWindowInsetTop, 0, 0)
            }
            collapsingToolbarContainer.layoutParams = (collapsingToolbarContainer.layoutParams as ViewGroup.MarginLayoutParams).apply {
                setMargins(0, 0, 0, 0)
            }

            insets.consumeSystemWindowInsets()
        }
    }
    private fun initAppBar() {  //scroll 조절
        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val topPadding = 300f.dpToPx(this)
            val realAlphaScrollHeight = appBarLayout.measuredHeight - appBarLayout.totalScrollRange
            val abstractOffset = abs(verticalOffset)

            val realAlphaVerticalOffset = if (abstractOffset - topPadding < 0) 0f else abstractOffset - topPadding

            if (abstractOffset < topPadding) {
                binding.toolbarBackgroundView.alpha = 0f  // 액션바 투명
                return@OnOffsetChangedListener
            }
            val percentage = realAlphaVerticalOffset / realAlphaScrollHeight
            binding.toolbarBackgroundView.alpha = 1 - (if (1 - percentage * 2 < 0) 0f else 1 - percentage * 2)
        })
        initActionBar()
    }


    private fun initActionBar() = with(binding){  //ActionBar 커스터마이징
        toolbar.navigationIcon = null
        toolbar.setContentInsetsAbsolute(0,0)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let{
            it.setHomeButtonEnabled(false)
            it.setDisplayHomeAsUpEnabled(false)
            it.setDisplayShowHomeEnabled(false)
        }

    }

}
fun Float.dpToPx(context: Context): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,this,context.resources.displayMetrics)
fun Activity.makeStatusBarTransparent(){
    window.apply {
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = Color.TRANSPARENT
    }
}