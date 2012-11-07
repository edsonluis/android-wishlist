package br.gov.serpro.edson.wishlist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE livro (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, author TEXT, publishing TEXT)");
		db.execSQL("CREATE TABLE categoria (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
