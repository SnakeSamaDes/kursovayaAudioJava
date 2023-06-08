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
    }
    @FXML
    private void playClick(ActionEvent actionEvent) {
    }
    @FXML
    private void stopClick(ActionEvent actionEvent) {
    }
    @FXML
    private void pauseClick(ActionEvent actionEvent) {
    }
    @FXML
    private void closeApplication(ActionEvent actionEvent) {
    }
    @FXML
    private void refreshClick(ActionEvent actionEvent) {
    }


}