package bu.ac.kr.delivery_service.presentation.addtrackingitem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bu.ac.kr.delivery_service.databinding.FragmentAddTrackingItemBinding
import bu.ac.kr.delivery_service.entity.ShippingCompany
import org.koin.android.scope.ScopeFragment

class AddTrackingItemFragment : ScopeFragment(), AddTrackingItemsContract.View {


    override val presenter: AddTrackingItemsContract.Presenter by inject()

    private var binding: FragmentAddTrackingItemBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentAddTrackingItemBinding.inflate(inflater)
        .also { binding = it }
        .root


}
