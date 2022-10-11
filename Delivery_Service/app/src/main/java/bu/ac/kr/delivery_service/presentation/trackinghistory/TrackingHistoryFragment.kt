package bu.ac.kr.delivery_service.presentation.trackinghistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import bu.ac.kr.delivery_service.databinding.FragmentTrackingHistoryBinding
import bu.ac.kr.delivery_service.entity.TrackingInformation
import bu.ac.kr.delivery_service.entity.TrackingItem
import bu.ac.kr.delivery_service.presentation.trackingitems.TrackingItemsContract
import org.koin.android.scope.ScopeFragment
import org.koin.core.parameter.parametersOf

class TrackingHistoryFragment : ScopeFragment(), TrackingHistoryContract.View{

    override val presenter : TrackingHistoryContract.Presenter by inject{
        parametersOf(arguments.item, arguments.information)
    }
    private var binding : FragmentTrackingHistoryBinding? = null
    private var arguments : TrackingHistoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentTrackingHistoryBinding.inflate(inflater)
        .also{ binding = it}
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViews()
        presneter.onViewCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    private fun bindViews() {
        binding?.refreshLayout?.setOnRefreshListener{
            presenter.refresh()
        }
        binding?.deleteTrackingItemButton?.setOnclickListener{
            presenter.deleteTrackingItem()
        }
    }
    override fun hideLoadingIndicator() {
        TODO("Not yet implemented")
    }

    override fun showTrackingItemInformation(
        trackingItem: TrackingItem,
        trackingInformation: TrackingInformation,
    ) {
        TODO("Not yet implemented")
    }

    override fun finish() {
        TODO("Not yet implemented")
    }

    override val presenter: TrackingItemsContract.Presenter
        get() = TODO("Not yet implemented")
}