package com.theidiomsbook;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.provider.SearchRecentSuggestions;
import android.text.InputType;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.theidiomsbook.controller.SearchSuggestionsProvider;

public class SettingsActivity extends SherlockPreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences);

		Preference customPref = (Preference) findPreference("delete-history-cache");
		customPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			public boolean onPreferenceClick(Preference preference) {
				SearchRecentSuggestions suggestions = new SearchRecentSuggestions(getApplicationContext(),
						SearchSuggestionsProvider.AUTHORITY, SearchSuggestionsProvider.MODE);
				suggestions.clearHistory();
				
				Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_history_deleted), Toast.LENGTH_SHORT);
				toast.show();

				return true;
			}

		});
		
		EditTextPreference numQuestions = (EditTextPreference)findPreference("settings_num_questions");
		numQuestions.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
		EditTextPreference numResponses = (EditTextPreference)findPreference("settings_num_responses");
		numResponses.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			super.onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
