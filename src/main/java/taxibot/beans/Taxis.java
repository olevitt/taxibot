package taxibot.beans;

import com.google.gson.annotations.SerializedName;

public class Taxis {

	@SerializedName("data")
	private Taxi[] taxis;

	public Taxi[] getTaxis() {
		return taxis;
	}

	public void setTaxis(Taxi[] taxis) {
		this.taxis = taxis;
	}
	
	
}
