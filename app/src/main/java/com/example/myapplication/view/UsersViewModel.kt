package com.example.myapplication.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.response.User
import com.example.myapplication.data.response.UsersResponse
import com.example.myapplication.data.retrofit.api
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

class UsersViewModel : ViewModel() {

    private val compositeDiasposable = CompositeDisposable()

    private var hasMoreData = true
    private var isLoading = false;

    private val users: MutableLiveData<List<User>> by lazy {
        MutableLiveData<List<User>>(mutableListOf()).also {
            loadUsers(0)
        }
    }

    fun getUsers(): LiveData<List<User>> {
        return users
    }


    private fun loadUsers(offset: Int) {
        isLoading = true
        this.compositeDiasposable += api.getUsers(offset).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<UsersResponse>(),
                SingleObserver<UsersResponse> {
                override fun onSuccess(t: UsersResponse) {
                    val currentUsers = users.value!!
                    val newUserList = currentUsers + t.data.users
                    users.postValue(newUserList)
                    hasMoreData = t.data.has_more
                    isLoading = false
                }

                override fun onComplete() {
                    isLoading = false
                }

                override fun onNext(t: UsersResponse) {
                    isLoading = false
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    isLoading = false
                    hasMoreData = false
                }

            })
    }

    fun loadMore() {
        val currentOffset = users.value?.count()
        if (currentOffset != null && hasMoreData && !isLoading) {
            loadUsers(currentOffset)
        }
    }

    override fun onCleared() {
        super.onCleared()
        this.compositeDiasposable.clear()
    }
}