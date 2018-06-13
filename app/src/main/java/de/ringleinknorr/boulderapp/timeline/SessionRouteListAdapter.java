package de.ringleinknorr.boulderapp.timeline;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import de.ringleinknorr.boulderapp.routes.RouteCardView;

public class SessionRouteListAdapter extends RecyclerView.Adapter<SessionRouteListAdapter.ViewHolder> {
    private List<SessionRoute> routeList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RouteCardView view;

        public ViewHolder(RouteCardView v) {
            super(v);
            view = v;
        }
    }

    public SessionRouteListAdapter(List<SessionRoute> routeList) {
        this.routeList = routeList;
    }

    @Override
    public SessionRouteListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                 int viewType) {
        return new ViewHolder(new RouteCardView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String routeLevel = routeList.get(position).getRoute().getTarget().getLevel().name();
        holder.view.getRouteLevelText().setText(routeLevel);
    }

    public void setRoutes(List<SessionRoute> routeList) {
        this.routeList = routeList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }
}
