package com.theidiomsbook;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.theidiomsbook.controller.DictionaryDatabase;

public class IdiomsListActivity extends SherlockListActivity {
	String sourceLanguage, targetLanguage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_idioms_list);

		sourceLanguage = getIntent().getExtras().getString("language");
		if (sourceLanguage.equals(getResources().getString(R.string.ltr_spanish)))
			targetLanguage = getResources().getString(R.string.ltr_english);
		else
			targetLanguage = getResources().getString(R.string.ltr_spanish);

		String sListTitle;
		if (sourceLanguage.equals("spanish"))
			sListTitle = getResources().getString(R.string.lbl_spanish_list_title);
		else
			sListTitle = getResources().getString(R.string.lbl_english_list_title);
		
		TextView tvListTitle = (TextView) findViewById(R.id.ListTitle);
		tvListTitle.setText(sListTitle);

		fillList(sourceLanguage, targetLanguage);
		getListView().setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
				Intent intent = new Intent(IdiomsListActivity.this, IdiomDescriptionActivity.class);
				TextView idiomId = (TextView) view.findViewById(R.id.row_idiom_id);
				intent.putExtra("idiom-id", idiomId.getText());
				intent.putExtra("source-language", sourceLanguage);
				intent.putExtra("target-language", targetLanguage);
				startActivity(intent);
				Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.msg_toast_slide_screen), Toast.LENGTH_LONG);
				toast.show();
			}

		});
		getListView().setFastScrollEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportMenuInflater().inflate(R.menu.actionbar_search, menu);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		setSearchBoxHint(searchView);
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(true);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
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

	@Override
	public void startActivity(Intent intent) {

		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			intent.putExtra("source-language", sourceLanguage);
			intent.putExtra("target-language", targetLanguage);
		}
		super.startActivity(intent);

	}

	private void fillList(String sourceLanguage, String targetLanguage) {
		Cursor itemCursor = null;

		itemCursor = DictionaryDatabase.load(this).getAllIdioms(sourceLanguage, targetLanguage);

		String[] from = new String[] { DictionaryDatabase.COLUMN_ID,
		    DictionaryDatabase.getColumnNameByLanguage(sourceLanguage),
		    DictionaryDatabase.getColumnNameByLanguage(targetLanguage) };
		int[] to = new int[] { R.id.row_idiom_id, R.id.row_source_language, R.id.row_target_language };

		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.idiom_row, itemCursor,
		    from, to, 0);
		setListAdapter(adapter);
	}

	private void setSearchBoxHint(SearchView searchView) {
		if (sourceLanguage.equals("spanish"))
			searchView.setQueryHint(getResources().getString(R.string.lbl_menu_search_hint_spanish));
		else if (sourceLanguage.equals("english"))
			searchView.setQueryHint(getResources().getString(R.string.lbl_menu_search_hint_english));
	}
}
