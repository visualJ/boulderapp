package de.ringleinknorr.boulderapp.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.ringleinknorr.boulderapp.R;
import de.ringleinknorr.boulderapp.models.Session;

public class SessionsStatisticsView extends ConstraintLayout {

    @BindView(R.id.session_statistics_month_trend)
    TextView sessionStatisticsMonthTrend;
    @BindView(R.id.session_statistics_previous_session_trend)
    TextView sessionStatisticsPreviousSessionTrend;
    @BindView(R.id.session_statistics_chart)
    LineChart chart;
    @BindView(R.id.icon_session_statistics_prev_session_trend)
    ImageView prevSessionTrendIcon;
    @BindView(R.id.icon_session_statistics_monthly_trend)
    ImageView monthlyTrendIcon;

    @BindColor(R.color.colorPrimary)
    int colorGreen;
    @BindColor(R.color.colorDarkGrey)
    int colorGrey;

    public SessionsStatisticsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_session_statistics, this);
        ButterKnife.bind(this);
        setLayoutParams(new Constraints.LayoutParams(Constraints.LayoutParams.MATCH_PARENT, Constraints.LayoutParams.WRAP_CONTENT));

    }

    public void setMonthlyTrend(double trend) {
        if (trend < 0) {
            monthlyTrendIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_trending_down_black_24dp));
        } else if (trend == 0) {
            monthlyTrendIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_trending_flat_black_24dp));
        } else {
            monthlyTrendIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_trending_up_black_24dp));
        }
        monthlyTrendIcon.setVisibility(VISIBLE);
        sessionStatisticsMonthTrend.setText(getResources().getString(R.string.trend_value_with_decimals, (trend)));
    }

    public void setPreviousSessionTrend(double trend){
        if (trend < 0) {
            prevSessionTrendIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_trending_down_black_24dp));
        } else if (trend == 0) {
            prevSessionTrendIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_trending_flat_black_24dp));
        } else {
            prevSessionTrendIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_trending_up_black_24dp));
        }
        prevSessionTrendIcon.setVisibility(VISIBLE);
        sessionStatisticsPreviousSessionTrend.setText(getResources().getString(R.string.trend_value, (trend)));
    }

    public void setLineChart(List<Session> previousSessions){
        List<Entry> entries1 = new ArrayList<>();
        List<Entry> entries2 = new ArrayList<>();

       final List<String> xAxisLabels = new ArrayList<>();

        for (Session session : previousSessions) {
            entries2.add(new Entry(previousSessions.indexOf(session), session.getSuccessfulSessionRoutes().size()));
            entries1.add(new Entry(previousSessions.indexOf(session), session.getRoutes().size()));
            DateFormat df = new SimpleDateFormat("dd.MM");

            xAxisLabels.add(df.format(session.getDate()));
        }

        LineDataSet dataSet1 = new LineDataSet(entries1, "Hinzugefügte Routen");
        dataSet1.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet1.setColor(colorGrey);
        dataSet1.setValueTextColor(colorGrey);
        dataSet1.setValueTextSize(10f);
        dataSet1.setCircleRadius(5f);
        dataSet1.setCircleColor(colorGrey);
        dataSet1.setLineWidth(2f);
        dataSet1.enableDashedLine(20f, 10f, 4f);


        LineDataSet dataSet2 = new LineDataSet(entries2, "Geschaffte Routen");
        dataSet2.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet2.setColor(colorGreen);
        dataSet2.setValueTextColor(colorGrey);
        dataSet2.setValueTextSize(10f);
        dataSet2.setCircleRadius(5f);
        dataSet2.setCircleColor(colorGreen);
        dataSet2.setLineWidth(2f);

        LineData lineData1 = new LineData(dataSet1,dataSet2);
        chart.setData(lineData1);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(false);
        leftAxis.setDrawGridLines(false);
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
        rightAxis.setDrawGridLines(false);

        IAxisValueFormatter formatter = (value, axis) -> {
            if(value >= 0) {
                if (xAxisLabels.size() > (int) value) {
                    return xAxisLabels.get((int) value);
                } else return "";
            }else {
                return "";
            }
        };

        IValueFormatter valueFormatter = (value, entry, dataSetIndex, viewport) -> {
            DecimalFormat mFormat = new DecimalFormat("0");
            return mFormat.format(value);
        };

        dataSet1.setValueFormatter(valueFormatter);
        dataSet2.setValueFormatter(valueFormatter);

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(formatter);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);

        Legend chartLegend = chart.getLegend();
        chartLegend.setForm(Legend.LegendForm.LINE);
        Description chartDescription = new Description();
        chartDescription.setText("");
        chart.setDescription(chartDescription);
        chart.invalidate();
    }

}
