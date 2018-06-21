package de.ringleinknorr.boulderapp;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.nav_drawer)
    NavigationView navDrawer;
    @BindView(R.id.app_toolbar)
    Toolbar appToolbar;
    @BindView(R.id.navigation_drawer_layout)
    DrawerLayout drawer;
    private NavController navController;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(appToolbar);
        toggle = new ActionBarDrawerToggle(
                this, drawer, appToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerSlideAnimationEnabled(false);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toggle.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        toggle.setDrawerIndicatorEnabled(false);
        toggle.setToolbarNavigationClickListener(view -> onBackPressed());

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnNavigatedListener((controller, destination) -> {
            showBackArrow(false);
            switch (destination.getId()) {
                case R.id.sessionFragment:
                case R.id.addRouteSearchFragment:
                    showBackArrow(true);
                    break;
                default:
                    showBackArrow(false);
                    break;
            }
        });
        NavigationUI.setupWithNavController(navDrawer, navController);
    }

    public void showBackArrow(boolean enable) {
        toggle.setDrawerIndicatorEnabled(!enable);
    }


    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }
}
