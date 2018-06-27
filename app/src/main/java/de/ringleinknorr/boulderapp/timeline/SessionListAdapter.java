package de.ringleinknorr.boulderapp.timeline;

import android.support.annotation.NonNull;
import android.text.format.DateUtils;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.ringleinknorr.boulderapp.ItemListAdapter;

import static de.ringleinknorr.boulderapp.App.getContext;

public class SessionListAdapter extends ItemListAdapter<Session, SessionCardView> {

    private Locale locale;

    public SessionListAdapter(List<Session> sessions, Locale locale, OnItemClickListener<Session, SessionCardView> onItemClickListener) {
        super(sessions, onItemClickListener);
        this.locale = locale;
    }

    @Override
    public void onBindView(SessionCardView sessionCardView, int position, Session session) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(session.getDate());
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, locale);

        sessionCardView.setAlpha(1f);
        sessionCardView.dayText.setText(day);
        sessionCardView.monthText.setText(String.valueOf(month.toUpperCase()));
        sessionCardView.gymText.setText(String.valueOf(session.getGym().getTarget().getName()));
        sessionCardView.setRoutes(session.getRoutes().size());
        sessionCardView.setSuccessfulRoutes(session.getSuccessfulSessionRoutes().size());
    }

    @Override
    public SessionCardView onCreateView(@NonNull ViewGroup parent, int viewType) {
        return new SessionCardView(parent.getContext());
    }

    @NonNull
    @Override
    public String provideSectionTitle(int position) {
        if (position == 0 || isMothDifferent(position)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(getItems().get(position).getDate());
            String date = DateUtils.formatDateTime(getContext(), cal.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE + DateUtils.FORMAT_SHOW_YEAR + DateUtils.FORMAT_NO_MONTH_DAY);
            return date;
        } else {
            return "";
        }
    }

    private boolean isMothDifferent(int position) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(getItems().get(position - 1).getDate());
        cal2.setTime(getItems().get(position).getDate());
        return cal1.get(Calendar.MONTH) != cal2.get(Calendar.MONTH) || cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR);
    }
}
