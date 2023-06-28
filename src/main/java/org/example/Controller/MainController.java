package org.example.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.example.Model.Result;

import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML private Button exportButton;
    private ObservableList<Result> tableResult = FXCollections.observableArrayList();

    @FXML private TableView<Result> tableView;

    @FXML private TableColumn<Result, String> fileName;

    @FXML private TableColumn<Result, String> blockNumber;

    @FXML private TableColumn<Result, String> blockSize;

    @FXML private TableColumn<Result, String> associativity;

    @FXML private TableColumn<Result, String> replacement;

    @FXML private TableColumn<Result, String> writeHit;

    @FXML private TableColumn<Result, String> writeMiss;

    @FXML private TableColumn<Result, String> cacheReadHitRate;

    @FXML private TableColumn<Result, String> cacheWriteHitRate;

    @FXML private TableColumn<Result, String> cacheEvictions;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fileName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        blockNumber.setCellValueFactory(new PropertyValueFactory<>("blockNumber"));
        blockSize.setCellValueFactory(new PropertyValueFactory<>("blockSize"));
        associativity.setCellValueFactory(new PropertyValueFactory<>("associativity"));
        replacement.setCellValueFactory(new PropertyValueFactory<>("replacement"));
        writeHit.setCellValueFactory(new PropertyValueFactory<>("writeHit"));
        writeMiss.setCellValueFactory(new PropertyValueFactory<>("writeMiss"));
        cacheReadHitRate.setCellValueFactory(new PropertyValueFactory<>("cacheReadHitRate"));
        cacheWriteHitRate.setCellValueFactory(new PropertyValueFactory<>("cacheWriteHitRate"));
        cacheEvictions.setCellValueFactory(new PropertyValueFactory<>("cacheEvictions"));

        tableView.setItems(tableResult);
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
            Result result1 = controller.ProcessResults();
            AddToTable(result1);
        }
    }

    public void ExportToExcel(ActionEvent actionEvent) {
       Writer writer = null;
        try {
            File file = new File("/CacheResults.csv");
            writer = new BufferedWriter(new FileWriter(file));

            String text = "File Name" + "," + "Block Number" + "," + "Block Size" + "," + "Associativity" + "," + "Replacement"
                    + "," + "Write Hit" + "," + "Write Miss" + "," + "Cache Read Hit Rate" + "," + "Cache Write Hit Rate"
                    + "," + "Cache Evictions" + "\n";
            writer.write(text);
            for (Result results : tableResult) {
                text = results.getFileName() + "," + results.getBlockNumber() + "," + results.getBlockSize() + "," + results.getAssociativity()
                        + "," + results.getReplacement() + "," + results.getWriteHit() + "," + results.getWriteMiss() + "," + results.getCacheReadHitRate()
                        + "," + results.getCacheWriteHitRate() + "," + results.getCacheEvictions() + "\n";
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {

            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void AddToTable(Result result) {
        tableResult.add(result);
        tableView.setItems(tableResult);
        exportButton.setDisable(false);
    }
}