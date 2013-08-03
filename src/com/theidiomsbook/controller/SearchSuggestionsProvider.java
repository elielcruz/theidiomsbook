package com.theidiomsbook.controller;

import android.content.SearchRecentSuggestionsProvider;

public class SearchSuggestionsProvider extends SearchRecentSuggestionsProvider {
	
	public final static String AUTHORITY = "com.theidiomsbook.controller.SearchSuggestionsProvider";
  public final static int MODE = DATABASE_MODE_QUERIES;

  public SearchSuggestionsProvider() {
      setupSuggestions(AUTHORITY, MODE);
  }

}
