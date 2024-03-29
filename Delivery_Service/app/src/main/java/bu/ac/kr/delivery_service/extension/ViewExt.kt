package bu.ac.kr.delivery_service.extension

import android.view.View
import androidx.annotation.ColorRes

fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toInvisible() {
    visibility = View.INVISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}

fun View.color(@ColorRes colorResId: Int) = context.color(colorResId)