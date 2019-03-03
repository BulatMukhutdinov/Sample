package ru.bulat.mukhutdinov.sample.infrastructure.util.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ScrollAwareFabBehavior @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FloatingActionButton.Behavior(context, attrs) {

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout,
                                     child: FloatingActionButton,
                                     directTargetChild: View,
                                     target: View,
                                     nestedScrollAxes: Int,
                                     type: Int
    ): Boolean {
        return if (nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL) {
            true
        } else {
            super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes, type)
        }
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout,
                                child: FloatingActionButton,
                                target: View,
                                dxConsumed: Int,
                                dyConsumed: Int,
                                dxUnconsumed: Int,
                                dyUnconsumed: Int,
                                type: Int,
                                consumed: IntArray) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed)
        if (dyConsumed > 0) {
            if (child.visibility == View.VISIBLE) {
                child.hide(object : FloatingActionButton.OnVisibilityChangedListener() {
                    override fun onHidden(floatingActionButton: FloatingActionButton?) {
                        super.onHidden(floatingActionButton)
                        floatingActionButton!!.visibility = View.INVISIBLE
                    }
                })
            }
        } else if (dyConsumed < 0 && child.visibility != View.VISIBLE) {
            child.show()
        }
    }
}