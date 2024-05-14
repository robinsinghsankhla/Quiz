package com.robin.quiz.model

data class QuizModel(
    val id:String,
    val title:String,
    val subTitle:String,
    val time:String,
    val question: List<QuestionModel>)
{
 constructor():this("","","","", emptyList())

}

data class QuestionModel(
    val question:String,
    val answerList: List<String>,
    val correctAnswer:String
)
{
    constructor():this("", emptyList(),"")
}
