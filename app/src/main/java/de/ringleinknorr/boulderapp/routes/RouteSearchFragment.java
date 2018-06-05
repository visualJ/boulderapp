package de.ringleinknorr.boulderapp.routes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.ringleinknorr.boulderapp.R;

public class RouteSearchFragment extends Fragment {

    @BindView(R.id.auto_complete_text_view)
    AutoCompleteTextView
            autoCompleteTextView;
    @BindView(R.id.gym_sector_view)
    ImageView gymSectorImageView;

    public RouteSearchFragment() {
        // Required empty public constructor
    }

    @SuppressLint({"ClickableViewAccessibility"})
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_route_search, container, false);
        ButterKnife.bind(this, view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_dropdown_item_1line, HALLEN);

        AutoCompleteTextView textView = autoCompleteTextView;
        textView.setAdapter(adapter);
        gymSectorImageView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // Select sector
            }
            return true;
        });

        return view;
    }

    private static final String[] HALLEN = new String[]{
            "Nordwand Wiesbaden", "Boulderwelt Frankfurt", "Blockwerk Mainz"
    };
    private static final Integer[][] COORDS = new Integer[][]{
            {0, 0, 100, 100}
    };
}
