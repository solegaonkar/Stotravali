package com.github.solegaonkar.stotravali.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.solegaonkar.stotravali.R;
import com.github.solegaonkar.stotravali.model.DAO;

public class MainActivity extends AppCompatActivity {

	private DAO dao = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initAppData();
		refreshView();
	}


	private void initAppData() {
		dao = DAO.init(getApplicationContext());
	}

	/**
	 * Create the view depending upon the state of the application
	 */
	private void refreshView() {
		if (dao.getText() == null) {
			createButtons();
		} else {
			createText();
		}
	}

	private void createText() {
		ScrollView scrollView = findViewById(R.id.scrollView);
		scrollView.removeAllViews();
		TextView textView = new TextView(this);
		textView.setText(dao.getText());
		scrollView.addView(textView);
	}

	private void createButtons() {
		ScrollView scrollView = findViewById(R.id.scrollView);
		scrollView.removeAllViews();

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);

		for (String key : dao.getKeySet()) {
			layout.addView(createIndexButton(key));
			layout.addView(createDummyView());
		}
		scrollView.addView(layout);
	}

	private void click(String title) {
		if (!"".equals(title)) {
			dao.setKey(title);
			refreshView();
		}
	}

	public void backClick(View view) {
		if (dao.back())
			refreshView();
	}

	public void minimizeApp() {
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(startMain);
	}

	private Button createIndexButton(String text) {
		Button b = new Button(this);
		b.setText(text);
		b.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
		b.setTextColor(getResources().getColor(R.color.colorText));
		b.setAlpha(0.8f);

		b.setOnClickListener(new View.OnClickListener() {
								 @Override
								 public void onClick(View v) {
									 click(((Button) v).getText().toString());
								 }
							 }
		);

		return b;
	}

	private View createDummyView() {
		View v = new View(this);
		v.setMinimumHeight(20);
		return v;
	}

}
