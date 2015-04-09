package ufrj.museunacional.testedemeteorito;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class ResultActivity extends Activity{
	
	private static final int DELAY_MS = 5000;
	
	public static final String IS_METEORITE_KEY = "IS_METEORITE";
	
	TextView tvTitle;
	TextView tvText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_result);
		
		tvTitle = (TextView) findViewById(R.id.tvTitleResult);
		tvText = (TextView) findViewById(R.id.tvTextResult);
		
		setTestResult(getIntent().getBooleanExtra(IS_METEORITE_KEY, false));
		setTimer();
		
	}
	
	private void setTimer(){
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				finish();
			}
		}, DELAY_MS);
	}
	
	private void setTestResult(boolean isMeteorite){
		
		if(isMeteorite){
			tvTitle.setText(getString(R.string.is_meteorite_title));
			tvText.setText(getString(R.string.is_meteorite_text));
		}else{
			tvTitle.setText(getString(R.string.isnt_meteorite_title));
			tvText.setText(getString(R.string.isnt_meteorite_text));
		}
		
	}

}
