package ru.raven.player;

import javafx.scene.media.AudioSpectrumListener;


public class SpectrumListener implements AudioSpectrumListener {

    private final SpectrumPane pane;

    public SpectrumListener(SpectrumPane pane) {
        this.pane = pane;
    }

    @Override
    public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
        for (int i = 0; i < pane.charts.length; i++) {
            pane.charts[i].setYValue((int) magnitudes[i] + 60);
        }
    }
}
