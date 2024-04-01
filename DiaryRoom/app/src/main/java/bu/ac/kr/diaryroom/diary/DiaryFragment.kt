package bu.ac.kr.diaryroom.diary

import DiaryAdapter
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import bu.ac.kr.diaryroom.dialog.DialogHelper
import bu.ac.kr.diaryroom.R
import bu.ac.kr.diaryroom.base.BaseFragment
import bu.ac.kr.diaryroom.databinding.FragmentDiaryBinding
import bu.ac.kr.diaryroom.diary.data.DiaryDatabase
import bu.ac.kr.diaryroom.utils.getNavOptions
import bu.ac.kr.diaryroom.viewModel.DiaryViewModel
import bu.ac.kr.diaryroom.viewModel.Factory.DiaryViewModelFactory
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date

class DiaryFragment(override val layoutResourceId: Int = R.layout.fragment_diary) :
    BaseFragment<FragmentDiaryBinding>() {

    private lateinit var diaryViewModel: DiaryViewModel
    private lateinit var diaryAdapter: DiaryAdapter

    override fun aboutBinding() {
        viewDataBinding.lifecycleOwner = this
        val application = requireNotNull(this.activity).application
        val dataSource = DiaryDatabase.getInstance(application).diaryItemDao()
        val viewModelFactory = DiaryViewModelFactory(dataSource)
        diaryViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(DiaryViewModel::class.java)
        diaryAdapter = DiaryAdapter(diaryViewModel)

        viewDataBinding.diaryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewDataBinding.diaryRecyclerView.adapter = diaryAdapter
        val dialogHelper = DialogHelper(requireContext(), viewLifecycleOwner, diaryViewModel)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            diaryViewModel.getAllDiaryItems().collect { items ->
                diaryAdapter.submitList(items)
            }
        }

        viewDataBinding.homeToolbarRightButton.setOnClickListener {
            findNavController().navigate(
                DiaryFragmentDirections.actionDiaryWrittingFragment(),
                getNavOptions
            )
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            diaryViewModel.getAllDiaryItems().collect { items ->
                diaryAdapter.submitList(items)

            }
        }

        viewDataBinding.calendarImageView.setOnClickListener {
            val dialog = dialogHelper.createDialog(
                showDatePicker = { showDatePicker() },
                submitList = { items -> diaryAdapter.submitList(items) }
            )
            dialog.show()
            val params = dialogHelper.getDialogLayoutParams(dialog)
            dialog.window?.attributes = params
        }
    }
    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker().build()
        datePicker.show(childFragmentManager, "DatePicker")

        datePicker.addOnPositiveButtonClickListener {
            val dateFormatter = SimpleDateFormat("yyyy년 MM월 dd일")
            val date = dateFormatter.format(Date(it))
            diaryViewModel.getDiaryItemsForDate(date).observe(viewLifecycleOwner, Observer { items ->
                diaryAdapter.submitList(items)
            })
            Toast.makeText(requireContext(), "$date 일기", Toast.LENGTH_LONG).show()
        }

    }
    override fun observeData() {
    }
}
