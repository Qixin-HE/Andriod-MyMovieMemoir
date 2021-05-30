package com.example.a2.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.a2.R;
import com.example.a2.SearchGoogleAPI;
import com.example.a2.networkconnection.NetworkConnection;
import com.example.a2.post.Top5movie;
import com.example.a2.viewmodel.SharedViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MovieSearchFragment extends Fragment {
    private EditText etMessage;
    private Button button;
    //
    private SharedViewModel model;

    private EditText etSearch;
    private TextView tvtest;

    NetworkConnection networkConnection = null;

    //list-view

    List<HashMap<String, String>> movieListArray = null;
    List<HashMap<String, String>> movieSearchArray = null;
    SimpleAdapter myListAdapter;
    ListView movieList;
    HashMap<String, String> map = new HashMap<String, String>();
    String[] colHEAD = new String[]{"NAME", "YEAR", "IMG"};
    int[] dataCell = new int[]{R.id.tvMoviename, R.id.tvReleaseyear, R.id.ivMovie};

    FragmentManager fragmentManager=null;

    List<Top5movie> searsharraylist;


    public MovieSearchFragment() {
// Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieListArray = new ArrayList<HashMap<String, String>>();

        networkConnection = new NetworkConnection();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.moviesearch_fragment, container, false);
//update the value stored in a LiveData object
        // model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        fragmentManager = getActivity().getSupportFragmentManager();
        etSearch = view.findViewById(R.id.etMoviesearch);

        //list-view
        movieList = view.findViewById(R.id.lvMoviesearch);

        movieSearchArray = new ArrayList<HashMap<String, String>>();

        Button btSearch = view.findViewById(R.id.btSearch);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieSearchArray.clear();
                movieListArray.clear();

                final String keyword = etSearch.getText().toString(); //create an anonymous AsyncTask

                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        return SearchGoogleAPI.search(keyword, new String[]{"num"},
                                new String[]{"3"});
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        searsharraylist = new ArrayList<Top5movie>();
                        movieListArray = SearchGoogleAPI.getSnippet(result);
                        String snippet = "";
                        String title = "";
                        String releaseyear = "";
                        String imgurl = "";

                        for (HashMap<String, String> moviesearch : movieListArray) {
                            map = new HashMap<String, String>();
                            title = moviesearch.get("name");
                            imgurl = moviesearch.get("imgurl");
                            snippet = moviesearch.get("snippet");
                            releaseyear = moviesearch.get("year");
                            //"NAME","YEAR","IMG"
                            map.put("NAME", title);
                            map.put("YEAR", releaseyear);
                            map.put("IMG", imgurl);
                            //map.put("SNIPPET", snippet);
                            movieSearchArray.add(map);
                            /*
                            Top5movie m = new Top5movie();
                            m.setMovieName(title);
                            m.setReleaseYear(releaseyear);
                            m.setRatingScore(imgurl);
                            m.setSnippet(snippet);
                            searsharraylist.add(m);

                             */

                        }
                        myListAdapter = new SimpleAdapter(getActivity(), movieSearchArray, R.layout.moviesearch_list_view, colHEAD, dataCell);
                        movieList.setAdapter((myListAdapter));

                        //https://stackoverflow.com/questions/30314314/image-from-url-in-listview-using-simpleadapter

                        for(int i=0;i<myListAdapter.getCount();i++) {
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
                }.execute();

            }
        });

        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle =new Bundle();
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                Fragment movieViewFragment =new MovieViewFragment();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(movieListArray.get(position).get("name"));
                stringBuffer.append("\n\n");

                stringBuffer.append(movieListArray.get(position).get("year"));
                stringBuffer.append("\n\n");

                stringBuffer.append(movieListArray.get(position).get("snippet"));
                stringBuffer.append("\n\n");

                stringBuffer.append(movieListArray.get(position).get("imgurl"));
                stringBuffer.append("\n\n");

                String moviedetail = stringBuffer.toString();
                bundle.putString("detail",moviedetail);
                movieViewFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content_frame,movieViewFragment).addToBackStack(null).commit();
            }
        });

        return view;
    }

    private class ImageLoaderTask extends AsyncTask<HashMap<String, Object>, Void, HashMap<String, Object>>{

        @Override
        protected HashMap<String, Object> doInBackground(HashMap<String, Object>... hm) {


            InputStream iStream= null;
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
                File tmpFile = new File(cacheDirectory.getPath() + "/wpta_"+position+".png");

                // The FileOutputStream to the temporary file
                FileOutputStream fOutStream = new FileOutputStream(tmpFile);

                // Creating a bitmap from the downloaded inputstream
                Bitmap b = BitmapFactory.decodeStream(iStream);

                // Writing the bitmap to the temporary file as png or jpeg file
                b.compress(Bitmap.CompressFormat.JPEG,2, fOutStream);

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



            }catch (Exception e) {
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
                SimpleAdapter adapter = (SimpleAdapter) movieList.getAdapter();

                // Getting the hashmap object at the specified position of the listview
                HashMap<String, Object> hm = (HashMap<String, Object>) adapter.getItem(position);

                // Overwriting the existing path in the adapter
                hm.put("IMG", path);


                // Noticing listview about the dataset changes
                adapter.notifyDataSetChanged();
            }
            catch (Exception e) {
                Log.d(TAG,"NO IMAGE URL");
            }
            }

        }
    }
