package bu.ac.kr.subway_arriveinfo.extension

import android.content.Context
import androidx.annotation.Px

@Px
fun Context.dip(dipValue : Float) = (dipValue * resources.displayMetrics.density).toInt()

//dp  값을 실제 화면의 픽셀 값으로 변환