package com.github.solegaonkar.stotravali.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.solegaonkar.stotravali.R;
import com.github.solegaonkar.stotravali.model.DAO;

import java.util.SortedSet;

public class ScrollingActivity extends AppCompatActivity {

	private DAO dao = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrolling);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		if (dao == null) {
			createAppData();
		}
		refreshView();
	}

	private void createAppData() {
		dao = new DAO(getApplicationContext());
	}

	/**
	 * Create the view depending upon the state of the application
	 */
	private void refreshView() {
		if (dao.getKey1() == null || dao.getKey2() == null) {
			createButtons();
		} else {
			createText();
		}
	}

	private void createText() {
		LinearLayout layout = findViewById(R.id.mainLayout);
		layout.removeAllViews();
		TextView textView = new TextView(this);
		textView.setText(dao.getText());
		layout.addView(textView);
	}

	private void createButtons() {
		LinearLayout layout = findViewById(R.id.mainLayout);
		layout.removeAllViews();
		SortedSet<String> set = null;
		if (dao.getKey1() == null) {
			set = dao.getTitleMap().navigableKeySet();
		} else {
			set = dao.getTitleMap().get(dao.getKey1()).navigableKeySet();
		}

		for (String s : set) {
			Button b = new Button(this);
			b.setText(s);
			b.setOnClickListener(new View.OnClickListener() {
									 @Override
									 public void onClick(View v) {
										 click(((Button) v).getText().toString());
									 }
								 }
			);
			layout.addView(b);
		}
	}

	private void click(String title) {
		if (dao.getKey1() == null) {
			dao.setKey1(title);
		} else {
			dao.setKey2(title);
		}
		refreshView();
	}
}
