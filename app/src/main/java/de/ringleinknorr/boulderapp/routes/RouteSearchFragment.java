package de.ringleinknorr.boulderapp.routes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.ringleinknorr.boulderapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RouteSearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RouteSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RouteSearchFragment extends Fragment {

    public RouteSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_route_search, container, false);
    }
}
