package de.ringleinknorr.boulderapp.timeline;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.ringleinknorr.boulderapp.ItemListAdapter;

public class SessionListAdapter extends ItemListAdapter<Session, SessionCardView> {

    private Locale locale;

    public SessionListAdapter(List<Session> sessions, Locale locale, OnItemClickListener<Session> onItemClickListener) {
        super(sessions, onItemClickListener);
        this.locale = locale;
    }

    @Override
    public void onBindView(SessionCardView sessionCardView, int position, Session session) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(session.getDate());
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, locale);

        sessionCardView.dayText.setText(day);
        sessionCardView.monthText.setText(String.valueOf(month.toUpperCase()));
        sessionCardView.gymText.setText(String.valueOf(session.getGym().getTarget().getName()));
        sessionCardView.setRoutes(session.getRoutes().size());
    }

    @Override
    public SessionCardView onCreateView(@NonNull ViewGroup parent, int viewType) {
        return new SessionCardView(parent.getContext());
    }

}
