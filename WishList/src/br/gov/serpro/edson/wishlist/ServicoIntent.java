package br.gov.serpro.edson.wishlist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;



public class ServicoIntent extends IntentService {

	private static final String TAG = ServicoIntent.class.getSimpleName();
	
	private String productName;
	
	private double priceMin;
	
	private double priceMax;
	
//	final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	
	public ServicoIntent() {
		super("Serviço");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "Verificando valor do produto!");

		String strJson = HttpUtil.doGet("http://sandbox.buscape.com/service/findProductList/72577349624e6c685068513d/?keyword=Samsung%20Galaxy%20Nexus&format=json");
		
		try {
			JSONObject json = new JSONObject(strJson);
			JSONArray array = json.getJSONArray("product");
			for(int index = 0; index < array.length(); index++) {
				JSONObject jsonObject = array.getJSONObject(index);
				JSONObject jsonProduct = jsonObject.getJSONObject("product");
				productName = jsonProduct.getString("productname");
				priceMin = Double.parseDouble(jsonProduct.getString("pricemin"));
				priceMax = Double.parseDouble(jsonProduct.getString("pricemax"));
				
				Log.d(TAG, "Produto: " + productName + " Min: " + priceMin + " Max: " + priceMax);

//				NotificationCompat.Builder builder = new Builder(this);
//				builder.setWhen(System.currentTimeMillis());
//				builder.setSmallIcon(R.drawable.ic_launcher);
//				builder.setTicker("O produto " + productName + " mudou de preço!");
//				builder.setContentTitle("O produto " + productName + " mudou de preço!");
//				builder.setContentText("Preço min: " + priceMin + " Preço max: " + priceMax);
//				
//				Intent i = new Intent(this, ProductActivity.class);
//
//				PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
//				builder.setContentIntent(pendingIntent);
//				
//				Notification not = builder.build();
//				not.flags = Notification.FLAG_AUTO_CANCEL;
//				
//				notificationManager.notify(1, not);
			}
		} catch (JSONException e) {
			stopSelf();
		}
	}

}
