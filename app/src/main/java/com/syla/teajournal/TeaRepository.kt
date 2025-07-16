package com.syla.teajournal.data

import com.syla.teajournal.Tea
import com.syla.teajournal.TeaDao
import kotlinx.coroutines.flow.Flow

class TeaRepository(private val teaDao: TeaDao) {

    val allTeas: Flow<List<Tea>> = teaDao.getAllTeas()

    fun getTeaById(id: Int): Flow<Tea> {
        return teaDao.getTeaById(id)
    }

    suspend fun insert(tea: Tea) {
        teaDao.insert(tea)
    }

    suspend fun update(tea: Tea) {
        teaDao.update(tea)
    }

    suspend fun delete(tea: Tea) {
        teaDao.delete(tea)
    }
}