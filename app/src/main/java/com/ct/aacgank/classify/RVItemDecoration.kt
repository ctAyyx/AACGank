package com.ct.aacgank.classify

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ct.aacgank.classify.data.ClassifyBean
import com.ct.aacgank.newest.adapter.NewestAdapter
import java.util.*
import kotlin.math.roundToInt

/**
 * 文件名 RVItemDecoration
 * 创建者  CT
 * 时 间  2019/9/19 14:52
 * TODO
 */


/**
 * 只有一个基本间隔的Decoration
 * */
class LinearItemDecoration(private val interval: Int = 1) : RecyclerView.ItemDecoration() {


    private val mPaint = Paint().apply {
        color = Color.parseColor("#EAEAEA")

    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = interval
        outRect.bottom = 0

        val position = parent.getChildAdapterPosition(view)
        if (position == (parent.adapter?.itemCount ?: 0) - 1) {
            outRect.bottom = interval
        }


    }

    /**
     * 在Item之后绘制
     *
     * */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {


    }


    /**
     *  在Item之前绘制
     *  绘制RecyclerView
     * */
    private val outRect = Rect()

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {


        // drawRect(canvas, parent)


    }

    private fun drawRect(
        canvas: Canvas,
        parent: RecyclerView
    ) {
        canvas.save()
        //获取RecyclerView的padding
        val left = parent.paddingLeft
        val top = parent.paddingTop
        val right = parent.width - parent.paddingRight
        val bottom = parent.height - parent.paddingBottom
        //裁剪出RecyclerView显示ItemView的区域
        canvas.clipRect(left, top, right, bottom)

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, outRect)


            //获取ChildView的底部
            val childBottom = outRect.bottom + child.translationY.roundToInt()
            val childTop = outRect.top + child.height


            //可以绘制的区域
            canvas.drawRect(
                left.toFloat(),
                childTop.toFloat(),
                right.toFloat(),
                childBottom.toFloat(),
                mPaint
            )

        }


        canvas.restore()
    }
}


/**
 * GridDecoration
 *
 * */
class GridItemDecoration(private val interval: Int = 16) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val childCount = parent.childCount
        val itemCount = parent.adapter?.itemCount ?: 0
        outRect.top = interval
        outRect.bottom = 0
        for (i in 0 until childCount) {
            val childView = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(childView)

            if (position % 2 == 0) {
                outRect.left = interval
                outRect.right = interval / 2

            } else {
                outRect.left = interval / 2
                outRect.right = interval
            }


            if (position == itemCount - 1 || position == itemCount - 2)
                outRect.bottom = interval


        }


    }
}


/**
 * 悬浮吸顶
 * */

class RoofItemDecoration() : RecyclerView.ItemDecoration() {


    var mList: List<ClassifyBean> = mutableListOf()
    private val headHeight = 80

    interface OnHeadTopListener {
        fun getPreHeadTop(position: Int): String
    }

    private val mPaint = Paint().apply {
        color = Color.RED
        textSize = 40f
        style = Paint.Style.FILL
    }

    private val mPaint2 = Paint().apply {
        color = Color.YELLOW
        textSize = 40f
        textLocale = Locale.CHINA
        style = Paint.Style.FILL
    }

    private val onHeadTopListener = object : OnHeadTopListener {
        override fun getPreHeadTop(position: Int): String {

            if (position == 0)
                return mList[0].type!!

            val currentData = mList[position]
            val preData = mList[position]
            if (preData.type == currentData.type)
                return preData.type!!
            else
                return currentData.type!!


        }
    }


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = 0

        val adapter = parent.adapter as NewestAdapter

        val position = parent.getChildAdapterPosition(view)

        if (position == -1)
            return

        val model = adapter.currentList!![position]
        Log.e("RoofItemDecoration", "position:$position  Type:${model?.type}")
        if (position == 0)
            outRect.top = headHeight
        else {
            val oldModel = adapter.currentList!![position - 1]
            if (oldModel?.type != model?.type)
                outRect.top = headHeight
        }


    }

    /**
     * 这里绘制每个HeaderItem的标题栏
     * 也可以在onDrawOver中绘制
     *
     * */
//    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//
//
//        val count = parent.childCount
//
//        val outRect = Rect()
//        for (i in 0 until count) {
//            val childView = parent.getChildAt(i)
//            val position = parent.getChildAdapterPosition(childView)
//            parent.getDecoratedBoundsWithMargins(childView, outRect)
//
//
//            if (outRect.top + childView.height != outRect.bottom) {
//
//                canvas.save()
//                canvas.drawRect(
//                    0f,
//                    outRect.top.toFloat(),
//                    parent.width.toFloat(),
//                    outRect.top + headHeight.toFloat(),
//                    mPaint
//                )
//
//                canvas.drawText(
//                    onHeadTopListener.getPreHeadTop(position),
//                    20f,
//                    outRect.top.toFloat() + 60f,
//                    mPaint2
//                )
//                canvas.restore()
//
//
//            }
//
//
//        }
//
//    }

    private var needDrawTop = true

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)
        needDrawTop = true
        val childCount = parent.childCount
        val outRect = Rect()
        //获取当前界面第一个View在Adapter中的角标
        val firstPosition = parent.getChildAdapterPosition(
            parent.getChildAt(0)
        )
        //RecyclerView数据为初始化
        if (firstPosition == -1)
            return

        //获取顶部标题
        val title = onHeadTopListener.getPreHeadTop(
            firstPosition
        )
        //判断当前RecyclerView是否有HeaderItem进入
        for (i in 0 until childCount) {
            val childView = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(childView, outRect)

            //如果当前ItemView到RecyclerView顶部的距离 +ItemView的高度不等于 ItemView底部到RecyclerView
            // 顶部的距离
            //则该ItemView是一个HeaderItem
            if ((outRect.top + childView.height) != outRect.bottom) {

                canvas.drawRect(
                    0f,
                    outRect.top.toFloat(),
                    parent.width.toFloat(),
                    outRect.top + headHeight.toFloat(),
                    mPaint
                )

                canvas.drawText(
                    onHeadTopListener.getPreHeadTop(parent.getChildAdapterPosition(childView)),
                    20f,
                    outRect.top.toFloat() + 60f,
                    mPaint2
                )

                //一个带有Head的Item进入界面
                if (outRect.top in 0..headHeight) {
                    //当前Item抵达Head或从外部进入head
                    canvas.drawRect(0f, 0f, parent.width.toFloat(), outRect.top.toFloat(), mPaint2)
                    canvas.drawText(title, 20f, outRect.top.toFloat() - 20f, mPaint)
                    needDrawTop = false
                }


            }

        }

        //在没有HeadItem进入时 画顶部
        if (needDrawTop) {
            canvas.drawRect(0f, 0f, parent.width.toFloat(), headHeight.toFloat(), mPaint2)
            canvas.drawText(title, 20f, 60f, mPaint)
        }


    }


    private fun drawRect(canvas: Canvas, parent: RecyclerView, outRect: Rect, paint: Paint) {

        val left = parent.paddingLeft.toFloat()
        val right = (parent.width - parent.paddingRight).toFloat()
        val top = parent.paddingTop.toFloat()
        val bottom = headHeight.toFloat()

        canvas.drawRect(left, top, right, bottom, paint)
    }

}

