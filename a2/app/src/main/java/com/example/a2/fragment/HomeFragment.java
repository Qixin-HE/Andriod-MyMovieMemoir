package com.example.a2.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.a2.Parcelable.Person;
import com.example.a2.R;
import com.example.a2.networkconnection.NetworkConnection;
import com.example.a2.post.Top5movie;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {
    //move
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    //NetworkConnection networkConnection = null;


    int credentialsid;
    NetworkConnection networkConnection = null;
    /*
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
*/


    //List-view
    List<HashMap<String, String>> movieListArray;
    SimpleAdapter myListAdapter;
    ListView top5list;
    HashMap<String, String> map = new HashMap<String, String>();
    String[] colHead = new String[]{"MOVIE", "DATE", "SCORE"};
    int[] dataCell = new int[]{R.id.tvMname, R.id.tvMreleasedate, R.id.tvMratingscore};


    //move ends
    String fname;

    public HomeFragment() {
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        networkConnection = new NetworkConnection();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Person singinperson = bundle.getParcelable("person1");
            credentialsid = singinperson.getCredentialsid();
            Get2020TopWatchedMovie get2020TopWatchedMovie = new Get2020TopWatchedMovie();
            get2020TopWatchedMovie.execute(String.valueOf(credentialsid));
            fname = singinperson.getFname();
        }




        //List-view


        movieListArray = new ArrayList<HashMap<String, String>>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        TextView tvgreeting = view.findViewById(R.id.tvGreeting);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(calendar.getTime());

        top5list = view.findViewById(R.id.lvMoviesearch);


        tvgreeting.setText("Hi! " + fname + "! " + date);

        return view;
    }

    //get top 5 movie
    private class Get2020TopWatchedMovie extends AsyncTask<String, Void, ArrayList<Top5movie>> {
        @Override
        protected ArrayList<Top5movie> doInBackground(String... params) {
            String credentialsid = params[0];

            return networkConnection.get2020TopWatchedMovie(credentialsid);
        }

        @Override
        protected void onPostExecute(ArrayList<Top5movie> movielist) {
            //"MOVIE", "DATE", "SCORE"
            for (Top5movie m : movielist) {
                map = new HashMap<String, String>();
                String moviename = m.getMovieName();
                String year = m.getReleaseYear();
                String score = m.getRatingScore();
                map.put("MOVIE", moviename);
                map.put("DATE", year);
                map.put("SCORE", score);
                movieListArray.add(map);
            }
            myListAdapter = new SimpleAdapter(getActivity(), movieListArray, R.layout.homefragment_list_view, colHead, dataCell);
            top5list.setAdapter((myListAdapter));

        }
    }
}
