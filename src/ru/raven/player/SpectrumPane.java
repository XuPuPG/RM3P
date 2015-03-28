package ru.raven.player;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer;

public class SpectrumPane extends BorderPane {

    private final LineChart<Number, Number> ac;
    public XYChart.Data<Number, Number>[] charts;
    private final MediaPlayer mp;

    public SpectrumPane(MediaPlayer mp) {
        this.mp = mp;
        NumberAxis xAxis = new NumberAxis(0, 128, 8);
        NumberAxis yAxis = new NumberAxis(0, 50, 10);
        ac = new LineChart<>(xAxis, yAxis);
        charts = new XYChart.Data[(int) xAxis.getUpperBound()];
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 0; i < charts.length; i++) {
            charts[i] = new XYChart.Data<>(i, 50);
            series.getData().add(charts[i]);
        }
        ac.getData().add(series);
        setCenter(ac);
    }
}
