package bu.ac.kr.diaryroom.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

abstract class BaseFragment<T : ViewDataBinding> : Fragment(), LifecycleOwner {

    /**
     * @viewDataBinding  : 데이터바인딩
     * @layoutResourceId : 레이아웃 리소스 아이디
     */
    lateinit var viewDataBinding: T
    abstract val layoutResourceId: Int
    var onBackPressedCallback : OnBackPressedCallback? = null

    /**
     *  @aboutBinding : 바인딩 후 기본적으로 해야할 부분을 담당
     *  @observeData  : 라이브데이터 관찰 후 컨트롤 해야하는 부분 등록하기 위함.
     */
    abstract fun aboutBinding()
    abstract fun observeData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        return viewDataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aboutBinding()
        observeData()
        // navigation 그래프에서 정의한 이동 방식에 따라 프래그먼트 뷰가 생성되었을 경우 해당 그래프의 목적지로 이동하여 레이아웃을 보여주도록 함

    }


    fun viewDataBindingIsInitialized() : Boolean {
        return ::viewDataBinding.isInitialized
    }
}