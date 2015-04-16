package ufrj.museunacional.testedemeteorito;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class QuestionsActivity extends Activity {

	private ArrayList<Question> mQuestions;
	private Question mCurrentQuestion;

	private TextView tvText;
	private ImageView iv;

	private final Handler mHandler = new Handler();
	private static final int RESET_TIME = 45000;
	
	private final Runnable mReset = new Runnable() {
		
		@Override
		public void run() {
			finish();
			
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_questions);

		tvText = (TextView) findViewById(R.id.tvQuestionText);
		iv = (ImageView) findViewById(R.id.ivQuestionImage);

		getQuestionsScheme();
		setListeners();
		setCurrentQuestionNumber(1);
	}

	private void setCurrentQuestionNumber(int questionNumber) {
		for (Question q : mQuestions) {
			if (q.getNumber() == questionNumber) {
				mCurrentQuestion = q;
			}
		}

		((TextView) findViewById(R.id.tvQuestionTitle))
				.setText(mCurrentQuestion.getTitle());

		if (mCurrentQuestion.getText().isEmpty()) {
			tvText.setVisibility(View.GONE);
		} else {
			tvText.setText(mCurrentQuestion.getText());
			tvText.setVisibility(View.VISIBLE);
		}

		if (mCurrentQuestion.getImage().isEmpty()) {
			iv.setVisibility(View.GONE);
		} else {
			// TODO set image
			iv.setVisibility(View.VISIBLE);
		}
	}

	private void setListeners() {
		((Button) findViewById(R.id.btQuestionYes))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						switch (mCurrentQuestion.getYes_link()) {
						case Question.AFFIRMATIVE_LINK:
							startActivity(new Intent(QuestionsActivity.this,
									ResultActivity.class).putExtra(
									ResultActivity.IS_METEORITE_KEY, true));
							finish();
							break;
						case Question.NEGATIVE_LINK:
							startActivity(new Intent(QuestionsActivity.this,
									ResultActivity.class).putExtra(
									ResultActivity.IS_METEORITE_KEY, false));
							finish();
							break;
						default:
							setCurrentQuestionNumber(mCurrentQuestion
									.getYes_link());
						}

					}
				});
		((Button) findViewById(R.id.btQuestionNo))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						switch (mCurrentQuestion.getNo_link()) {
						case Question.AFFIRMATIVE_LINK:
							startActivity(new Intent(QuestionsActivity.this,
									ResultActivity.class).putExtra(
									ResultActivity.IS_METEORITE_KEY, true));
							finish();
							break;
						case Question.NEGATIVE_LINK:
							startActivity(new Intent(QuestionsActivity.this,
									ResultActivity.class).putExtra(
									ResultActivity.IS_METEORITE_KEY, false));
							finish();
							break;
						default:
							setCurrentQuestionNumber(mCurrentQuestion
									.getNo_link());
						}

					}
				});
		((ImageButton) findViewById(R.id.btCloseQuestions)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		mHandler.postDelayed(mReset, RESET_TIME);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mHandler.removeCallbacks(mReset);
		mHandler.postDelayed(mReset, RESET_TIME);
		return super.onTouchEvent(event);
	}

	private void getQuestionsScheme() {

		String s = Util.getStringFromInputStream(getResources()
				.openRawResource(R.raw.questions));
		Gson g = new Gson();
		mQuestions = g.fromJson(s, new TypeToken<ArrayList<Question>>() {
		}.getType());

	}
}
