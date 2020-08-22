package ru.mertsalovda.audioplayer.repositiry

interface IRepository<T> {
    fun getAll(): MutableList<T>
    fun getById(id: Long): T?
    fun getNext(): T
    fun getPrevious(): T
    fun getCurrent(): T
    fun insert(item: T): Boolean
    fun insertAll(items: List<out T>): Boolean
    fun update(item: T): Boolean
    fun delete(item: T): Boolean
    fun deleteById(id: Long): Boolean
}