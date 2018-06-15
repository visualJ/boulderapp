package de.ringleinknorr.boulderapp.routes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import java.util.List;

public class GymSectorImageView extends android.support.v7.widget.AppCompatImageView {
    private Paint p;
    private Gym gym;

    public GymSectorImageView(Context c, AttributeSet attrs) {
        super(c, attrs);
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (gym != null) {
            for (GymSector sector : gym.getGymSectors()) {
                GymSectorCoord tempCoord = null;
                List<GymSectorCoord> coords = sector.getGymSectorCoords();
                for (GymSectorCoord coord : coords) {
                    if (tempCoord != null) {
                        canvas.drawLine(tempCoord.getX(), tempCoord.getY(), coord.getX(), coord.getY(), p);
                    }
                    tempCoord = coord;
                }
                canvas.drawLine(tempCoord.getX(), tempCoord.getY(), coords.get(0).getX(), coords.get(0).getY(), p);
            }
        }
    }


    public Gym getGym() {
        return gym;
    }

    public void setGym(Gym gym) {
        this.gym = gym;
    }
}

