package com.example.myapplication2;

public class QuizState {
    private String userID;
    private boolean quiz;

    public QuizState(){}

    public QuizState(String userID, boolean quiz){
        this.userID = userID;
        this.quiz = quiz;
    }

    public String getUserID() {return userID; }
    public boolean getQuiz() {return quiz; }


}
