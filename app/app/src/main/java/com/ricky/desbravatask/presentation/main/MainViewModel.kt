package com.ricky.desbravatask.presentation.main

import androidx.lifecycle.ViewModel
import com.ricky.desbravatask.domain.usercase.DepartamentoManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val departamentoManager: DepartamentoManager
) : ViewModel() {
}