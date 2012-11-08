package br.gov.serpro.edson.wishlist;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ProductActivity extends Activity {

	private ActionBar actionBar;

	private Button buttonSave;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);

		actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);

		Bundle b = getIntent().getExtras();
		if (b != null) {
			Long id = b.getLong("item_id");
			Toast.makeText(this, "A ID selecionada foi " + id,
					Toast.LENGTH_SHORT).show();
		}

		buttonSave = (Button) findViewById(R.id.button_save);
		buttonSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_product, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case android.R.id.home:
			intent = new Intent(ProductActivity.this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
			break;

		case R.id.menu_settings: 
			intent = new Intent(ProductActivity.this, PrefActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}

		return true;
	}

}
