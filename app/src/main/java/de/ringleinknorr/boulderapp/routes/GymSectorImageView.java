package de.ringleinknorr.boulderapp.routes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.snatik.polygon.Point;
import com.snatik.polygon.Polygon;

import java.util.List;

import de.ringleinknorr.boulderapp.R;

public class GymSectorImageView extends android.support.v7.widget.AppCompatImageView {
    private Paint p;
    private Gym gym;
    private long currentGymId;

    GymSector selectedSector;

    public GymSectorImageView(Context c, AttributeSet attrs) {
        super(c, attrs);
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(8);
        p.setColor(getResources().getColor(R.color.colorLightGrey));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (gym != null) {
            for (GymSector sector : gym.getGymSectors()) {
                drawSector(canvas, sector);
            }

            if(currentGymId != gym.getId()){
                selectedSector =null;
            }

            if(selectedSector != null ){
                p.setColor(getResources().getColor(R.color.colorPrimary));
                drawSector(canvas, selectedSector);
            }
            p.setColor(getResources().getColor(R.color.colorLightGrey));
            currentGymId = gym.getId();
        }
    }

    public void drawSector(Canvas canvas , GymSector sector){
        GymSectorCoord tempCoord = null;
        List<GymSectorCoord> coords = sector.getGymSectorCoords();
        for (GymSectorCoord coord : coords) {
            if (tempCoord != null) {
                canvas.drawLine(tempCoord.getX(), tempCoord.getY(), coord.getX(), coord.getY(), p);
            }
            tempCoord = coord;
            if (coords.indexOf(coord) == coords.size()-1){
                canvas.drawLine(tempCoord.getX(), tempCoord.getY(), coords.get(0).getX(), coords.get(0).getY(), p);
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();
        Point touchPoint = new Point(x,y);
        for (GymSector sector : gym.getGymSectors()){
            Polygon sectorPolygon = buildPolygon(sector);
            if (sectorPolygon.contains(touchPoint)){
                this.selectedSector = sector;
                invalidate();
                return true;
            }
        }
        return true;
    }

    private Polygon buildPolygon(GymSector gymSector){
        Polygon.Builder polygonBuilder = new Polygon.Builder();
        for (GymSectorCoord coord : gymSector.getGymSectorCoords()){
            polygonBuilder.addVertex(new Point(coord.getX(),coord.getY()));
        }
        return polygonBuilder.build();
    }

    public Gym getGym() {
        return gym;
    }

    public void setGym(Gym gym) {
        this.gym = gym;
    }
}

