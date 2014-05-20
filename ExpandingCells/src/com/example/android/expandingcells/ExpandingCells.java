/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.expandingcells;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * This activity creates a listview whose items can be clicked to expand and show
 * additional content.
 *
 * In this specific demo, each item in a listview displays an image and a corresponding
 * title. These two items are centered in the default (collapsed) state of the listview's
 * item. When the item is clicked, it expands to display text of some varying length.
 * The item persists in this expanded state (even if the user scrolls away and then scrolls
 * back to the same location) until it is clicked again, at which point the cell collapses
 * back to its default state.
 */
public class ExpandingCells extends Activity {

    private final int CELL_DEFAULT_HEIGHT = 150;
    private final int NUM_OF_CELLS = 4;
    public ArrayList<News> NewsList;
   // public ArrayList<Bytes> Byte = new ArrayList<Bytes>();
    private LocationManager locationManager;
	private String provider;
	private MyLocationListener mylistener;
	private Criteria criteria;
    public String Horo;
    private String Horo_Image;
	public String horoscopeText="hello";
	public String Color;
	public String C_Image;
	public String Raghukalam;
	public String Raghu_Image;
	
	 CustomArrayAdapter adapter;

    private ExpandingListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
		// Get the location manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// Define the criteria how to select the location provider
		criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);	//default


		criteria.setCostAllowed(false); 
		// get the best provider depending on the criteria
		provider = locationManager.getBestProvider(criteria, false);

		// the last known location of this provider
		Location location = locationManager.getLastKnownLocation(provider);

		mylistener = new MyLocationListener();

		if (location != null) {
			mylistener.onLocationChanged(location);
		} else {
			// leads to the settings because there is no last known location
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
		}
		// location updates: at least 1 meter and 200millsecs change
		//NETWORK_PROVIDER
		locationManager.requestLocationUpdates(provider, 200, 1, mylistener);
		
        this.NewsList = new ArrayList<News>(4);
		for ( int i=0; i<4; i++) {
			NewsList.add(new News());
			} 
		
		//Methods to populate the Object
		getColor();
		getHoroscope();
		getRaghukalam();
		//getWeather();
		
        //Log.d("DEBUG", Byte.getBody());
        Log.d("DEBUG", "Print");
       // Toast.makeText(this, Byte.size(), Toast.LENGTH_LONG).show();
     
        ExpandableListItem[] values = new ExpandableListItem[] {
        	    new ExpandableListItem("Color", R.drawable.mb_color, CELL_DEFAULT_HEIGHT,
                		NewsList.get(0).getBody()),
                new ExpandableListItem("Horoscope", R.drawable.mb_horoscope, CELL_DEFAULT_HEIGHT,
                		NewsList.get(1).getBody()),
                new ExpandableListItem("Raghukalam", R.drawable.mb_raghukalam, CELL_DEFAULT_HEIGHT,
                        NewsList.get(2).getBody()),
                new ExpandableListItem("Weather", R.drawable.mb_weather, CELL_DEFAULT_HEIGHT,
                        NewsList.get(3).getBody()),
        };

        List<ExpandableListItem> mData = new ArrayList<ExpandableListItem>();

        for (int i = 0; i < NUM_OF_CELLS; i++) {
            ExpandableListItem obj = values[i % values.length];
            mData.add(new ExpandableListItem(obj.getTitle(), obj.getImgResource(),
                    obj.getCollapsedHeight(), obj.getText()));
        }

       // CustomArrayAdapter adapter = new CustomArrayAdapter(this, R.layout.list_view_item, mData);
        adapter = new CustomArrayAdapter(this, R.layout.list_view_item, mData);

        mListView = (ExpandingListView)findViewById(R.id.main_list_view);
        mListView.setAdapter(adapter);
        mListView.setDivider(null);
    }
    
    public void getColor(){
    	try{
            Log.d("DEBUG", "Color_Catchpre");
            Color = CommonLib.colorToWear(64, false);
            C_Image = "drawable://" + R.drawable.mb_color;
            NewsList.get(0).setNews("Test", "Suggested Color", Color);
    		//itemList.add(new Bytes("Test", "Suggested Color", Color));
    		Log.d("DEBUG", "Color_Catchpost");
            } 
            catch (Exception e) {
    			e.printStackTrace();
    			System.out.println(e);
    			Log.d("DEBUG", "Catch Block reached");
            }
    }
    
    public void getHoroscope(){
    	
    	AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://pipes.yahoo.com/pipes/pipe.run?_id=_omfgXdL3BGGadhGdrq02Q&_render=json&sign=Virgo&url=http%3A%2F%2Fwww.astrology.com%2Fhoroscopes%2Fdaily-horoscope.rss", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {	
				JSONArray horoscopeJsonResults = null;
				try {				
					horoscopeJsonResults = response.getJSONObject("value").getJSONArray("items");				

					// Pass the index value based on what sunsign, here, 0->Aries, 1->Taurus ... etc				
					String horoscopeFullString = horoscopeJsonResults.getJSONObject(0).getString("description").toString();

					Document doc = Jsoup.parse(horoscopeFullString);
					Element p= doc.select("p").first();
					horoscopeText = p.text();
					Horo_Image = "drawable://" + R.drawable.mb_horoscope;
                    Horo = "Daily Horoscope for " + CommonLib.findZodiacSign("9", "6") + ": \n" + horoscopeText;
                    Log.d("DEBUG",Horo);
                    Log.d("DEBUG", "Horo");
                    NewsList.get(1).setNews(Horo_Image, "Horoscope", Horo);
                    adapter.notifyDataSetChanged();
                    Log.d("DEBUG", "Horo1");
                    adapter.notifyDataSetChanged();
                    //itemList.add(new Bytes(Horo_Image,"Horoscope",  Horo));
					// Replace this with birth date
					//todoAdapter.add("Daily Horoscope for " + CommonLib.findZodiacSign("12", "6") + ": \n" + horoscopeText);
                    
				} catch (JSONException e) {
					e.printStackTrace();
					Log.d("DEBUG", "pipes");
				}
			}
		});
    }

    public void getRaghukalam(){
		// Raahu Kaalam for today
		Calendar c = Calendar.getInstance(); 
		Integer dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		Log.d("DEBUG", "Today is" + dayOfWeek.toString());
		Raghukalam = "Raahu Kaalam: " + CommonLib.raahuKaalam(dayOfWeek);
		Raghu_Image = "drawable://" + R.drawable.mb_raghukalam;
		//todoAdapter.add("Raahu Kaalam: " + CommonLib.raahuKaalam(dayOfWeek));
		//itemList.add(new Bytes(Raghu_Image,"Raghu Kalam", Raghukalam));
		NewsList.get(2).setNews(Raghu_Image, "Raghukalam", Raghukalam);

    }
    

	private class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// Initialize the location fields

			AsyncHttpClient client_w = new AsyncHttpClient();

			client_w.get("http://api.openweathermap.org/data/2.5/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&APPID=055a946d7193b12bb5ad491595be1124", new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject response) {
					try{
						String weatherResult = response.getJSONObject("main").getString("temp").toString();
						String cloudResult = response.getJSONObject("clouds").getString("all").toString();
						String cdescResult = response.getJSONArray("weather").getJSONObject(0).getString("description");
						Log.d("DEBUG", weatherResult);
						Log.d("DEBUG", cloudResult);
						Log.d("DEBUG", cdescResult);
						NewsList.get(3).setNews("Weather", "Weather", "weather temp" );

					} catch (JSONException e) {
						Log.d("DEBUG", "weather2");
						e.printStackTrace();
					}
				}

			});
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			Toast.makeText(ExpandingCells.this, provider + "'s status changed to "+status +"!",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onProviderEnabled(String provider) {
			Toast.makeText(ExpandingCells.this, "Provider " + provider + " enabled!",
					Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onProviderDisabled(String provider) {
			Toast.makeText(ExpandingCells.this, "Provider " + provider + " disabled!",
					Toast.LENGTH_SHORT).show();
		}

	}
}