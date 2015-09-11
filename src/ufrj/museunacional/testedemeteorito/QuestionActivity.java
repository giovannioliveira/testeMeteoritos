package ufrj.museunacional.testedemeteorito;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class QuestionActivity extends Activity {

	private ArrayList<Question> mQuestions;
	private Question mCurrentQuestion;

	private final Handler mHandler = new Handler();
	private static final int initialCountdown = 120000;
	private static int countdown = initialCountdown; // time(ms) to finish
														// activity due idleness

	private FrameLayout flClickInterceptor;

	private TextView tvTitle;

	private Button btYes;
	private Button btNo;
	private ImageButton btClose;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_question);

		getQuestionsScheme();
		setGlobalVariables();
		setListeners();
		mCurrentQuestion = mQuestions.get(0);
		setCurrentQuestion(1);
	}

	private void getQuestionsScheme() {

		String s = Util.getStringFromInputStream(getResources()
				.openRawResource(R.raw.questions));
		Gson g = new Gson();
		mQuestions = g.fromJson(s.trim(), new TypeToken<ArrayList<Question>>() {
		}.getType());

	}

	private void setGlobalVariables() {

		tvTitle = (TextView) findViewById(R.id.tvQuestionTitle);
		btYes = (Button) findViewById(R.id.btQuestionYes);
		btNo = (Button) findViewById(R.id.btQuestionNo);
		btClose = (ImageButton) findViewById(R.id.btQuestionClose);
		flClickInterceptor = (FrameLayout) findViewById(R.id.click_interceptor);

	}

	private void setListeners() {

		btYes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (mCurrentQuestion.getYes_link()) {
				case Question.AFFIRMATIVE_LINK:
					startActivity(new Intent(QuestionActivity.this,
							ResultActivity.class).putExtra(
							ResultActivity.IS_METEORITE_KEY, true));
					finish();
					break;
				case Question.NEGATIVE_LINK:
					startActivity(new Intent(QuestionActivity.this,
							ResultActivity.class).putExtra(
							ResultActivity.IS_METEORITE_KEY, false));
					finish();
					break;
				default:
					setCurrentQuestion(mCurrentQuestion.getYes_link());
				}

			}
		});

		btNo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (mCurrentQuestion.getNo_link()) {
				case Question.AFFIRMATIVE_LINK:
					startActivity(new Intent(QuestionActivity.this,
							ResultActivity.class).putExtra(
							ResultActivity.IS_METEORITE_KEY, true));
					finish();
					break;
				case Question.NEGATIVE_LINK:
					startActivity(new Intent(QuestionActivity.this,
							ResultActivity.class).putExtra(
							ResultActivity.IS_METEORITE_KEY, false));
					finish();
					break;
				default:
					setCurrentQuestion(mCurrentQuestion.getNo_link());
				}

			}
		});

		btClose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();

			}
		});

		flClickInterceptor.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				countdown = initialCountdown;
				return false;
			}
		});

		Runnable mRunnable = new Runnable() {

			@Override
			public void run() {

				countdown--;
				if (countdown <= 0) {
					finish();
				} else {
					mHandler.postDelayed(this, 1000);
				}
				Log.v("time to close", String.valueOf(countdown));

			}
		};

		mRunnable.run();

	}

	private void setCurrentQuestion(int questionNumber) {

		for (Question q : mQuestions) {
			if (q.getNumber() == questionNumber) {
				mCurrentQuestion = q;
				break;
			}
		}

		setCurrentQuestionData();
	}

	private void setCurrentQuestionData() {

		tvTitle.setText(mCurrentQuestion.getTitle());

	}
}
