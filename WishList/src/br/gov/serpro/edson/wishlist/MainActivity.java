package br.gov.serpro.edson.wishlist;

import android.app.Activity;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private Button buttonNew;
	
	private ListView listBooks;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        buttonNew = (Button) findViewById(R.id.button_new);
        listBooks = (ListView) findViewById(R.id.listView_books);
        
        buttonNew.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, ProductActivity.class));
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
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private void recarregar() {
    	
    }
}
