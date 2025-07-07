package com.atulit.nutrisport.di

import com.atulit.nutrisport.admin_panel.AdminPanelViewModel
import com.atulit.nutrisport.auth.AuthViewModel
import com.atulit.nutrisport.data.AdminRepositoryImpl
import com.atulit.nutrisport.data.CustomerRepositoryImpl
import com.atulit.nutrisport.data.domain.AdminRepository
import com.atulit.nutrisport.data.domain.CustomerRepository
import com.atulit.nutrisport.home.HomeGraphViewModel
import com.atulit.nutrisport.manage_product.ManageProductViewModel
import com.atulit.nutrisport.profile.ProfileViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val sharedModule = module {
    single<CustomerRepository> { CustomerRepositoryImpl() }
    single<AdminRepository> { AdminRepositoryImpl() }
    viewModelOf(::AuthViewModel)
    viewModelOf(::HomeGraphViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::ManageProductViewModel)
    viewModelOf(::AdminPanelViewModel)
}

expect val targetModule : Module

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null,
) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule, targetModule)
    }

}