package com.learning.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.learning.trivia.controller.AppController;
import com.learning.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private List<Question> mQuestionBank = new ArrayList<>();
    private final String URL = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    private final int QUESTION_INDEX = 0;
    private final int ANSWER_INDEX = 1;

    public Repository() {
        Log.d("JSON", "Fetched");
    }

    public List<Question> getQuestionsAndAnswers(FillRepositoryAsyncResponse callBack) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, response -> {
            int responseLength = response.length();
            String question = "";
            boolean isTrue = false;

            for (int i = 0; i < responseLength; i++) {
                try {
                    // Retrieve data
                    question = response.getJSONArray(i).get(QUESTION_INDEX).toString();
                    isTrue = (boolean) response.getJSONArray(i).get(ANSWER_INDEX);
                    mQuestionBank.add(new Question(question, isTrue));

                    Log.d("JSON Array", "Question: " + question);
                    Log.d("JSON Array", "Answer: " + String.valueOf(isTrue));
                    Log.d("JSON Array", "Added questions to list");
                    Log.d("Size of question bank", "Adding..." + String.valueOf(mQuestionBank.size()));
                } catch (JSONException e) {
                    Log.d("JSON Array", "Could not retrieve individual array");
                    e.printStackTrace();
                }
            }

            // Ensures callBack is valid
            if (callBack != null) callBack.processFinished((ArrayList<Question>) mQuestionBank);
            Log.d("Size of question bank", "Finished this part: " + String.valueOf(mQuestionBank.size()));

        }, error -> Log.d("JSON", "Error in requesting data"));

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        Log.d("Size of question bank", "Finished: " + String.valueOf(mQuestionBank.size()));

        return mQuestionBank;
    }
}
