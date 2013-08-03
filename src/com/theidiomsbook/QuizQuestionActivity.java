package com.theidiomsbook;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.theidiomsbook.controller.DictionaryDatabase;
import com.theidiomsbook.model.Quiz;
import com.theidiomsbook.model.Response;

public class QuizQuestionActivity extends SherlockActivity {
	private Quiz quiz;
	private String sourceLanguage, targetLanguage;
	private String sourceIdiom;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_quiz);

		quiz = Quiz.getInstance();
		sourceLanguage = quiz.getQuizLanguage();
		targetLanguage = (sourceLanguage.equals("spanish")) ? "english" : "spanish";

		setActivityHeader(true);
		fillActivity(getRandomIdioms(quiz.getNumResponses()));		
		
	}

	@Override
	public void onBackPressed() {
		showConfirmationDialog();
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
			showConfirmationDialog();
			return true;
		case R.id.action_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void setActivityHeader(boolean canStartQuiz) {
		String title;
		if (canStartQuiz) {
			title = getResources().getString(R.string.lbl_question) + " "
			    + quiz.incrementCurrentQuestion() + " " + getResources().getString(R.string.lbl_of) + " "
			    + quiz.getNumQuestions();
		} else {
			title = getResources().getString(R.string.msg_empty_list);
		}

		((TextView) findViewById(R.id.currentQuestionHeader)).setText(title);
	}

	private Set<Response> getRandomIdioms(int numRandomResponses) {
		HashSet<Response> responses = new HashSet<Response>();
		int numIdioms = DictionaryDatabase.load(this).getIdiomsCount();

		String[] aIDs = getRandomIDs(numRandomResponses, numIdioms);

		Cursor cursor = DictionaryDatabase.load(this).getIdiomsByID(aIDs);
		if (cursor == null)
			return responses;

		cursor.moveToFirst();
		
		sourceIdiom = cursor.getString(cursor.getColumnIndex(DictionaryDatabase
		    .getColumnNameByLanguage(sourceLanguage)));

		Response response = quiz.getNewResponseObject();
		response.setIdiomId(cursor.getInt(cursor.getColumnIndex("_id")));
		response.setSourceIdiom(sourceIdiom);
		response.setTargetIdiom(cursor.getString(cursor.getColumnIndex(DictionaryDatabase
		    .getColumnNameByLanguage(targetLanguage))));
		response.setAskedIdiom(sourceIdiom);
		response.setIsTheCorrectAnswer(true);
		responses.add(response);

		fillResponsesList(cursor, responses);

		while (responses.size() < numRandomResponses) {
			aIDs = getRandomIDs((numRandomResponses - responses.size()), numIdioms);
			cursor = DictionaryDatabase.load(getApplicationContext()).getIdiomsByID(aIDs);
			fillResponsesList(cursor, responses);
		}

		return responses;
	}

	private void fillResponsesList(Cursor cursor, Set<Response> responses) {
		while (cursor.moveToNext()) {
			Response response = quiz.getNewResponseObject();
			response.setIdiomId(cursor.getInt(cursor.getColumnIndex("_id")));
			response.setSourceIdiom(cursor.getString(cursor.getColumnIndex(DictionaryDatabase
			    .getColumnNameByLanguage(sourceLanguage))));
			response.setTargetIdiom(cursor.getString(cursor.getColumnIndex(DictionaryDatabase
			    .getColumnNameByLanguage(targetLanguage))));
			response.setAskedIdiom(sourceIdiom);
			response.setIsTheCorrectAnswer(false);
			responses.add(response);
		}
	}

	private String[] getRandomIDs(int numRandomResponses, int maxIdioms) {
		if (maxIdioms < 1)
			return null;
		Random randomGenerator = new Random(System.currentTimeMillis());
		String[] aIDs = new String[numRandomResponses];
		for (int i = 0; i < aIDs.length; i++)
			aIDs[i] = String.valueOf(randomGenerator.nextInt(maxIdioms) + 1);

		return aIDs;
	}

	private void fillActivity(Set<Response> responses) {
		LinearLayout llOptionsList = (LinearLayout) findViewById(R.id.optionList);

		if (responses.size() == 0)
			setActivityHeader(false);

		Iterator<Response> iterator = responses.iterator();
		while (iterator.hasNext()) {
			final Response response = iterator.next();
			if (response.isTheCorrectAnswer())
				((TextView) findViewById(R.id.CurrentIdiom)).setText(response.getSourceIdiom());
			LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
			TextView viewResponse = (TextView) inflater.inflate(R.layout.quiz_response_button, null);
			viewResponse.setText(response.getTargetIdiom());
			viewResponse.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					if (response.isTheCorrectAnswer()) {
						quiz.incrementCorrectAnswers();
						response.setIsGuessed(true);
					}
					quiz.addCheckedResponse(response);
					goToNextQuestion();
				}
			});
			llOptionsList.addView(viewResponse);
		}
	}

	private void goToNextQuestion() {
		Intent intent;

		if (quiz.getCurrentQuestion() < quiz.getNumQuestions()) {
			intent = new Intent(QuizQuestionActivity.this, QuizQuestionActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		} else
			intent = new Intent(QuizQuestionActivity.this, QuizResultsActivity.class);

		startActivity(intent);
	}

	private void showConfirmationDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.msg_exit_quiz)
		    .setTitle(getResources().getString(R.string.lbl_exit_quiz_header))
		    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
				    Intent intent = new Intent(QuizQuestionActivity.this, MainActivity.class);
				    startActivity(intent);
			    }
		    }).setNegativeButton(android.R.string.cancel, null).create().show();
	}	

}
