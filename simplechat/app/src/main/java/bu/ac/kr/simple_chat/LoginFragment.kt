package bu.ac.kr.simple_chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import bu.ac.kr.simple_chat.databinding.FragmentLoginBinding

class LoginFragment: Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        val view = binding.root

        //닉네임이 공백일 경우 버튼 비활
        binding.etNickname.addTextChangedListener { text ->
            binding.btnEnter.isEnabled = text.toString().replace(" ","")!=""
        }
        binding.btnEnter.setOnClickListener {
            val nickname = binding.etNickname.text.toString()
            val bundle = Bundle()
            bundle.putString("nickname",nickname)
            //chatFragment로 이동
            (activity as MainActivity).replaceFragment(bundle)
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}