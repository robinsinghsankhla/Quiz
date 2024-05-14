package com.robin.quiz.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.robin.quiz.QuestionActivity
import com.robin.quiz.databinding.QuizRecLayoutBinding
import com.robin.quiz.model.QuizModel

class QuizAdapter(private val quizList: MutableList<QuizModel>) :
    RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
            val bind = QuizRecLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return QuizViewHolder(bind)
    }

    override fun getItemCount() = quizList.size

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.bindView(quizList[position])
    }
    class QuizViewHolder(val binding: QuizRecLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(model : QuizModel){
            //bind all view
           binding.apply {
               quizRecViewTitle.text = model.title
               quizRecViewSubtitle.text = model.subTitle
               quizRecViewTimer.text = model.time+" min"
               root.setOnClickListener {
                   val intent = Intent(root.context,QuestionActivity::class.java)
                   //set data
                   QuestionActivity.questionList = model.question
                   QuestionActivity.time = model.time
                   root.context.startActivity(intent)
               }
           }
        }
    }
}