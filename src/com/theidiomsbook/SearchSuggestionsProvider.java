package com.theidiomsbook;

import android.content.SearchRecentSuggestionsProvider;

public class SearchSuggestionsProvider extends SearchRecentSuggestionsProvider {
	
	public final static String AUTHORITY = "com.theidiomsbook.SearchSuggestionsProvider";
  public final static int MODE = DATABASE_MODE_QUERIES;

  public SearchSuggestionsProvider() {
      setupSuggestions(AUTHORITY, MODE);
  }

}
