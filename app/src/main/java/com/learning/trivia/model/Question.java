package com.learning.trivia.model;

public class Question {
    private String question;
    private boolean isTrue;

    public Question() {

    }

    public Question(String question, boolean isTrue) {
        this.question = question;
        this.isTrue = isTrue;
    }

    public String getQuestion() {
        return question;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }

    @Override
    public String toString() {
        return "Question: " + getQuestion() + "\t" + "Answer: " + isTrue();
    }
}
