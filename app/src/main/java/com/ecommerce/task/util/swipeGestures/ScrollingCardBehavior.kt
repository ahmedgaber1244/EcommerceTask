package com.ecommerce.task.util.swipeGestures

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class ScrollingCardBehavior(context: Context?, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<CardView>(context, attrs) {
    private var toolbarHeight = 0

    init {
        toolbarHeight = 100
    }

    fun layoutDependsOn(parent: CoordinatorLayout?, fab: CardView?, dependency: View?): Boolean {
        return dependency is AppBarLayout
    }

    fun onDependentViewChanged(
        parent: CoordinatorLayout?,
        fab: CardView,
        dependency: View
    ): Boolean {
        if (dependency is AppBarLayout) {
            val lp = fab.layoutParams as CoordinatorLayout.LayoutParams
            val fabBottomMargin = lp.bottomMargin
            val distanceToScroll = fab.height + fabBottomMargin
            val ratio = dependency.getY() / toolbarHeight.toFloat()
            fab.translationY = -distanceToScroll * ratio
        }
        return true
    }
}