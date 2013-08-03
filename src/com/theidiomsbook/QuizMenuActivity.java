package com.theidiomsbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.theidiomsbook.libraries.UIHelper;
import com.theidiomsbook.model.Quiz;

public class QuizMenuActivity extends SherlockActivity {
	private Quiz quiz;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz_menu);

		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		String numQuestions = sharedPref.getString("settings_num_questions", "10");
		((EditText) findViewById(R.id.num_questions)).setText(numQuestions);
		String numResponses = sharedPref.getString("settings_num_responses", "5");
		((EditText) findViewById(R.id.num_responses)).setText(numResponses);
		String language = sharedPref.getString("settings_quiz_language", "english");
		if (language.equals("english"))
			((RadioGroup) findViewById(R.id.quiz_language)).check(R.id.rbEnglish);
		else if (language.equals("spanish"))
			((RadioGroup) findViewById(R.id.quiz_language)).check(R.id.rbSpanish);

		TextView btnStartQuiz = (TextView) findViewById(R.id.BtnStartQuiz);
		btnStartQuiz.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (getFormValues()) {
					Intent intent = new Intent(QuizMenuActivity.this, QuizQuestionActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
					startActivity(intent);
				}
			}

		});
	}

	@Override
	public void onResume() {
		super.onResume();
		quiz = Quiz.getInstance();
		quiz.reset();

		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		String numQuestions = sharedPref.getString("settings_num_questions", "10");
		((EditText) findViewById(R.id.num_questions)).setText(numQuestions);
		String numResponses = sharedPref.getString("settings_num_responses", "5");
		((EditText) findViewById(R.id.num_responses)).setText(numResponses);
		String language = sharedPref.getString("settings_quiz_language", "english");
		if (language.equals("english"))
			((RadioGroup) findViewById(R.id.quiz_language)).check(R.id.rbEnglish);
		else if (language.equals("spanish"))
			((RadioGroup) findViewById(R.id.quiz_language)).check(R.id.rbSpanish);
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
			super.onBackPressed();
			return true;
		case R.id.action_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private Boolean getFormValues() {
		int iNumQuestions = 0, iNumResponses = 0;

		String sNumQuestions = ((EditText) findViewById(R.id.num_questions)).getText().toString()
		    .trim();
		String sNumResponses = ((EditText) findViewById(R.id.num_responses)).getText().toString()
		    .trim();
		RadioGroup rgLanguage = (RadioGroup) findViewById(R.id.quiz_language);
		try {
			iNumQuestions = Integer.valueOf(sNumQuestions);
		} catch (NumberFormatException e) {
			UIHelper.showMessage(getApplicationContext(),
			    getResources().getString(R.string.msg_num_questions_bad_number));
			return false;
		}
		try {
			iNumResponses = Integer.valueOf(sNumResponses);
		} catch (NumberFormatException e) {
			UIHelper.showMessage(getApplicationContext(),
			    getResources().getString(R.string.msg_num_responses_bad_number));
			return false;
		}

		if (!validateForm(iNumQuestions, iNumResponses))
			return false;

		int viewLanguageID = rgLanguage.getCheckedRadioButtonId();
		quiz.setQuizLanguage(getLanguageFromSelectedView(viewLanguageID));

		return true;
	}

	private String getLanguageFromSelectedView(int viewLanguageID) {
		if (viewLanguageID == R.id.rbSpanish)
			return "spanish";
		else
			return "english";
	}

	private Boolean validateForm(int numQuestions, int numResponses) {
		if ((numQuestions < 1) || (numQuestions > 50)) {
			UIHelper.showMessage(getApplicationContext(),
			    getResources().getString(R.string.msg_num_questions_empty));
			return false;
		} else {
			quiz.setNumQuestions(numQuestions);
		}

		if ((numResponses < 2) || (numResponses > 10)) {
			UIHelper.showMessage(getApplicationContext(),
			    getResources().getString(R.string.msg_num_responses_empty));
			return false;
		} else {
			quiz.setNumResponses(numResponses);
		}

		return true;
	}

}
