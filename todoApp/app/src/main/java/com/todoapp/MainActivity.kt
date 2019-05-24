package com.todoapp

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.todoapp.network.Task
import com.todoapp.network.TodoApiFactory

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity: AppCompatActivity() {

    private val tasksList = mutableListOf<Task>()

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recyclerView.adapter = TaskListAdapter(tasksList)

        fab.setOnClickListener { view ->
            showAddItemOnDialog()
        }

        getTasks()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getTasks() {
        coroutineScope.launch {
            val getTasksDeferred = TodoApiFactory.retrofitService.getTasks().await()
            Log.i("task", "\uD83D\uDD25\uD83D\uDD25 RESULT => $getTasksDeferred")
            tasksList.clear()
            tasksList.addAll(getTasksDeferred)
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    private fun createTasks(str: String) {
        coroutineScope.launch {
            val getTasksDeferred = TodoApiFactory.retrofitService.createTasks(Task("",str, false)).await()
            Log.i("🚨 task", "\uD83D\uDD25\uD83D\uDD25 RESULT => $getTasksDeferred")
            getTasks()
        }
    }

    private fun showAddItemOnDialog() {
        val editText = EditText(this)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Ajoute une nouvel tache")
            .setMessage("Ecrire sa tassk ")
            .setView(editText)
            .setPositiveButton("Ajouter") { _, _ -> createTasks(editText.text.toString()) }
            .setNegativeButton("Annuler") { _, _-> null }
            .create()
        dialog.show()
    }

}