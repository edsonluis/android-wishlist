package br.gov.serpro.edson.wishlist;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class BooksAdapter extends CursorAdapter {

	private Context context;
	private Cursor cursor;

	public BooksAdapter(Context context, Cursor cursor) {
		super(context, cursor, 0);
		this.context = context;
		this.cursor = cursor;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (!cursor.moveToPosition(position)) {
			throw new IllegalStateException("couldn't move cursor to position "
					+ position);
		}
		if (convertView == null) {
			convertView = newView(context, cursor, parent);
		}
		bindView(convertView, context, cursor);
		return convertView;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		Resources res = this.context.getResources();

		ViewHolder holder = (ViewHolder) view.getTag();
		
		if (holder == null) {
            holder = new ViewHolder();
            holder.textView1 = (TextView) view.findViewById(R.id.textView_product);
            holder.textView2 = (TextView) view.findViewById(R.id.textView_author);
            holder.textView3 = (TextView) view.findViewById(R.id.textView_publishing);
            holder.column1 = cursor.getColumnIndexOrThrow("name");
            holder.column2 = cursor.getColumnIndexOrThrow("author");
            holder.column3 = cursor.getColumnIndexOrThrow("publishing");
            view.setTag(holder);
        }

        holder.textView1.setText(cursor.getString(holder.column1));
        holder.textView2.setText(res.getString(R.string.textview_author) + ": "
				+ cursor.getString(holder.column2));
        holder.textView3.setText(res.getString(R.string.textview_publishing) + ": "
				+ cursor.getString(holder.column3));

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return LayoutInflater.from(context).inflate(R.layout.listview_books,
				null);
	}
	
	static class ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
        int column1; 
        int column2;
        int column3; 
    }

}
