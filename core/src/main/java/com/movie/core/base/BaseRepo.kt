package com.movie.core.base

import com.movie.network.network.NetworkManager
import javax.inject.Inject

open class BaseRepo {
    @Inject
    lateinit var networkManager: NetworkManager
}