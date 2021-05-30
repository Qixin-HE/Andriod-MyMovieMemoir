package com.example.a2;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.a2.fragment.MapFragment;
import com.example.a2.fragment.MovieSearchFragment;
import com.example.a2.fragment.HomeFragment;
import com.example.a2.fragment.MovieMemoirFragment;
import com.example.a2.fragment.ReportFragment;
import com.example.a2.fragment.WatchlistFragment;
import com.google.android.material.navigation.NavigationView;

public class HomescreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //navigation
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        //final TextView tvgreeting = findViewById(R.id.tvGreeting);

        bundle = getIntent().getExtras();



        /*
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(calendar.getTime());

        tvgreeting.setText("Hi! " + fname + "! " + date);

         */


        //navigationView - adding the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nv);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //these two lines of code show the navicon drawer icon top left hand side
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragmentWithBundle(new HomeFragment(), bundle);
        //==


    }

    //navigationView
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.movieSearch:
                replaceFragment(new MovieSearchFragment());
                break;
            case R.id.movieMemoir:
                replaceFragment(new MovieMemoirFragment());
                break;
            case R.id.homescreen:
                replaceFragmentWithBundle(new HomeFragment(), bundle);
                break;
            case R.id.watchlist:
                replaceFragment(new WatchlistFragment());
                break;
            case R.id.reports:
                replaceFragment(new ReportFragment());
                break;
            case R.id.maps:
                replaceFragment(new MapFragment());
                break;
        }
        //this code closes the drawer after you selected an item from the menu,
        //otherwise stay open
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment nextFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, nextFragment);

        fragmentTransaction.commit();
    }

    private void replaceFragmentWithBundle(Fragment nextFragment, Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, nextFragment);
        nextFragment.setArguments(bundle);
        fragmentTransaction.commit();
    }

    //To use ActionBarDrawerToggle, we need to implement its method onOptionsItemSelected().
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    public Bundle getBundle() {
        return bundle;
    }


}
