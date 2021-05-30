package com.example.a2;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;

public class SearchGoogleAPI {

    private static final String API_KEY = "AIzaSyAVtvedU4Lb157l7-OfhOgpvv6H71SeuRI";
    private static final String SEARCH_ID_cx = "002722581420390946418:oofc338jq5w";

    public static String search(String keyword, String[] params, String[] values) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter = "";
        if (params != null && values != null) {
            for (int i = 0; i < params.length; i++) {
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }
        try {
            url = new URL("https://www.googleapis.com/customsearch/v1?key=" + API_KEY + "&cx=" +
                    SEARCH_ID_cx + "&q=" + keyword + query_parameter);

            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return textResult;
    }

    //26.
    public static List<HashMap<String,String>> getSnippet(String result) {
        String snippet = null;
        String moviename = "";
        String year = "";
        String imgurl = "";
        String movienameoriginal = "";
        List<HashMap<String,String>> moviesearchList =new ArrayList<HashMap<String,String>>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if (jsonArray != null && jsonArray.length() > 0) {
                for(int i=0 ;i<jsonArray.length();i++) {
                    movienameoriginal = jsonArray.getJSONObject(i).getString("title");
                    //https://stackoverflow.com/questions/18590901/check-if-a-string-contains-numbers-java
                    //\d+ means a digit from 1 time to infinite.
                    if(movienameoriginal.matches(".*(\\d.)*")) {
                        snippet = jsonArray.getJSONObject(i).getString("snippet");
                        HashMap<String,String> moviesearch = new HashMap<String,String>();
                        String[] movienametitlepart = movienameoriginal.split(Pattern.quote("("));
                        moviename = movienametitlepart[0];
                        Log.d(TAG,movienametitlepart[0]);
                        String[] movienameyearpart = movienametitlepart[1].split(Pattern.quote(")"));
                        year = movienameyearpart[0];
                        imgurl = jsonArray.getJSONObject(i).getJSONObject("pagemap").getJSONArray("cse_image").getJSONObject(0).getString("src");
                        moviesearch.put("snippet",snippet);
                        moviesearch.put("name",moviename);
                        moviesearch.put("year",year);
                        moviesearch.put("imgurl",imgurl);
                        moviesearchList.add(moviesearch);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            snippet = "NO INFO FOUND";
        }
        return moviesearchList;
    }

    public static String getimageurl(String result) {
        String imgurl = "";
        String movienameoriginal = "";

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if (jsonArray != null && jsonArray.length() > 0) {
                for(int i=0 ;i<jsonArray.length();i++) {
                    movienameoriginal = jsonArray.getJSONObject(i).getString("title");
                    //https://stackoverflow.com/questions/18590901/check-if-a-string-contains-numbers-java
                    //\d+ means a digit from 1 time to infinite.
                    if(movienameoriginal.matches(".*(\\d.)*")) {
                        imgurl = jsonArray.getJSONObject(i).getJSONObject("pagemap").getJSONArray("cse_image").getJSONObject(0).getString("src");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgurl;
    }

    public static String searchforimg(String keyword, String[] params, String[] values) {
        String result = search(keyword, params, values);
        String url = getimageurl(result);
        return url;

    }

}


