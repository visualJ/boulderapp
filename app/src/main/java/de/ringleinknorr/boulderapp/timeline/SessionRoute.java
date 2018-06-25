package de.ringleinknorr.boulderapp.timeline;

import android.support.annotation.NonNull;

import de.ringleinknorr.boulderapp.routes.Route;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.converter.PropertyConverter;
import io.objectbox.relation.ToOne;

@Entity
public class SessionRoute {
    @Id
    private long id;
    @Convert(converter = ResultConverter.class, dbType = Integer.class)
    private Result result;
    private ToOne<Route> route;
    private ToOne<Session> session;

    public SessionRoute() {
        result = Result.FAILURE;
    }

    public SessionRoute(long routeId, long sessionId) {
        this();
        this.route.setTargetId(routeId);
        this.session.setTargetId(sessionId);
    }

    public SessionRoute(Route route, Session session) {
        this();
        this.route.setTarget(route);
        this.session.setTarget(session);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ToOne<Route> getRoute() {
        return route;
    }

    public void setRoute(ToOne<Route> route) {
        this.route = route;
    }

    public ToOne<Session> getSession() {
        return session;
    }

    public void setSession(ToOne<Session> session) {
        this.session = session;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(@NonNull Result result) {
        this.result = result;
    }

    public enum Result {
        SUCCESS(0), FAILURE(1);

        final int id;

        Result(int id) {
            this.id = id;
        }
    }

    public static class ResultConverter implements PropertyConverter<Result, Integer> {
        @Override
        public Result convertToEntityProperty(Integer databaseValue) {
            if (databaseValue != null) {
                for (Result result : Result.values()) {
                    if (result.id == databaseValue) {
                        return result;
                    }
                }
            }
            return Result.FAILURE;
        }

        @Override
        public Integer convertToDatabaseValue(Result entityProperty) {
            return entityProperty == null ? null : entityProperty.id;
        }
    }

}
