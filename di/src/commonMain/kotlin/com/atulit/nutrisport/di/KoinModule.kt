package com.atulit.nutrisport.di

import com.atulit.nutrisport.auth.AuthViewModel
import com.atulit.nutrisport.data.CustomerRepositoryImpl
import com.atulit.nutrisport.data.domain.CustomerRepository
import com.atulit.nutrisport.home.HomeGraphViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val sharedModule = module {
    single <CustomerRepository> { CustomerRepositoryImpl() }
    viewModelOf(::AuthViewModel)
    viewModelOf(::HomeGraphViewModel)
}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null,
) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule)
    }

}