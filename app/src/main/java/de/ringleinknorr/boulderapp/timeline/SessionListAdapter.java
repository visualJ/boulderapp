package de.ringleinknorr.boulderapp.timeline;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SessionListAdapter extends RecyclerView.Adapter<SessionListAdapter.ViewHolder> {

    private List<Session> sessions;
    private Locale locale;
    private OnSessionSelectedListener onSessionSelectedListener;

    public SessionListAdapter(List<Session> sessions, Locale locale, OnSessionSelectedListener onSessionSelectedListener) {
        this.sessions = sessions;
        this.locale = locale;
        this.onSessionSelectedListener = onSessionSelectedListener;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
        notifyDataSetChanged();
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
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, locale);

        SessionCardView view = holder.view;
        view.dayText.setText(day);
        view.monthText.setText(String.valueOf(month.toUpperCase()));
        view.gymText.setText(String.valueOf(session.getGym().getTarget().getName()));
        view.setRoutes(session.getRoutes().size());
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    public OnSessionSelectedListener getOnSessionSelectedListener() {
        return onSessionSelectedListener;
    }

    public void setOnSessionSelectedListener(OnSessionSelectedListener onSessionSelectedListener) {
        this.onSessionSelectedListener = onSessionSelectedListener;
    }

    interface OnSessionSelectedListener {
        void onSessionSelected(long sessionId);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public SessionCardView view;

        public ViewHolder(SessionCardView itemView) {
            super(itemView);
            view = itemView;
            view.setOnClickListener(ignore -> {
                long sessionId = sessions.get(getAdapterPosition()).getId();
                onSessionSelectedListener.onSessionSelected(sessionId);
            });
        }
    }

}
