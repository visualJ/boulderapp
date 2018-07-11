package de.ringleinknorr.boulderapp.dependencyinjection;

import javax.inject.Singleton;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import de.ringleinknorr.boulderapp.fragments.NewSessionFragment;
import de.ringleinknorr.boulderapp.fragments.RouteFragment;
import de.ringleinknorr.boulderapp.fragments.RouteSearchFragment;
import de.ringleinknorr.boulderapp.fragments.SessionFragment;
import de.ringleinknorr.boulderapp.fragments.StatisticsFragment;
import de.ringleinknorr.boulderapp.fragments.TimelineFragment;

@Module
public abstract class AppDIModule {
    @ContributesAndroidInjector
    @Singleton
    abstract TimelineFragment timelineFragment();

    @ContributesAndroidInjector
    @Singleton
    abstract NewSessionFragment newSessionFragment();

    @ContributesAndroidInjector
    @Singleton
    abstract SessionFragment sessionFragment();

    @ContributesAndroidInjector
    abstract StatisticsFragment statisticsFragment();

    @ContributesAndroidInjector
    @Singleton
    abstract RouteSearchFragment routeSearchFragment();

    @ContributesAndroidInjector
    @Singleton
    abstract RouteFragment routeFragment();
}
