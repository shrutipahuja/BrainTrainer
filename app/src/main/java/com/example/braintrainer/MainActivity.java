package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView number1, number2, outcomeText, counterText, totalQuestions, totalScore, plusText, slashText;
    Button startButton, playAgain;
    CountDownTimer countDownTimer;
    ImageView counterTile, scoreTile, gridBackground;
    GridLayout gridLayout;
    int correctAnswer, randomNumberX;
    final int min = 1, max = 20, counterLimit = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number1 = findViewById(R.id.number1TextView);
        number2 = findViewById(R.id.number2TextView);
        counterText = findViewById(R.id.counterTextView);
        startButton = findViewById(R.id.startButton);
        playAgain = findViewById(R.id.playAgainButton);
        outcomeText = findViewById(R.id.outcomeTextView);
        gridLayout = findViewById(R.id.gridLayout);
        totalQuestions = findViewById(R.id.totalQuestionsTextView);
        totalScore = findViewById(R.id.totalScoreTextView);
        counterTile = findViewById(R.id.counterTile);
        scoreTile = findViewById(R.id.scoreTile);
        plusText = findViewById(R.id.plusTextView);
        slashText = findViewById(R.id.slashTextView);
        gridBackground = findViewById(R.id.fourSquaresImage);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startButton.setVisibility(View.INVISIBLE);
                number1.setVisibility(View.VISIBLE);
                number2.setVisibility(View.VISIBLE);
                counterText.setVisibility(View.VISIBLE);
                gridLayout.setVisibility(View.VISIBLE);
                totalQuestions.setVisibility(View.VISIBLE);
                totalScore.setVisibility(View.VISIBLE);
                counterTile.setVisibility(View.VISIBLE);
                scoreTile.setVisibility(View.VISIBLE);
                plusText.setVisibility(View.VISIBLE);
                slashText.setVisibility(View.VISIBLE);
                gridBackground.setVisibility(View.VISIBLE);
                for (int i = 0; i < 4; i++) {
                    gridLayout.getChildAt(i).setVisibility(View.VISIBLE);
                }
                startGame();
            }
        });

        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                counterText.setText(String.valueOf(l / 1000));

            }

            @Override
            public void onFinish() {
                playAgain.setVisibility(View.VISIBLE);
                outcomeText.setText(getResources().getString(R.string.done));
                counterText.setText(String.valueOf(counterLimit));
                for (int i = 0; i < gridLayout.getChildCount(); i++) {
                    gridLayout.getChildAt(i).setOnClickListener(null);
                }
            }
        };
    }

    public void startGame() {
        countDownTimer.start();
        newQuestion();
    }

    public void newQuestion() {
        int randomNumber1 = new Random().nextInt((max - min) + 1) + min;
        int randomNumber2 = new Random().nextInt((max - min) + 1) + min;
        number1.setText(String.valueOf(randomNumber1));
        number2.setText(String.valueOf(randomNumber2));
        correctAnswer = randomNumber1 + randomNumber2;


        int randomAnswerPosition = new Random().nextInt(4);
        // Log.i("Random number generated", String.valueOf(randomAnswerPosition));
        TextView correctAnswerPosition = (TextView) gridLayout.getChildAt(randomAnswerPosition);
        correctAnswerPosition.setText(String.valueOf(correctAnswer));

        for (int i = 0; i < 4; i++) {
            TextView answerPosition = (TextView) gridLayout.getChildAt(i);
            if (answerPosition.getText() == "") {
                randomNumberX = new Random().nextInt((max - min) + 1) + min;
                answerPosition.setText(String.valueOf(randomNumberX));
            }
        }

        for (int i = 0; i < 3; i++) {
            TextView answerPosition = (TextView) gridLayout.getChildAt(i);
            TextView answerPositionNext = (TextView) gridLayout.getChildAt(i + 1);
            if (answerPosition.getText() == answerPositionNext.getText()) {
                answerPosition.setText(String.valueOf(randomNumberX + 1));
            }
        }
    }

    public void getOutcome(View view) {
        int total = (Integer.valueOf(totalQuestions.getText().toString()));
        int score = (Integer.valueOf(totalScore.getText().toString()));
        TextView answerSelected = (TextView) view;
        String answer = (String) answerSelected.getText();
        if (String.valueOf(correctAnswer).equals(answer)) {
            outcomeText.setVisibility(View.VISIBLE);
            outcomeText.setText(getResources().getString(R.string.correct));
            score++;
            total++;
            totalQuestions.setText(String.valueOf(total));
            totalScore.setText(String.valueOf(score));
        } else {
            outcomeText.setVisibility(View.VISIBLE);
            outcomeText.setText(getResources().getString(R.string.incorrect));
            total++;
            totalQuestions.setText(String.valueOf(total));
        }
        newQuestion();
    }


    public void startNewGame(View view) {
        totalScore.setText("0");
        totalQuestions.setText("0");
        outcomeText.setVisibility(View.INVISIBLE);
        playAgain.setVisibility(View.INVISIBLE);
        countDownTimer.start();
        startGame();
    }
}
