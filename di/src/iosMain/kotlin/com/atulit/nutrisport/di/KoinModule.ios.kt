package com.atulit.nutrisport.di

import com.atulit.nutrisport.manage_product.PhotoPicker
import org.koin.dsl.module

actual val targetModule = module {
    single<PhotoPicker> { PhotoPicker() }
}