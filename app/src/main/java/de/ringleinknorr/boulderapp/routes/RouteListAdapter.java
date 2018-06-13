package de.ringleinknorr.boulderapp.routes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public class RouteListAdapter extends RecyclerView.Adapter<RouteListAdapter.ViewHolder> {
    private List<Route> routeList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RouteCardView view;

        public ViewHolder(RouteCardView v) {
            super(v);
            view = v;
        }
    }

    public RouteListAdapter(List<Route> routeList) {
        this.routeList = routeList;
    }

    @Override
    public RouteListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                          int viewType) {
        return new ViewHolder(new RouteCardView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String routeLevel = routeList.get(position).getLevel().name();
        holder.view.getRouteLevelText().setText(routeLevel);
    }

    public void setRoutes(List<Route> routeList) {
        this.routeList = routeList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }
}
