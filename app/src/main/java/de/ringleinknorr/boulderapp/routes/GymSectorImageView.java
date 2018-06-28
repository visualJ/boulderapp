package de.ringleinknorr.boulderapp.routes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.snatik.polygon.Point;
import com.snatik.polygon.Polygon;

import java.util.List;

import butterknife.BindColor;
import butterknife.ButterKnife;
import de.ringleinknorr.boulderapp.R;

public class GymSectorImageView extends android.support.v7.widget.AppCompatImageView {
    private Paint p;
    private Gym gym;
    private GymSector selectedSector;
    private OnSectorSelectedListener onSectorSelectedListener;

    @BindColor(R.color.colorPrimary)
    int selectedColor;
    @BindColor(R.color.colorLightGreyTransparent)
    int selectedColorTransparent;
    @BindColor(R.color.colorLightGrey)
    int deselectedColor;

    public GymSectorImageView(Context c, AttributeSet attrs) {
        super(c, attrs);
        ButterKnife.bind(this);
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(4);
        p.setColor(deselectedColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (gym != null) {
            for (GymSector sector : gym.getGymSectors()) {
                drawSector(canvas, sector);
            }
            if (selectedSector != null) {
                // draw selected sector over other sectors
                p.setColor(selectedColor);
                drawSector(canvas, selectedSector);
                p.setColor(deselectedColor);
            }
        }
    }

    public void drawSector(Canvas canvas, GymSector sector) {
        List<GymSectorCoord> coords = sector.getGymSectorCoords();
        float scaleFactorX = this.getMeasuredWidth() / 640f;
        float scaleFactorY = this.getMeasuredHeight() / 400f;

        Path sectorPath = new Path();


        for (GymSectorCoord coord : coords.subList(0, coords.size())) {
               if (coords.indexOf(coord) == 0){
                sectorPath.moveTo(coord.getX()*scaleFactorX, coord.getY() * scaleFactorY);
            }else {
                sectorPath.lineTo(coord.getX()*scaleFactorX, coord.getY() * scaleFactorY);
            }
        }
       sectorPath.lineTo( coords.get(0).getX() * scaleFactorX, coords.get(0).getY() * scaleFactorY);
       canvas.drawPath(sectorPath,p);

       if (sector.equals(selectedSector)){
           p.setStyle(Paint.Style.FILL);
           p.setColor(selectedColorTransparent);
           canvas.drawPath(sectorPath,p);
           p.setStyle(Paint.Style.STROKE);

       }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();
            Point touchPoint = new Point(x, y);
            for (GymSector sector : gym.getGymSectors()) {
                Polygon sectorPolygon = buildPolygon(sector);
                if (sectorPolygon.contains(touchPoint)) {
                    this.selectedSector = sector == this.selectedSector ? null : sector;
                    invalidate();
                    if (onSectorSelectedListener != null) {
                        onSectorSelectedListener.onSectorSelected(this.selectedSector);
                    }
                    return true;
                }
            }
            this.selectedSector = null;
            invalidate();
            if (onSectorSelectedListener != null) {
                onSectorSelectedListener.onSectorSelected(null);
            }
        }
        return true;
    }

    private Polygon buildPolygon(GymSector gymSector) {
        Polygon.Builder polygonBuilder = new Polygon.Builder();
        float scaleFactorX = this.getMeasuredWidth() / 640f;
        float scaleFactorY = this.getMeasuredHeight() / 400f;
        for (GymSectorCoord coord : gymSector.getGymSectorCoords()) {
            polygonBuilder.addVertex(new Point(coord.getX() * scaleFactorX, coord.getY() * scaleFactorY));
        }
        return polygonBuilder.build();
    }

    public Gym getGym() {
        return gym;
    }

    public void setGym(Gym gym) {
        this.gym = gym;
        this.selectedSector = null;
    }

    public GymSector getSelectedSector() {
        return selectedSector;
    }

    public void setSelectedSector(GymSector selectedSector) {
        this.selectedSector = selectedSector;
    }

    public OnSectorSelectedListener getOnSectorSelectedListener() {
        return onSectorSelectedListener;
    }

    public void setOnSectorSelectedListener(OnSectorSelectedListener onSectorSelectedListener) {
        this.onSectorSelectedListener = onSectorSelectedListener;
    }

    public interface OnSectorSelectedListener {
        void onSectorSelected(GymSector sector);
    }

}

