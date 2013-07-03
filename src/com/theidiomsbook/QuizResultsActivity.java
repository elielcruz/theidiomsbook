package com.theidiomsbook;

import java.util.Iterator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.theidiomsbook.Quiz.Response;

public class QuizResultsActivity extends SherlockActivity {
	private Quiz quiz;
	private String sourceLanguage, targetLanguage;
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz_results);
		
		quiz = Quiz.getInstance();
		sourceLanguage = quiz.getQuizLanguage();
		targetLanguage = (sourceLanguage.equals("spanish")) ? "english" : "spanish";
		
		fillActivity();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportMenuInflater().inflate(R.menu.actionbar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(QuizResultsActivity.this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		case R.id.action_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void fillActivity() {
		setOverviewMessage();
		
		((TextView)findViewById(R.id.BtnRestartQuiz)).setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				quiz.reset();
				Intent intent = new Intent(QuizResultsActivity.this, QuizMenuActivity.class);
				startActivity(intent);
			}
		});
		
		setSummaryResultsList();
	}
	
	private void setOverviewMessage() {
		int correctAnswers = quiz.getCorrectAnswers();
		int numQuestions = quiz.getNumQuestions();
		String message = getResources().getString(R.string.lbl_obtained) + " " 
										+ correctAnswers + " " 
										+ getResources().getString(R.string.lbl_of) + " " 
										+ numQuestions + " ("
										+ (int)(correctAnswers/(float)numQuestions*100) + "%)";
		((TextView)findViewById(R.id.resultsOverview)).setText(message);
	}
	
	private void setSummaryResultsList() {
		LinearLayout viewResultsList = (LinearLayout)findViewById(R.id.ResultsSummary);
		LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
		Iterator<Response> iterator = quiz.getCheckedResponses().iterator();		
		while(iterator.hasNext()) {
			final Response response = iterator.next();
			LinearLayout viewResult = (LinearLayout)inflater.inflate(R.layout.quiz_result_row, null);			
			if(response.isGuessed())
				((TextView)viewResult.findViewById(R.id.quiz_result_row_icon_correct)).setVisibility(View.VISIBLE);
			else
				((TextView)viewResult.findViewById(R.id.quiz_result_row_icon_incorrect)).setVisibility(View.VISIBLE);
			
			TextView viewResultId = (TextView)viewResult.findViewById(R.id.row_result_id);
			viewResultId.setText(String.valueOf(response.getIdiomId()));
			TextView viewResultTextAsked = (TextView)viewResult.findViewById(R.id.quiz_result_row_text_asked);
			TextView viewResultTextAnswered = (TextView)viewResult.findViewById(R.id.quiz_result_row_text_answered);
			viewResultTextAsked.setText(response.getAskedIdiom());
			viewResultTextAnswered.setText(response.getTargetIdiom());
			viewResult.setOnClickListener(new OnClickListener() {
				@Override
        public void onClick(View v) {
					Intent intent = new Intent(QuizResultsActivity.this, IdiomDescriptionActivity.class);
					intent.putExtra("idiom-id", String.valueOf(response.getIdiomId()));
					intent.putExtra("source-language", sourceLanguage);
					intent.putExtra("target-language", targetLanguage);
					startActivity(intent);
        }
			});
			viewResultsList.addView(viewResult);
		}
	}
}
