package org.example.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.example.Model.CacheData.Cache;
import org.example.Model.Model;
import org.example.Model.Result;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MainController {
    private static List<Result> Results;
    @FXML
    public void initialize() {
        Results = new ArrayList<>();

    }

    SimulationController controller;

    @FXML
    private BorderPane borderPane;


    @FXML
    public void ShowNewSimulationDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(borderPane.getScene().getWindow());
        FXMLLoader fxmlloader = new FXMLLoader();
        fxmlloader.setLocation(getClass().getResource("/Simulation.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlloader.load());
        }
        catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            controller = fxmlloader.getController();
            Results.add(controller.ProcessResults());
            System.out.println("OK pressed");
        }
        else {
            System.out.println("Cancel pressed");
        }
    }
}