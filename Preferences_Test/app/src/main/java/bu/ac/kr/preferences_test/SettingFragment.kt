package bu.ac.kr.preferences_test

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat


class SettingFragment: PreferenceFragmentCompat(){
    override fun onCreatePreferences(savedInstanceState : Bundle?, rootKey: String?){

        addPreferencesFromResource(R.xml.preferences)
        //설정항목에 대한 View 자동생성
    }
}