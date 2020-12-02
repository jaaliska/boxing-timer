package by.itman.boxingtimer.data

import by.itman.boxingtimer.models.TimerModel

interface TimerProvider {
    fun getAll(): List<TimerModel>
    fun getById(i: Int): TimerModel?
    fun save(timer: TimerModel): TimerModel
    fun remove(timer: TimerModel)
}