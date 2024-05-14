package com.robin.quiz

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.robin.quiz.databinding.ActivityQuestionBinding
import com.robin.quiz.databinding.FinalScoreDialogLayoutBinding
import com.robin.quiz.model.QuestionModel



class QuestionActivity : AppCompatActivity(), View.OnClickListener{
    companion object{
        var questionList: List<QuestionModel> = listOf()
        var time:String = ""
    }
    lateinit var binding: ActivityQuestionBinding
    var questionStatus = 0
    var getAnswerData = ""
    var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTime()
        setDataToView()

        //on click the option
        binding.apply {
           quizQuestionBtnNext.setOnClickListener(this@QuestionActivity)
            quizQuestionBtn01.setOnClickListener(this@QuestionActivity)
            quizQuestionBtn02.setOnClickListener(this@QuestionActivity)
            quizQuestionBtn03.setOnClickListener(this@QuestionActivity)
            quizQuestionBtn04.setOnClickListener(this@QuestionActivity)
        }
    }

    private fun setTime() {
        val totalTimeInMillis = time.toInt() * 60 * 1000L
        object : CountDownTimer(totalTimeInMillis,1000L){
            override fun onTick(millisUntilFinished: Long) {
                val second = millisUntilFinished/1000
                val minut = second/60
                val remainingSec = second%60
                binding.quizQuestionTimer.text = String.format("%02d:%02d",minut,remainingSec)
            }

            override fun onFinish() {
                // move to next
                Toast.makeText(this@QuestionActivity, "Your Time Has End", Toast.LENGTH_SHORT).show()
                finish()
            }

        }.start()
    }

    private fun setDataToView() {
        if(questionStatus == questionList.size){
            finishQuiz()
            return
        }
        getAnswerData = ""
        binding.apply {
            quizQuestionCountText.text = "Question ${questionStatus+1}/"+ questionList.size
            quizQuestionProgress.progress =
                (questionStatus.toFloat()/ questionList.size.toFloat()*100).toInt()
            quizQuestionText.text = questionList[questionStatus].question
            quizQuestionBtn01.text = questionList[questionStatus].answerList[0]
            quizQuestionBtn02.text = questionList[questionStatus].answerList[1]
            quizQuestionBtn03.text = questionList[questionStatus].answerList[2]
            quizQuestionBtn04.text = questionList[questionStatus].answerList[3]


        }
    }


    private fun finishQuiz() {
        val totalQuestion = questionList.size
        val percentage = (score.toFloat()/totalQuestion*100).toInt()
        // view binding on dialg
        val bindingDialog = FinalScoreDialogLayoutBinding.inflate(layoutInflater)
        bindingDialog.apply {
            dialogResultIndicator.progress = percentage
            dialogResultPercenctageText.text = "$percentage %"
            dialogCorrectQuestionText.text = "$score out of $totalQuestion is correct"
            if (percentage>60){
                dialogResultText.text = "Congrats! you have passed"
                dialogResultText.setTextColor(Color.BLUE)
                dialogDivider.setDividerColorResource(R.color.blue)
                dialogResultPercenctageText.setTextColor(Color.BLUE)
                dialogCorrectQuestionText.setTextColor(Color.BLUE)
            }else{
                dialogResultText.text = "Opps! you have failed"
                dialogResultText.setTextColor(Color.RED)
                dialogDivider.setDividerColorResource(R.color.red)
                dialogResultPercenctageText.setTextColor(Color.RED)
                dialogCorrectQuestionText.setTextColor(Color.RED)
            }
            //on finish button
            dialogFinishBtn.setOnClickListener {
                finish()
            }
        }

        //set dialogBuilder
        AlertDialog.Builder(this)
            .setView(bindingDialog.root)
            .setCancelable(false)
            .show()

    }

    override fun onClick(v: View?) {
        val clickBtn = v as Button
        //set color of the all button to gray
        binding.apply {
            quizQuestionBtn01.setBackgroundColor(getColor(R.color.grey))
            quizQuestionBtn02.setBackgroundColor(getColor(R.color.grey))
            quizQuestionBtn03.setBackgroundColor(getColor(R.color.grey))
            quizQuestionBtn04.setBackgroundColor(getColor(R.color.grey))
        }

        when(clickBtn.id){
            R.id.quiz_question_btn_next ->{
                //condition when no option selected

                if (getAnswerData==""){
                    Toast.makeText(this, "Please! select the answer first...", Toast.LENGTH_SHORT).show()
                    return
                }

                questionStatus++
                setDataToView()
            }
            else ->{
                clickBtn.setBackgroundColor(getColor(R.color.orange))
                getAnswerData = clickBtn.text.toString()
                if (getAnswerData == questionList[questionStatus].correctAnswer)
                    score ++
            }
        }

    }

}