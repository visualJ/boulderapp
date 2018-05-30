package de.ringleinknorr.boulderapp;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import de.ringleinknorr.boulderapp.timeline.TimelineFragment;

@Module
public abstract class AppDIModule {
    @ContributesAndroidInjector
    abstract TimelineFragment timelineFragment();
}
