package de.ringleinknorr.boulderapp.routes;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.converter.PropertyConverter;
import io.objectbox.relation.ToOne;

@Entity
public class Route {
    @Id
    private long id;

    private ToOne<Gym> gym;

    private String imageId;

    @Convert(converter = LevelConverter.class, dbType = Integer.class)
    private Level level;

    public enum Level {
        SCHWER(5), LEICHT(1), MITTEL(3);

        final int id;

        Level(int id) {
            this.id = id;
        }
    }

    public Route(Level level, Gym gym, String imageId) {
        this.level = level;
        this.gym.setTarget(gym);
        this.imageId = imageId;
    }

    public Route() {

    }

    public static class LevelConverter implements PropertyConverter<Level, Integer> {
        @Override
        public Level convertToEntityProperty(Integer databaseValue) {
            if (databaseValue != null) {
                for (Level level : Level.values()) {
                    if (level.id == databaseValue) {
                        return level;
                    }
                }
            }
            return Level.LEICHT;
        }

        @Override
        public Integer convertToDatabaseValue(Level entityProperty) {
            return entityProperty == null ? null : entityProperty.id;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Level getLevel() {
        return level;
    }

    public ToOne<Gym> getGym() {
        return gym;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
