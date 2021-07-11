package com.learning.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.learning.trivia.data.Repository;
import com.learning.trivia.databinding.ActivityMainBinding;
import com.learning.trivia.model.Question;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final String URL = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    private ActivityMainBinding mBinding;
    private List<Question> mQuestions;
    private String mCurrentQuestion = "";
    private int mCurrentQuestionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Trivia);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        // Fill question bank
        mQuestions = new Repository().getQuestionsAndAnswers(questionArrayList -> {
            if (questionArrayList.size() != 0) Log.d("onCreate: ", questionArrayList.get(0).toString());
            Collections.shuffle(mQuestions);

            mBinding.textQuestion.setText("");
            mBinding.textQuestion.setVisibility(View.VISIBLE);
            displayQuestion();
            updateQuestionNumber();
        });
    }

    private void displayQuestion() {
        mCurrentQuestion = mQuestions.get(mCurrentQuestionIndex).getQuestion();
        mBinding.textQuestion.setText(mCurrentQuestion);
    }

    // Buttons pressed
    public void truePressed(View view) {
        if (isCorrect(true)) {
            showToast(true);
        }
        else {
            showToast(false);
        }
    }

    public void falsePressed(View view) {
        if (isCorrect(false)) {
            showToast(true);
        }
        else {
            showToast(false);
        }
    }

    public void nextPressed(View view) {
        if ((mCurrentQuestionIndex + 1) < mQuestions.size()) {
            mCurrentQuestionIndex++;
        }
        else mCurrentQuestionIndex = 0;
        updateQuestionNumber();
        displayQuestion();

        Log.d("Navigate", "Index: " + mCurrentQuestionIndex + "\tSize of list: " + mQuestions.size());
    }

    public void prevPressed(View view) {
        if (mCurrentQuestionIndex == 0) mCurrentQuestionIndex = (mQuestions.size() - 1);
        else mCurrentQuestionIndex -= 1;
        updateQuestionNumber();
        displayQuestion();

        Log.d("Navigate", "Index: " + mCurrentQuestionIndex + "\tSize of list: " + mQuestions.size());
    }

    // Logic & actions --> update UI

    private boolean isCorrect(boolean answerSubmitted) {
        Log.d("Answer submitted", "User: " + answerSubmitted + "\t" + "Actual: " + mQuestions.get(mCurrentQuestionIndex).isTrue());

        if (mQuestions.get(mCurrentQuestionIndex).isTrue() == answerSubmitted) {
            return true;
        }
        else return false;
    }

    private void showToast(boolean answerCorrect) {
        if (answerCorrect) Snackbar.make(mBinding.navbar, R.string.snackbar_correct_message, Snackbar.LENGTH_SHORT).show();
        else Snackbar.make(mBinding.navbar, R.string.snackbar_incorrect_message, Snackbar.LENGTH_SHORT).show();
    }

    private void updateQuestionNumber() {
        mBinding.textQuestionNumber.setText(String.format(Locale.ENGLISH, ("Q%d/%d"), (mCurrentQuestionIndex + 1), mQuestions.size()));
    }
}