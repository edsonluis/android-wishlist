package br.gov.serpro.edson.wishlist;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button buttonNew;

	private ListView listBooks;

	private ActionBar actionBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(false);

		ArrayAdapter<String> actionAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1);
		actionAdapter.add("Categoria 1");
		actionAdapter.add("Categoria 2");
		actionAdapter.add("Categoria 3");
		actionAdapter.add("Categoria 4");

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
				startActivity(new Intent(MainActivity.this,
						ProductActivity.class));
			}
		});

		String[] columns = new String[] { "_id", "name", "author", "publishing" };

		MatrixCursor cursor = new MatrixCursor(columns);
		cursor.addRow(new String[] { "1", "Livro 1", "Jake", "Um" });
		cursor.addRow(new String[] { "2", "Livro 2", "John", "Dois" });
		cursor.addRow(new String[] { "3", "Livro 3", "Maria", "Três" });
		cursor.addRow(new String[] { "4", "Livro 4", "João", "Um" });

		BooksAdapter adapter = new BooksAdapter(this, cursor);
		adapter.notifyDataSetChanged();
		listBooks.setAdapter(adapter);
		listBooks.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(MainActivity.this,
						ProductActivity.class);
				intent.putExtra("item_id", id);
				startActivityForResult(intent, 1);
			}
		});
		
		cursor.close();

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

}
