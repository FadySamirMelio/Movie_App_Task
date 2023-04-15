package com.movie.core.base

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnyViewModel @Inject constructor() : BaseViewModel()