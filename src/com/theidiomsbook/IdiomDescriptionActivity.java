package com.theidiomsbook;

import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.theidiomsbook.controller.DictionaryDatabase;

public class IdiomDescriptionActivity extends SherlockActivity {
	private ViewPager idiomPager;
	private IdiomPagerAdapter pagerAdapter;
	private int numPages;
	private Context context;

	int idiomId;
	String sourceLanguage, targetLanguage;
	String sourceIdiom, targetIdiom, spanishDescription, englishDescription, examples;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_idiom_pager);

		context = this;
		numPages = DictionaryDatabase.load(context).getIdiomsCount();
		idiomId = Integer.valueOf(getIntent().getExtras().getString("idiom-id"));
		sourceLanguage = getIntent().getExtras().getString("source-language");
		targetLanguage = getIntent().getExtras().getString("target-language");

		pagerAdapter = new IdiomPagerAdapter();
		idiomPager = (ViewPager) findViewById(R.id.idiomPager);
		idiomPager.setAdapter(pagerAdapter);
		idiomPager.setCurrentItem(idiomId - 1); // Pager is zero-based. IdiomsId
																						// start in 1.
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
			Intent intent = new Intent(this, IdiomsListActivity.class);
			intent.putExtra("language", sourceLanguage);
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

	private void getIdiomData() {
		Cursor cursor = DictionaryDatabase.load(this).getIdiomsByID(
		    new String[] { String.valueOf(idiomId) });
		if (cursor == null)
			return;
		cursor.moveToFirst();
		sourceIdiom = cursor.getString(cursor.getColumnIndex(DictionaryDatabase
		    .getColumnNameByLanguage(sourceLanguage)));
		targetIdiom = cursor.getString(cursor.getColumnIndex(DictionaryDatabase
		    .getColumnNameByLanguage(targetLanguage)));
		spanishDescription = cursor.getString(cursor
		    .getColumnIndex(DictionaryDatabase.COLUMN_SPANISH_DESCRIPTION));
		englishDescription = cursor.getString(cursor
		    .getColumnIndex(DictionaryDatabase.COLUMN_ENGLISH_DESCRIPTION));
		examples = cursor.getString(cursor.getColumnIndex(DictionaryDatabase.COLUMN_EXAMPLES));

	}

	private void setIdiomId(int id) {
		this.idiomId = id;
	}

	private class IdiomPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return numPages;
		}

		/**
		 * Create the page for the given position. The adapter is responsible for
		 * adding the view to the container given here, although it only must ensure
		 * this is done by the time it returns from
		 * {@link #finishUpdate(android.view.ViewGroup)}.
		 * 
		 * @param collection
		 *          The containing View in which the page will be shown.
		 * @param position
		 *          The page position to be instantiated.
		 * @return Returns an Object representing the new page. This does not need
		 *         to be a View, but can be some other container of the page.
		 */
		@Override
		public Object instantiateItem(ViewGroup collection, int position) {
			LayoutInflater inflater = LayoutInflater.from(context);
			LinearLayout idiomScreen = (LinearLayout) inflater.inflate(
			    R.layout.activity_idiom_description, null);

			setIdiomId(position + 1);
			getIdiomData();

			TextView tvSourceIdiom = (TextView) idiomScreen.findViewById(R.id.sourceIdiom);
			TextView tvTargetIdiom = (TextView) idiomScreen.findViewById(R.id.targetIdiom);
			TextView tvDescription = (TextView) idiomScreen.findViewById(R.id.idiomDescription);
			TextView tvExamples = (TextView) idiomScreen.findViewById(R.id.idiomExamples);

			if (spanishDescription == null) {
				spanishDescription = getResources().getString(R.string.msg_no_additional_information);
				tvDescription.setTextAppearance(context, R.style.ItalicText);
			}
			if (englishDescription == null) {
				englishDescription = getResources().getString(R.string.msg_no_additional_information);
				tvDescription.setTextAppearance(context, R.style.ItalicText);
			}
			if (examples == null) {
				examples = getResources().getString(R.string.msg_no_examples);
				tvExamples.setTextAppearance(context, R.style.ItalicText);
			}

			tvSourceIdiom.setText(sourceIdiom);
			tvTargetIdiom.setText(targetIdiom);
			tvExamples.setText(examples);

			if (Locale.getDefault().getLanguage().equals("es")) {
				tvDescription.setText(spanishDescription);
			} else {
				tvDescription.setText(englishDescription);
			}

			((ViewPager) collection).addView(idiomScreen);

			return idiomScreen;
		}

		@Override
		public void destroyItem(ViewGroup collection, int position, Object view) {
			// collection.removeAllViews();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return (view == object);
		}

		@Override
		public void finishUpdate(ViewGroup arg0) {
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(ViewGroup arg0) {
		}

	}

}
