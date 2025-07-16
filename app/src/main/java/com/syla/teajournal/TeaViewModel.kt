package com.syla.teajournal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.syla.teajournal.data.TeaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TeaViewModel(private val teaRepository: TeaRepository) : ViewModel() {

    val allTeas: Flow<List<Tea>> = teaRepository.allTeas

    fun addTea(tea: Tea) {
        viewModelScope.launch {
            teaRepository.insert(tea)
        }
    }

    fun updateTea(tea: Tea) {
        viewModelScope.launch {
            teaRepository.update(tea)
        }
    }

    fun deleteTea(tea: Tea) {
        viewModelScope.launch {
            teaRepository.delete(tea)
        }
    }

    fun getTeaById(id: Int): Flow<Tea> {
        return teaRepository.getTeaById(id)
    }

    companion object {
        fun provideFactory(repository: TeaRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(TeaViewModel::class.java)) {
                        return TeaViewModel(repository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}