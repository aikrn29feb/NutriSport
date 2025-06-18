package com.atulit.nutrisport.shared.util

import kotlinx.cinterop.CValue
import platform.CoreGraphics.CGRect
import platform.CoreGraphics.CGRectGetWidth
import platform.UIKit.UIScreen
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
actual fun getScreenWidth(): Float {

    val bounds: CValue<CGRect> = UIScreen.mainScreen.bounds
    return CGRectGetWidth(bounds).toFloat()
}