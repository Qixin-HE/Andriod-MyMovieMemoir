package com.example.a2.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.a2.HomescreenActivity;
import com.example.a2.Parcelable.Person;
import com.example.a2.R;
import com.example.a2.SearchGoogleAPI;
import com.example.a2.networkconnection.NetworkConnection;
import com.example.a2.post.Memoir;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MovieMemoirFragment extends Fragment {
    private TextView tvTitle;
    NetworkConnection networkConnection = new NetworkConnection();
    //List-view
    List<HashMap<String, String>> movieListArray;
    SimpleAdapter myListAdapter;
    ListView moviememoirlistview;
    HashMap<String, String> map = new HashMap<String, String>();
    String[] colHead = new String[]{"MOVIE", "RDATE", "IMG", "WDATE", "POSTCODE", "COMMENT", "STARIMG",};
    int[] dataCell = new int[]{R.id.tvMname, R.id.tvReleaseyear, R.id.ivMoviepic, R.id.tvWatchdate, R.id.tvCinemapostcode, R.id.tvComment, R.id.ivStar};

    int credentialsid;
    String imgurl;
    ArrayList<String> keyword;
    ArrayList<Memoir> movielistwithoutimg;

    int counter = 0;

    public MovieMemoirFragment() {
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Memoir> movielistwithoutimg = new ArrayList<>();
        movieListArray = new ArrayList<HashMap<String, String>>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.moviememoir_fragment, container, false);
        tvTitle = view.findViewById(R.id.tvTitle);
        //list-view
        moviememoirlistview = view.findViewById(R.id.lvMoviememoir);
        HomescreenActivity homescreenactivity = (HomescreenActivity) getActivity();
        Bundle bundle = new Bundle();
        bundle = homescreenactivity.getBundle();
        if (bundle != null) {
            Person singinperson = bundle.getParcelable("person1");
            credentialsid = singinperson.getCredentialsid();
        }
        keyword = new ArrayList<String>();

        Getmoviememoir getmoviememoir = new Getmoviememoir();
        getmoviememoir.execute(String.valueOf(credentialsid));


        return view;
        //above attaches the Observer object to the LiveData object using the observe() method is below.

    }

    private class Getmoviememoir extends AsyncTask<String, Void, ArrayList<Memoir>> {
        int counter2 = 0;

        @Override
        protected ArrayList<Memoir> doInBackground(String... params) {

            return networkConnection.getMoviememoir(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Memoir> movielist) {
            movielistwithoutimg = movielist;
            for (Memoir m : movielistwithoutimg) {


                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        keyword.add(movielistwithoutimg.get(counter2).getMovieName());
                        counter2++;
                        int counter3 = counter2 - 1;
                        return SearchGoogleAPI.search(keyword.get(counter3), new String[]{"num"}, new String[]{"3"});
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        String url = SearchGoogleAPI.getimageurl(result);
                        movielistwithoutimg.get(counter).setIMG(url);
                        String report = movielistwithoutimg.get(counter).getIMG() + " NAME " + movielistwithoutimg.get(counter).getMovieName();
                        Log.d(TAG, report);
                        counter++;
                    }
                }.execute();
            }
            SetMovieListArray setMovieListArray = new SetMovieListArray();
            setMovieListArray.execute();
        }//private class

    }

    private class SetMovieListArray extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            for (Memoir m : movielistwithoutimg) {
                //{"MOVIE", "RDATE", "IMG", "WDATE", "POSTCODE", "COMMENT", "STARIMG",};
                String movie = "";
                String rdate = "";
                String wdate = "";
                String postcode = "";
                String comment = "";
                String staring = "";
                String imgurl = "";

                map = new HashMap<String, String>();
                movie = m.getMovieName();
                rdate = m.getReleaseYear();
                wdate = m.getWDATE();
                postcode = m.getPOSTCODE();
                comment = m.getCOMMENT();
                staring = m.getSTARIMG();
                imgurl = m.getIMG();

                map.put("MOVIE", movie);
                map.put("RDATE", rdate);
                map.put("WDATE", wdate);
                map.put("POSTCODE", postcode);
                map.put("COMMENT", comment);
                map.put("STARIMG", staring);
                map.put("IMG", imgurl);
                movieListArray.add(map);
            }
            myListAdapter = new SimpleAdapter(getActivity(), movieListArray, R.layout.moviememoir_list_view, colHead, dataCell);
            moviememoirlistview.setAdapter((myListAdapter));

            //https://stackoverflow.com/questions/30314314/image-from-url-in-listview-using-simpleadapter

            for (int i = 0; i < myListAdapter.getCount(); i++) {
                HashMap<String, Object> hm = (HashMap<String, Object>) myListAdapter.getItem(i);
                String imgUrl = (String) hm.get("IMG");

                ImageLoaderTask imageLoaderTask = new ImageLoaderTask();

                HashMap<String, Object> hmDownload = new HashMap<String, Object>();
                hmDownload.put("flag_path", imgUrl);
                hmDownload.put("position", i);

                // Starting ImageLoaderTask to download and populate image in the listview
                imageLoaderTask.execute(hmDownload);

            }

        }


    }


    private class ImageLoaderTask extends AsyncTask<HashMap<String, Object>, Void, HashMap<String, Object>> {

        @Override
        protected HashMap<String, Object> doInBackground(HashMap<String, Object>... hm) {


            InputStream iStream = null;
            String imgUrl = (String) hm[0].get("flag_path");
            int position = (Integer) hm[0].get("position");

            URL url;
            try {

                url = new URL(imgUrl);

                // Creating an http connection to communicate with url
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                // Connecting to url
                urlConnection.connect();

                // Reading data from url
                iStream = urlConnection.getInputStream();

                // Getting Caching directory
                File cacheDirectory = getActivity().getBaseContext().getCacheDir();

                // Temporary file to store the downloaded image
                File tmpFile = new File(cacheDirectory.getPath() + "/wpta_" + position + ".png");

                // The FileOutputStream to the temporary file
                FileOutputStream fOutStream = new FileOutputStream(tmpFile);

                // Creating a bitmap from the downloaded inputstream
                Bitmap b = BitmapFactory.decodeStream(iStream);

                // Writing the bitmap to the temporary file as png or jpeg file
                b.compress(Bitmap.CompressFormat.JPEG, 10, fOutStream);

                // Flush the FileOutputStream
                fOutStream.flush();

                //Close the FileOutputStream
                fOutStream.close();

                // Create a hashmap object to store image path and its position in the listview
                HashMap<String, Object> hmBitmap = new HashMap<String, Object>();

                // Storing the path to the temporary image file
                hmBitmap.put("flag", tmpFile.getPath());

                // Storing the position of the image in the listview
                hmBitmap.put("position", position);


                // Returning the HashMap object containing the image path and position
                return hmBitmap;


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            // Getting the path to the downloaded image
            try {
                String path = (String) result.get("flag");

                // Getting the position of the downloaded image
                int position = (Integer) result.get("position");

                // Getting adapter of the listview
                SimpleAdapter adapter = (SimpleAdapter) moviememoirlistview.getAdapter();

                // Getting the hashmap object at the specified position of the listview
                HashMap<String, Object> hm = (HashMap<String, Object>) adapter.getItem(position);

                // Overwriting the existing path in the adapter
                hm.put("IMG", path);


                // Noticing listview about the dataset changes
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.d(TAG, "NO IMAGE URL");
            }
        }

    }
}




