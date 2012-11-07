package br.gov.serpro.edson.wishlist;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

public class WishListApplication extends Application {

	private DBOpenHelper dbOpenHelper;
	private SQLiteDatabase database;
	
	@Override
	public void onCreate() {
		super.onCreate();
		dbOpenHelper = new DBOpenHelper(this, "meudb", null, 1);
		database = dbOpenHelper.getWritableDatabase();
	}
	
	public SQLiteDatabase getDatabase() {
		return database;
	}
	
}
