package br.gov.serpro.edson.wishlist;

import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();

	private Button buttonNew;

	private ListView listBooks;

	private ActionBar actionBar;

	private ProgressBar progressBar;

	private PendingIntent pendingIntent;
	
	private long timeAlarmManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		timeAlarmManager = prefs.getLong("notificationTime", 60000);
		
		Log.d(TAG, "Time: " + timeAlarmManager);
		
		Intent intent = new Intent(MainActivity.this, ServicoIntent.class);
		pendingIntent = PendingIntent.getService(MainActivity.this, 0, intent,
				0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar
				.getInstance().getTimeInMillis(), timeAlarmManager, pendingIntent);

		actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(false);

		ArrayAdapter<String> actionAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1);
		actionAdapter.add("Categoria 1");
		actionAdapter.add("Categoria 2");
		actionAdapter.add("Categoria 3");
		actionAdapter.add("Categoria 4");

		progressBar = (ProgressBar) findViewById(R.id.progressBar);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setListNavigationCallbacks(actionAdapter,
				new OnNavigationListener() {

					@Override
					public boolean onNavigationItemSelected(int itemPosition,
							long itemId) {
						return false;
					}
				});

		buttonNew = (Button) findViewById(R.id.button_new);
		listBooks = (ListView) findViewById(R.id.listView_books);

		buttonNew.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AsyncTask<Void, Void, ArrayAdapter<String>>() {

					@Override
					protected void onPreExecute() {
						progressBar.setVisibility(View.VISIBLE);
					}

					@Override
					protected ArrayAdapter<String> doInBackground(
							Void... params) {
						String strJson = HttpUtil
								.doGet("http://sandbox.buscape.com/service/findProductList/72577349624e6c685068513d/?keyword=galaxy%20nexus&format=json");

						ArrayAdapter<String> adapter = new ArrayAdapter<String>(
								MainActivity.this,
								android.R.layout.simple_list_item_1,
								android.R.id.text1);

						try {
							JSONObject json = new JSONObject(strJson);
							JSONArray array = json.getJSONArray("product");
							for (int index = 0; index < array.length(); index++) {
								JSONObject jsonObject = array
										.getJSONObject(index);
								JSONObject jsonProduct = jsonObject
										.getJSONObject("product");
								adapter.add(jsonProduct
										.getString("productname"));
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						return adapter;
					}

					@Override
					protected void onPostExecute(ArrayAdapter<String> result) {
						progressBar.setVisibility(View.GONE);
						listBooks.setAdapter(result);
					}

				}.execute();
			}
		});

		// String[] columns = new String[] { "_id", "name", "author",
		// "publishing" };
		//
		// MatrixCursor cursor = new MatrixCursor(columns);
		// cursor.addRow(new String[] { "1", "Livro 1", "Jake", "Um" });
		// cursor.addRow(new String[] { "2", "Livro 2", "John", "Dois" });
		// cursor.addRow(new String[] { "3", "Livro 3", "Maria", "Três" });
		// cursor.addRow(new String[] { "4", "Livro 4", "João", "Um" });
		//
		// BooksAdapter adapter = new BooksAdapter(this, cursor);
		// adapter.notifyDataSetChanged();
		// listBooks.setAdapter(adapter);
		// listBooks.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// Intent intent = new Intent(MainActivity.this,
		// ProductActivity.class);
		// intent.putExtra("item_id", id);
		// startActivityForResult(intent, 1);
		// }
		// });
		//
		// cursor.close();

		registerForContextMenu(listBooks);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Toast.makeText(this,
				"Request: " + requestCode + " Result: " + resultCode,
				Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.setHeaderTitle("Opções");
		menu.add("Compartilhar");
		menu.add("Excluir");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.menu_settings:
			intent = new Intent(MainActivity.this, PrefActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}

		return true;
	}

}
