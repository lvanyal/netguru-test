package com.example.myapplication.view

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.response.User
import kotlinx.android.synthetic.main.activity_main.*
import android.util.DisplayMetrics


class UsersActivity : AppCompatActivity() {

    private val usersAdapter = UsersAdapter();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        usersAdapter.width = displayMetrics.widthPixels

        main_users_list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        main_users_list.adapter = usersAdapter

        val model = ViewModelProvider(this)[UsersViewModel::class.java]
        model.getUsers().observe(this, Observer<List<User>> { users ->
            usersAdapter.submitList(users)
        })
    }
}
