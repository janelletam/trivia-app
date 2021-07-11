package com.learning.trivia.data;

import com.learning.trivia.model.Question;

import java.util.ArrayList;

public interface FillRepositoryAsyncResponse {
    void processFinished(ArrayList<Question> questionArrayList);
}
