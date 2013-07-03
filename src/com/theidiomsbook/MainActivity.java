package com.theidiomsbook;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.theidiomsbook.controller.DictionaryDatabase;

public class MainActivity extends SherlockActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DictionaryDatabase.load(this);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		LinearLayout btnSpanish = (LinearLayout) findViewById(R.id.BtnSpanish);
		LinearLayout btnEnglish = (LinearLayout) findViewById(R.id.BtnEnglish);
		LinearLayout btnQuiz = (LinearLayout) findViewById(R.id.BtnQuiz);
		//LinearLayout btnExit = (LinearLayout) findViewById(R.id.BtnExit);

		btnSpanish.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, IdiomsListActivity.class);
				intent.putExtra("language", "spanish");
				startActivity(intent);
			}

		});

		// English button
		btnEnglish.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, IdiomsListActivity.class);
				intent.putExtra("language", "english");
				startActivity(intent);
			}

		});

		// Quiz button
		btnQuiz.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, QuizMenuActivity.class);
				//intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			}

		});

//		// Exit button
//		btnExit.setOnClickListener(new OnClickListener() {
//			public void onClick(View arg0) {
//				finish();
//			}
//		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportMenuInflater().inflate(R.menu.actionbar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
