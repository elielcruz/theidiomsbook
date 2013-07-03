package com.theidiomsbook;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.theidiomsbook.controller.DictionaryDatabase;

public class SearchableActivity extends SherlockListActivity {
	private static String sourceLanguage, targetLanguage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchable);

		// Get the intent, verify the action and get the query
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
			    SearchSuggestionsProvider.AUTHORITY, SearchSuggestionsProvider.MODE);
			suggestions.saveRecentQuery(query, null);
			
			String sourceLanguage = intent.getStringExtra("source-language");
			if (sourceLanguage != null)
				SearchableActivity.sourceLanguage = sourceLanguage;			
			String targetLanguage = intent.getStringExtra("target-language");
			if (targetLanguage != null)
				SearchableActivity.targetLanguage = targetLanguage;
			
			searchIdioms(query, SearchableActivity.sourceLanguage, SearchableActivity.targetLanguage);
			getListView().setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
					Intent intent = new Intent(SearchableActivity.this, IdiomDescriptionActivity.class);
					TextView idiomId = (TextView) view.findViewById(R.id.row_idiom_id);
					intent.putExtra("idiom-id", idiomId.getText());
					intent.putExtra("source-language", SearchableActivity.sourceLanguage);
					intent.putExtra("target-language", SearchableActivity.targetLanguage);
					startActivity(intent);
				}

			});
			getListView().setFastScrollEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportMenuInflater().inflate(R.menu.actionbar_search, menu);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(true);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			super.onBackPressed();
			return true;
		case R.id.action_search:
			return true;
		case R.id.action_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void searchIdioms(String query, String sourceLanguage, String targetLanguage) {
		Cursor itemCursor = null;
		itemCursor = DictionaryDatabase.load(this).findIdioms(sourceLanguage, query);

		String[] from = new String[] { DictionaryDatabase.COLUMN_ID,
		    DictionaryDatabase.getColumnNameByLanguage(sourceLanguage),
		    DictionaryDatabase.getColumnNameByLanguage(targetLanguage) };
		int[] to = new int[] { R.id.row_idiom_id, R.id.row_source_language, R.id.row_target_language };

		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.idiom_row, itemCursor,
		    from, to, 0);
		setListAdapter(adapter);
	}

}