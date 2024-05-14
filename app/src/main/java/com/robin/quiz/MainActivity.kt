package com.robin.quiz

import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Visibility
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.robin.quiz.adapter.QuizAdapter
import com.robin.quiz.databinding.ActivityMainBinding
import com.robin.quiz.model.QuestionModel
import com.robin.quiz.model.QuizModel


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var datalist: MutableList<QuizModel>
    lateinit var adapter: QuizAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        datalist = mutableListOf()
        getDataFromFirebase()

    }

    private fun getDataFromFirebase() {
        //firebase realTime data
        binding.progressCircular.visibility = View.VISIBLE
        FirebaseDatabase.getInstance().reference
            .get()
            .addOnSuccessListener {
                binding.progressCircular.visibility = View.INVISIBLE
                if (it.exists()){
                    for (model in it.children){
                        val dataModel = model.getValue(QuizModel::class.java)
                        datalist.add(dataModel!!)
                    }
                }
                getQuizAdapter()
            }

    }

    private fun getQuizAdapter() {
        adapter = QuizAdapter(datalist)
        binding.recQuiz.layoutManager = LinearLayoutManager(this)
        binding.recQuiz.adapter = adapter
    }
}