package com.example.audioplayerkursach.controller;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    private final int BANDS = 128;
    private final double INTERVAL = 0.01;
    private final double DROP = 0.35;

    private MediaPlayer mediaplayer;
    private double volume;
    private XYChart.Data[] chartData;
    @FXML
    private Label lblVolume;
    @FXML
    private Button playButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Slider timeSlider;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Label labelTitle;
    @FXML
    private Label labelArtist;
    @FXML
    private AreaChart<String, Number> spektr;
    @FXML
    private void openfile(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters()
                    .add(new FileChooser.ExtensionFilter("MP3, WAV", "*.mp3","*.wav"));
            try {
                //
                File file = fileChooser.showOpenDialog(null);
                if (file == null) {System.out.println("NULL");
                }else{
                    playButton.setDisable(false);
                    pauseButton.setDisable(false);
                    stopButton.setDisable(false);
                    Media media = new Media(file.toURI().toString());
                    if(mediaplayer!=null){
                        mediaplayer.dispose();
                    }
                    mediaplayer = new MediaPlayer(media);
                    setMediaplayer(mediaplayer);
                }
            }catch (NullPointerException nullPointerException){
                nullPointerException.printStackTrace();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    private void setMediaplayer(MediaPlayer mediaplayer){
        try {
            // timeSlider
            mediaplayer.setOnReady(()->{
                try {
                    String artist = (String) mediaplayer.getMedia().getMetadata().get("artist");
                    String title = (String) mediaplayer.getMedia().getMetadata().get("title");
                    labelArtist.setText(artist != null ? artist : "");
                    labelTitle.setText(title != null ? title : "");
                    timeSlider.setMin(0);
                    timeSlider.setMax(mediaplayer.getMedia().getDuration().toSeconds());
                    timeSlider.setValue(0);
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            });
            mediaplayer.setAudioSpectrumListener(new SpektrumListener());
            //lister
            mediaplayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                    Duration d = mediaplayer.getCurrentTime();
                    timeSlider.setValue(d.toSeconds());
                }
            });
            timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    if(timeSlider.isPressed()){
                        double value = timeSlider.getValue();
                        mediaplayer.seek(new Duration(value*1000));
                    }
                }
            });
            //volumeSlider change volume
            volumeSlider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaplayer.setVolume(volumeSlider.getValue());
                }
            });
            if (volumeSlider.isPressed()) {
                volume = volumeSlider.getValue();
                if (mediaplayer != null) {
                    mediaplayer.setVolume(volume);
                }
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    };
    @FXML
    private void playClick(ActionEvent actionEvent) {
        try {
            mediaplayer.play();
            mediaplayer.setAudioSpectrumInterval(INTERVAL);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    @FXML
    private void stopClick(ActionEvent actionEvent) {
        try {
            mediaplayer.stop();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    @FXML
    private void pauseClick(ActionEvent actionEvent) {
        try {
            mediaplayer.pause();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    @FXML
    private void closeApplication(ActionEvent actionEvent) {
        mediaplayer = null;
        Platform.exit();
    }
    @FXML
    private void refreshClick(ActionEvent actionEvent) {
        if(mediaplayer!=null){
            mediaplayer.dispose();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        volume = 1.0;
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        chartData = new XYChart.Data[BANDS+2];
        for (int i = 0; i < chartData.length; i++) {
            chartData[i] = new XYChart.Data<>(Integer.toString(i + 1), 0);
            dataSeries.getData().add(chartData[i]);
        }
        spektr.getData().add(dataSeries);
    }
    private float[] createFilledBuffer(int size, float fillValue) {
        float[] floats = new float[size];
        Arrays.fill(floats, fillValue);
        return floats;
    }
    private class SpektrumListener implements AudioSpectrumListener {
        float[] buffer = createFilledBuffer(BANDS, mediaplayer.getAudioSpectrumThreshold());
        @Override
        public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
            Platform.runLater(() -> {
                chartData[0].setYValue(0);
                chartData[BANDS].setYValue(0);
                for (int i = 0; i < magnitudes.length; i++) {
                    if (magnitudes[i] >= buffer[i]) {
                        buffer[i] = magnitudes[i];
                        chartData[i + 1].setYValue(magnitudes[i] - mediaplayer.getAudioSpectrumThreshold());
                    } else {
                        chartData[i + 1].setYValue(buffer[i] - mediaplayer.getAudioSpectrumThreshold());
                        buffer[i] -= DROP;
                    }
                }
            });
        }
    }
}