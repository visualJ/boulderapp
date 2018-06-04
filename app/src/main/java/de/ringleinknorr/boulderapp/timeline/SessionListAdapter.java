package de.ringleinknorr.boulderapp.timeline;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SessionListAdapter extends RecyclerView.Adapter<SessionListAdapter.ViewHolder> {

    private List<Session> sessions;
    private Locale locale;

    public List<Session> getSessions() {
        return sessions;
    }

    public SessionListAdapter(List<Session> sessions, Locale locale) {
        this.sessions = sessions;
        this.locale = locale;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public SessionCardView view;
        public ViewHolder(SessionCardView itemView) {
            super(itemView);
            view = itemView;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new SessionCardView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Session session = sessions.get(position);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(session.getDate());
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        holder.view.dayText.setText(day);
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, locale);
        holder.view.monthText.setText(String.valueOf(month.toUpperCase()));
        holder.view.gymText.setText(String.valueOf(session.getGym().getTarget().getName()));
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }


}
