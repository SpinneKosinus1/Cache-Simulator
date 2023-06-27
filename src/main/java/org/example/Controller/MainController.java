package org.example.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.example.Model.Result;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController {
    @FXML
    private TableColumn<Result, String> fileName;

    @FXML
    private TableColumn<Result, String> blockNumber;

    @FXML
    private TableColumn<Result, String> blockSize;

    @FXML
    private TableColumn<Result, String> associativity;

    @FXML
    private TableColumn<Result, String> replacement;

    @FXML
    private TableColumn<Result, String> writeHit;

    @FXML
    private TableColumn<Result, String> writeMiss;

    @FXML
    private TableColumn<Result, String> cacheReadHitRate;

    @FXML
    private TableColumn<Result, String> cacheWriteHitRate;

    @FXML
    private TableColumn<Result, String> cacheEvictions;

    @FXML
    private TableView<Result> tableView;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fileName.setCellValueFactory(new PropertyValueFactory<Result, String>("fileName"));
        blockNumber.setCellValueFactory(new PropertyValueFactory<Result, String>("blockNumber"));
        blockSize.setCellValueFactory(new PropertyValueFactory<Result, String>("blockSize"));
        associativity.setCellValueFactory(new PropertyValueFactory<Result, String>("associativity"));
        replacement.setCellValueFactory(new PropertyValueFactory<Result, String>("replacement"));
        writeHit.setCellValueFactory(new PropertyValueFactory<Result, String>("writeHit"));
        writeMiss.setCellValueFactory(new PropertyValueFactory<Result, String>("writeMiss"));
        cacheReadHitRate.setCellValueFactory(new PropertyValueFactory<Result, String>("cacheReadHitRate"));
        cacheWriteHitRate.setCellValueFactory(new PropertyValueFactory<Result, String>("cacheReadMissRate"));
        cacheEvictions.setCellValueFactory(new PropertyValueFactory<Result, String>("cacheEvictions"));

    }

    SimulationController controller;

    @FXML
    private BorderPane borderPane;

    private void AddToTable(Result result) {
        ObservableList<Result> results = FXCollections.observableArrayList(result);
        results.add(result);
        tableView.setItems(results);
    }

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
            Result result1 = controller.ProcessResults();
            AddToTable(result1);
            System.out.println("OK pressed");
        }
        else {
            System.out.println("Cancel pressed");
        }
    }
}