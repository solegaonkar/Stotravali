package com.github.solegaonkar.stotravali.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.solegaonkar.stotravali.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeMap;

/**
 * Created by vikas on 20/1/18.
 */

public class DAO {
	private static DAO instance = new DAO();
	private TreeMap<String, TreeMap<String, String>> titleMap = null;
	private Context context;

	private String key1;
	private String key2;

	private String text;

	public static DAO init(Context context) {
		instance.context = context;
		if (instance.titleMap == null)
			instance.createMap();
		return instance;
	}

	private void createMap() {
		this.titleMap = new TreeMap<>();
		try {
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

	public boolean back() {
		if (key2 != null) {
			key2 = null;
			text = null;
		} else if (key1 != null){
			key1 = null;
		} else {
			return false;
		}
		return true;
	}

	public void setKey(String key) {
		if (key1 == null) {
			key1 = key;
		} else {
			key2 = key;
			try {
				text = getFileContent(titleMap.get(key1).get(key2));
			} catch (Exception e) {
				text = context.getResources().getText(R.string.error).toString();
			}
		}
	}

	public NavigableSet<String> getKeySet() {
		if (key1 == null) {
			return titleMap.navigableKeySet();
		} else {
			return titleMap.get(key1).navigableKeySet();
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public TreeMap<String, TreeMap<String, String>> getTitleMap() {
		return titleMap;
	}

	@NonNull
	private String getFileContent(String fileName) throws Exception {
		int id = context.getResources().getIdentifier(fileName,
				"raw", context.getPackageName());
		InputStream inputStream = context.getResources().openRawResource(id);
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		br.close();
		return sb.toString();
	}

}
