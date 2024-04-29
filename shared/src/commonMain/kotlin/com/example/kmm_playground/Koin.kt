package com.example.kmm_playground

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun commonStartKoin() = startKoin {
    modules(commonModule, platformModule())
}

expect fun platformModule(): Module

private val commonModule = module {
    singleOf(::TodosRepository) bind TodosRepository::class
}