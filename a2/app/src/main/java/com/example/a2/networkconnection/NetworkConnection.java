package com.example.a2.networkconnection;

import android.os.Build;
import android.util.Log;


import androidx.annotation.RequiresApi;

import com.example.a2.post.Credentials;
import com.example.a2.post.Memoir;
import com.example.a2.post.Person;
import com.example.a2.post.Top5movie;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

//this class will be responsible for invoking my webservice methods.
public class NetworkConnection {
    private OkHttpClient client = null;

    private String results;

    public static final MediaType JSON =
            MediaType.parse("application/json; charset=utf-8");

    public NetworkConnection() {
        client = new OkHttpClient();

    }

    private static final String BASE_URL = "http://172.16.11.150:8080/29217814-A1/webresources/";

    public String getAllCredentials() {
        final String methodPath = "restm3.credentials";
        Request.Builder builder = new Request.Builder();
        //DEBUG here
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            //debug here
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public String findByUsername(String username) {
        final String methodPath = "restm3.credentials/findByUsername/" + username;
        Request.Builder builder = new Request.Builder();
        //DEBUG here
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            //debug here
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    //sign in verify if there is a user has such username
    public boolean checkUsername(String results) {
        boolean check = false;
        if (!results.isEmpty())
            check = true;
        return check;
    }

    //sign in
    public boolean signIn(String username) {
        boolean check = false;
        check = checkUsername(findByUsername(username));
        return check;
    }

    public String findByPasswordhash(String passwordhash) {
        final String methodPath = "restm3.credentials/findByPasswordhash/" + passwordhash;
        Request.Builder builder = new Request.Builder();
        //DEBUG here
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            //debug here
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public String findByUsernameAndPassword(String username, String passwordhash) {
        final String methodPath = "restm3.credentials/findByUsernameAndPassword/" + username + "/" + passwordhash;
        Request.Builder builder = new Request.Builder();
        //DEBUG here
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            //debug here
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public String deleteCredentials(int credentialsId) {
        final String methodPath = "restm3.credentials/" + credentialsId;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.delete().build();
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    //26.
    //Date:https://www.javatpoint.com/java-string-to-date

    public String addCredentials(String[] details) {
        int intId = Integer.valueOf(countCredentialsId()) + 1;

        Credentials credentials = new Credentials(intId, details[0], details[1], details[2]);
        //credentials.setCredentials(Integer.parseInt(details[3]));
        Gson gson = new Gson();
        String credentialsJson = gson.toJson(credentials);
        String strResponse = "";
//this is for testing, you can check how the json looks like in Logcat
        Log.i("json ", credentialsJson);
        final String methodPath = "restm3.credentials/";
        RequestBody body = RequestBody.create(credentialsJson, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + methodPath).post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            //https://stackoverflow.com/questions/28300359/cant-get-okhttps-response-body-tostring-to-return-a-string
            strResponse = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResponse;
    }

    public String countCredentialsId() {
        final String methodPath = "restm3.credentials/countId";
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();

            Log.d(TAG, "Result: " + results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public String addPerson(String[] details) {
        String[] information = {details[8], details[9], details[10]};
        addCredentials(information);
        int intId = Integer.valueOf(countPersonId()) + 1;

        Person person = new Person(intId, details[0], details[1], details[2], details[3], Integer.parseInt(details[4]),
                details[5], details[7], details[6]);
        /*
        String sdate = getSignupdateById(1);
        String[] dateArray = sdate.split(" ");

        if (dateArray[5].length() == 4) {
            LocalDate date = LocalDate.parse(sdate, DateTimeFormatter.ofPattern("eee MMM dd hh:mm:ss zzzz yyyy"));
            String dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
            sdate = date.atStartOfDay().format(DateTimeFormatter.ofPattern(dateFormat)).toString();
        }
        else if (dateArray[5].length() == 4) {
            LocalDate date = LocalDate.parse(sdate, DateTimeFormatter.ofPattern("eee MMM dd hh:mm:ss zzz yyyy"));
            String dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
            sdate = date.atStartOfDay().format(DateTimeFormatter.ofPattern(dateFormat));
        }


        //https://stackoverflow.com/questions/2268969/convert-month-string-to-integer-in-java/2269439
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM")
                .withLocale(Locale.ENGLISH);
        TemporalAccessor accessor = parser.parse(dateArray[1]);
        sdate = String.valueOf(accessor.get(ChronoField.MONTH_OF_YEAR));


        ZoneId zone = ZoneId.of("AET");
        ZoneOffset zoneOffset = zone.getRules().getOffset(LocalDateTime.now());
        OffsetDateTime credentialssudate = OffsetDateTime.of(Integer.parseInt(dateArray[5]),Integer.parseInt(sdate), Integer.parseInt(dateArray[2]),00,00,00,00,zoneOffset);
        */
        int intcId = Integer.valueOf(countCredentialsId());
        person.setCredentialsid(intcId, details[8], details[9], details[10]);
        Gson gson = new Gson();
        String personJson = gson.toJson(person);
        String strResponse = "";
//this is for testing, you can check how the json looks like in Logcat
        Log.i("json ", personJson);
        final String methodPath = "restm3.person/";
        RequestBody body = RequestBody.create(personJson, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + methodPath).post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            strResponse = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResponse;
    }


    public String countPersonId() {
        final String methodPath = "restm3.person/countId";
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();

            Log.d(TAG, "Result: " + results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public String getUsernameById(int id) {
        final String methodPath = "restm3.person/getUsernameById/" + id;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();

            Log.d(TAG, "Result: " + results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public String getCredentialsIdByUsername(String email) {
        final String methodPath = "restm3.credentials/getCredentialsIdByUsername/" + email;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();

            Log.d(TAG, "Result: " + results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public String getHashById(int id) {
        final String methodPath = "restm3.person/getHashById/" + id;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();

            Log.d(TAG, "Result: " + results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public String getSignupdateById(int id) {
        final String methodPath = "restm3.person/getSignupdateById/" + id;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();

            Log.d(TAG, "Result: " + results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    /*
    //https://stackoverflow.com/questions/17290916/how-do-i-change-date-time-format-in-android
    public String formatDate(String dateToFormat, String inputFormat, String outputFormat) {
        try {


            String convertedDate = new SimpleDateFormat(outputFormat)
                    .format(new SimpleDateFormat(inputFormat)
                            .parse(dateToFormat));

            //Update Date
            return convertedDate;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

     */
    public String getPersonFnameByCredentialsId(String id) {
        final String methodPath = "restm3.person/getPersonFnameByCredentialsId2/" + id;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response responsefname = client.newCall(request).execute();
            results = responsefname.body().string();

            Log.d(TAG, "Result: " + results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    //homescreen-top5movie 2020

    public ArrayList<Top5movie> get2020TopWatchedMovie(String id) {
        final String methodPath = "restm3.memoir/get2020TopWatchedMovie/" + id;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        ArrayList<Top5movie> movie = new ArrayList<Top5movie>();
        try {
            Response responsefname = client.newCall(request).execute();
            results = responsefname.body().string();
            JsonParser parser = new JsonParser();
            JsonElement json = parser.parse(results);
            JsonArray movielist = json.getAsJsonArray();


            for (JsonElement m : movielist) {
                Top5movie top5movie = new Top5movie();
                JsonObject jsonObject = m.getAsJsonObject();
                String moviename = jsonObject.get("MovieName").getAsString();
                top5movie.setMovieName(moviename);
                String date = jsonObject.get("ReleaseYear").getAsString();
                top5movie.setReleaseYear(date);
                String score = jsonObject.get("RatingScore").getAsString();
                top5movie.setRatingScore(score);
                movie.add(top5movie);
            }

            Log.d(TAG, "Result: " + results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movie;
    }

    public ArrayList<Memoir> getMoviememoir(String id) {
        final String methodPath = "restm3.memoir/getMoviememoir/" + id;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        ArrayList<Memoir> movie = new ArrayList<Memoir>();
        try {
            Response responsefname = client.newCall(request).execute();
            results = responsefname.body().string();
            JsonParser parser = new JsonParser();
            JsonElement json = parser.parse(results);
            JsonArray memoirlist = json.getAsJsonArray();


            for (JsonElement m : memoirlist) {
                //{"MOVIE", "RDATE", "IMG", "WDATE", "POSTCODE", "COMMENT", "STARIMG",};

                JsonObject jsonObject = m.getAsJsonObject();
                String moviename = jsonObject.get("MOVIE").getAsString();

                String rdate = jsonObject.get("RDATE").getAsString();

                String wdate = jsonObject.get("WDATE").getAsString();

                String postcode = jsonObject.get("POSTCODE").getAsString();

                String comment = jsonObject.get("COMMENT").getAsString();

                String starimg = jsonObject.get("RatingScore").getAsString();

                //String movieName, String releaseYear, String POSTCODE, String COMMENT, String STARIMG, String WDATE
                Memoir memoir = new Memoir(moviename, rdate, postcode, comment, starimg, wdate);
                movie.add(memoir);
            }

            Log.d(TAG, "Result: " + results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movie;
    }

    public List<PieEntry> getMovieCount(String id) {
        String textResult = "";
        List<PieEntry> pieList = new ArrayList<>();
        final String methodPath = "restm3.memoir/getMovieCount/" + id;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response responsefname = client.newCall(request).execute();
            results = responsefname.body().string();
            JsonParser parser = new JsonParser();
            JsonElement json = parser.parse(results);
            JsonArray memoirlist = json.getAsJsonArray();
            // for () {

            //  }
        } catch (
                Exception e) {
            e.printStackTrace();
            try {
                JSONArray jsonArray = new JSONArray(textResult);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    PieEntry pie = new PieEntry(Integer.parseInt(jsonObject.getString("totalnumber")), jsonObject.getString("surburb"));
                    pieList.add(pie);
                }
            } catch (JSONException j) {
                e.printStackTrace();
            }
        }
        return pieList;
    }

    public String MovieCountMonth(String year) {


        List<String> xValue = new ArrayList<>();
        List<Integer> yValue = new ArrayList<>();
        List<IBarDataSet> dataSets = new ArrayList<>();
        LinkedHashMap<String, List<Integer>> chartDataMap = new LinkedHashMap<>();
        String textResult = "";
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "restm3.memoir/MovieCountMonth/";
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response responsefname = client.newCall(request).execute();
            results = responsefname.body().string();
            JsonParser parser = new JsonParser();
            JsonElement json = parser.parse(results);
            JsonArray memoirlist = json.getAsJsonArray();
            // for () {

            //  }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        try {
            JSONArray jsonArray = new JSONArray(textResult);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                xValue.add(jsonObject.getString("Month"));
                yValue.add(Integer.parseInt(jsonObject.getString("totalnumber")));
            }
        } catch (
                JSONException j) {
            j.printStackTrace();
        }

        return textResult;
    }

    private void initBarDataSet(BarDataSet barDataSet, int color) {
        barDataSet.setColor(color);
        barDataSet.setFormLineWidth(1f);
        barDataSet.setFormSize(15.f);
        barDataSet.setDrawValues(false);
    }

}
