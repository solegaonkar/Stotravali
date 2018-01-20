package com.github.solegaonkar.stotravali;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.TreeMap;

public class ScrollingActivity extends AppCompatActivity {

	private TreeMap<String, TreeMap<String, String>> titleMap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrolling);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		String title = getIntent().getStringExtra("title");
		Toast.makeText(this, title, Toast.LENGTH_LONG);
	}

	private void addButtons() {
		LinearLayout layout = findViewById(R.id.mainLayout);
		for (String s : titleMap.keySet()) {
			Button b = new Button(this);
			b.setText(s);
			b.setOnClickListener(new View.OnClickListener() {
									 @Override
									 public void onClick(View v) {
										 clickTitle(((Button) v).getText().toString());
									 }
								 }
			);
			layout.addView(b);
		}
	}

	private void clickTitle(String s) {
		Toast.makeText(this, s, Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, ScrollingActivity.class);
		intent.putExtra("title", s);
		startActivity(intent);
	}

	private void readTitles() {
		try {
			titleMap = new TreeMap<>();
			JSONObject jsonObject = new JSONObject(getFileContent("titles"));

			for (Iterator<String> it1 = jsonObject.keys(); it1.hasNext(); ) {
				String key1 = it1.next();
				Log.i("Key1", key1);
				titleMap.put(key1, new TreeMap<String, String>());
				for (Iterator<String> it2 = jsonObject.getJSONObject(key1).keys(); it2.hasNext(); ) {
					String key2 = it2.next();
					titleMap.get(key1).put(key2, jsonObject.getJSONObject(key1).getString(key2));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@NonNull
	private String getFileContent(String fileName) throws Exception {
		getResources().getIdentifier(fileName,
				"raw", getPackageName());
		InputStream inputStream = getApplicationContext().getResources().openRawResource(R.raw.titles);
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		return sb.toString();
	}

}
