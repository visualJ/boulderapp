package de.ringleinknorr.boulderapp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import de.ringleinknorr.boulderapp.routes.RouteSearchFragment;
import de.ringleinknorr.boulderapp.timeline.NewSessionFragment;
import de.ringleinknorr.boulderapp.timeline.SessionFragment;
import de.ringleinknorr.boulderapp.timeline.TimelineFragment;

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
    @Singleton
    abstract RouteSearchFragment routeSearchFragment();
}
