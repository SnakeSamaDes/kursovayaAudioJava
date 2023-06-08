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
import java.util.ResourceBundle;

public class MainWindowController{


    private MediaPlayer mediaplayer;
    private double volume;


    @FXML
    private Slider timeSlider;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Label labelTitle;
    @FXML
    private Label labelArtist;
    @FXML
    private AreaChart<String, Number> spektrum;
    @FXML
    private void openfile(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters()
                    .add(new FileChooser.ExtensionFilter("MP3, WAV", "*.mp3", "*.wav"));
            try {
                File file = fileChooser.showOpenDialog(null);
                Media media = new Media(file.toURI().toString());
                if (mediaplayer != null) {
                    mediaplayer.dispose();
                }
                mediaplayer = new MediaPlayer(media);
            } catch (NullPointerException nullPointerException) {
                nullPointerException.printStackTrace();
            }}}
    @FXML
    private void playClick(ActionEvent actionEvent) {
        try {
            mediaplayer.play();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void stopClick(ActionEvent actionEvent) {
        try {
            mediaplayer.stop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void pauseClick(ActionEvent actionEvent) {
        try {
            mediaplayer.pause();
        }catch (Exception e){
            e.printStackTrace();
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


    
}