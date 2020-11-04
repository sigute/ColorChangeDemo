package com.github.sigute.colorchangedemo

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { RandomColorRepository() }
    viewModel { RandomColorViewModel(get()) }
}
