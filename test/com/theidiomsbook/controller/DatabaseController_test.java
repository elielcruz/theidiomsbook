package com.theidiomsbook.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;

import com.theidiomsbook.R;

public class DatabaseController_test extends android.test.ActivityTestCase {
	private Context context;

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		context = getInstrumentation().getContext();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testSelectedItemsByID() {
		String[] IDs = {"4", "2", "3"};
		Cursor cursor = DictionaryDatabase.load(context).getIdiomsByID(IDs);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			System.out.println(cursor.getString(cursor.getColumnIndex(DictionaryDatabase.COLUMN_SPANISH)));
			cursor.moveToNext();
		}
		assertTrue(cursor.getCount() == 3);
		cursor.close();
	}
	
	@Test
	public void testJSONDictionaryWellFormed() {
		final Resources resources = context.getResources();
		
    try {
    	InputStream inputStream = resources.openRawResource(R.raw.tib);
    	BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
	    JSONParser parser = new JSONParser();
	    
	    Object obj = parser.parse(reader);
	    JSONArray JSONRoot = (JSONArray)obj;
	    Iterator<?> iterator = JSONRoot.iterator();
	    while(iterator.hasNext()) {
	    	 JSONObject JSONIdiom = (JSONObject)iterator.next();
	    	 String id = (String)JSONIdiom.get("nid");
	    	 System.out.println(id);
	    }
	    
    } catch (UnsupportedEncodingException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
    } catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
    } catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
    }
	}

}
