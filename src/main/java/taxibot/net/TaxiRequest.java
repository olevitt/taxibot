package taxibot.net;

import java.io.IOException;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import taxibot.beans.Taxi;
import taxibot.beans.Taxis;


public class TaxiRequest {

	private final OkHttpClient client = new OkHttpClient();

	public Taxi getTaxi() throws Exception {
		Request request = new Request.Builder()
				.url("https://dev.api.taxi/taxis/?lon=2.278421&lat=48.877486")
				.header("X-API-KEY", "35ee6663-c07a-4d37-9957-5c3611126175")
				.header("X-VERSION", "2")
				.header("Accept", "application/json")
				.build();

		Response response = client.newCall(request).execute();
		if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
		
		Taxis lesTaxis = new Gson().fromJson(response.body().string(), Taxis.class);
		Taxi[] taxis = lesTaxis.getTaxis();
		System.out.println("Found "+taxis.length+" taxis");
		if (taxis.length > 0) {
			return taxis[0];
		}
		else {
			return null;
		}
	}
}
