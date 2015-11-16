package com.example.ysych.discounttravel.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.ysych.discounttravel.R;
import com.example.ysych.discounttravel.data.HelperFactory;
import com.example.ysych.discounttravel.fragments.FragmentCountry;
import com.example.ysych.discounttravel.model.Country;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final long DRAWER_CLOSE_DELAY_MS = 250;
    private static final String NAV_ITEM_ID = "navItemId";

    private final Handler mDrawerActionHandler = new Handler();
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private int mNavItemId;
    private NavigationView navigationView;
    private List<Country> countries;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // listen for navigation events
        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.inflateHeaderView(R.layout.drawer_header);

        try {
            countries = HelperFactory.getHelper().getCountryDAO().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(Country country : countries){
            navigationView.getMenu().add(0, country.getId(), 0, country.getTitle().toUpperCase());
        }

        // load saved navigation state if present
        if (null == savedInstanceState) {
            mNavItemId = R.id.all_tours;
        } else {
            mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
        }

        navigationView.setNavigationItemSelectedListener(this);

        // set up the hamburger icon to open and close the drawer
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open,
                R.string.close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        navigate(mNavItemId);
    }

    private void navigate(int itemId) {
        // perform the actual navigation logic, updating the main content fragment etc
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new FragmentCountry();
        Bundle bundle = new Bundle();
        bundle.putLong(FragmentCountry.COUNTRY_CODE, itemId);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(final MenuItem menuItem) {
        // update highlighted item in the navigation menu
        menuItem.setChecked(true);
        mNavItemId = menuItem.getItemId();

        // allow some time after closing the drawer before performing real navigation
        // so the user can see what is happening
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mDrawerActionHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(menuItem.getItemId());
            }
        }, DRAWER_CLOSE_DELAY_MS);
        return true;
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.support.v7.appcompat.R.id.home) {
            return mDrawerToggle.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, mNavItemId);
    }
}
