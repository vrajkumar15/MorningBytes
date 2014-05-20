package com.example.android.expandingcells;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;

import android.util.Log;


public class CommonLib {

	private static final int MAXNUMQUOTES = 100;
	private static String[] quotes = new String[MAXNUMQUOTES];
	
	public static String dailyHoroscope(){
		JSONObject jObj = new JSONObject();
		return "Today you will rock Mr. Raj !!";

	}
	/*
	 * Gender?? ** should we consider gender? **
	 * 
	 * (nice temp & clear sky)
	 *  Happy: Pink, light blue, green, maybe purple
		  (cloudy) Bored: Light colors, black or gray, white
		
		(warm & clear) Hyper/ sporty: Team colors, orange, red, blue
		(extremes) Crazy: Yellow, bright orange, maybe neon
	
	 *
	 */
	
	public static String findZodiacSign (String month, String day) {
        int M = Integer.parseInt(month);
        int D = Integer.parseInt(day);
        if ((M == 12 && D >= 22 && D <= 31) || (M ==  1 && D >= 1 && D <= 19))
            return "Capricorn";
        else if ((M ==  1 && D >= 20 && D <= 31) || (M ==  2 && D >= 1 && D <= 17))
            return "Aquarius";
        else if ((M ==  2 && D >= 18 && D <= 29) || (M ==  3 && D >= 1 && D <= 19))
            return "Pisces";
        else if ((M ==  3 && D >= 20 && D <= 31) || (M ==  4 && D >= 1 && D <= 19))
            return "Aries";
        else if ((M ==  4 && D >= 20 && D <= 30) || (M ==  5 && D >= 1 && D <= 20))
            return "Taurus";
        else if ((M ==  5 && D >= 21 && D <= 31) || (M ==  6 && D >= 1 && D <= 20))
            return "Gemini";
        else if ((M ==  6 && D >= 21 && D <= 30) || (M ==  7 && D >= 1 && D <= 22))
            return "Cancer";
        else if ((M ==  7 && D >= 23 && D <= 31) || (M ==  8 && D >= 1 && D <= 22))
            return "Leo";
        else if ((M ==  8 && D >= 23 && D <= 31) || (M ==  9 && D >= 1 && D <= 22))
            return "Virgo";
        else if ((M ==  9 && D >= 23 && D <= 30) || (M == 10 && D >= 1 && D <= 22))
            return "Libra";
        else if ((M == 10 && D >= 23 && D <= 31) || (M == 11 && D >= 1 && D <= 21))
            return "Scorpio";
        else if ((M == 11 && D >= 22 && D <= 30) || (M == 12 && D >= 1 && D <= 21))
            return "Sagittarius";
        else
            return "Illegal date";

    }

	
	public static String colorToWear(int temperature, boolean isCloudy){
		
		String dressColor = "blue";
		
		if(temperature>60 && temperature<75){
			if(!isCloudy){
				String[] happyColors = {"pink", "light blue", "green", "purple"};
				int idx = new Random().nextInt(happyColors.length);
				dressColor = (happyColors[idx]);			
			}
			else{
				String[] boredColors = {"light color", "black", "grey", "white"};
				int idx = new Random().nextInt(boredColors.length);
				dressColor = (boredColors[idx]);
			}
		}
		
		return dressColor;
	}
	
	
	public static String quoteOfTheDay(){
		
		// For hack day, maintain an array of strings for quotes
		// For shipping the hack, move this to server and return a Json or something, make it light-weight
		quotes[0]="As you sow, so you reap";
		quotes[1]="It is never too late to be what you might have been";
		quotes[2]="What the mind can conceive, it can achieve";
		
		int idx = new Random().nextInt(3);
		return quotes[idx];
	}
	
	public static String raahuKaalam(int dayOfWeek){
		// hashmap with the standard times and raahu kalams
		// 1-7 SUNDAY to SATURDAY
		
		Map<Integer,String> mapDayToRaahuKaalam = new HashMap<Integer,String>();
		
		mapDayToRaahuKaalam.put(2, "7:30AM to 9AM");
		mapDayToRaahuKaalam.put(7, "9AM to 10:30AM");
		mapDayToRaahuKaalam.put(6, "10:30AM to 12PM");
		mapDayToRaahuKaalam.put(4, "12PM to 1:30PM");
		mapDayToRaahuKaalam.put(5, "1:30PM to 3PM");
		mapDayToRaahuKaalam.put(3, "3PM to 4:30PM");
		mapDayToRaahuKaalam.put(1, "4:30PM to 6PM");
		
		if(! mapDayToRaahuKaalam.containsKey(dayOfWeek)){
			return "Sorry! Invalid Day Detected";
		}else{
			Log.d("DEBUG", mapDayToRaahuKaalam.get(dayOfWeek));
			return mapDayToRaahuKaalam.get(dayOfWeek);	
		}
		
	}
}
