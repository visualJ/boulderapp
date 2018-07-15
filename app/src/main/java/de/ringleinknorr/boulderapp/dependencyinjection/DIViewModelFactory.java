package de.ringleinknorr.boulderapp.dependencyinjection;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * A view model factory that injects view models. This can be used to get injected
 * view models from a regular {@link ViewModelProvider}.
 *
 * @param <T>
 */
public class DIViewModelFactory<T extends ViewModel> implements ViewModelProvider.Factory {

    private Lazy<T> viewModel;

    @Inject
    public DIViewModelFactory(Lazy<T> viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) viewModel.get();
    }
}
