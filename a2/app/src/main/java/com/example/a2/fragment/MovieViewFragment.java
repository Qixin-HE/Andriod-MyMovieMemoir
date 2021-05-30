package com.example.a2.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a2.R;

import com.squareup.picasso.Picasso;




public class MovieViewFragment extends Fragment {
    String[] detail;
    private TextView tvMoviename;
    private TextView tvMovieyear;
    private TextView tvMoviesnippet;
    private ImageView ivMovie;



    public MovieViewFragment() {
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String moviedetail = bundle.getString("detail");
            detail = moviedetail.split("\n\n");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.movieview_fragment, container, false);
        tvMoviename = view.findViewById(R.id.tvMoviename);
        tvMovieyear= view.findViewById(R.id.tvReleaseyear);
        tvMoviesnippet= view.findViewById(R.id.tvmoviedetail);
        ivMovie= view.findViewById(R.id.ivMoviepic);

        tvMoviename.setText(detail[0]);
        tvMovieyear.setText(detail[1]);
        tvMoviesnippet.setText(detail[2]);
        //https://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
        Picasso.get().load(detail[3]).into(ivMovie);



        return view;
    }
}

