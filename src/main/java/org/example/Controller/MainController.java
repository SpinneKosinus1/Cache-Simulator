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
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.example.Model.Result;
import javafx.stage.FileChooser.ExtensionFilter;

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
        cacheReadHitRate.setCellValueFactory(new PropertyValueFactory<>("cacheReadMissRate")); // Cache Misses (false value name)
        cacheWriteHitRate.setCellValueFactory(new PropertyValueFactory<>("cacheWriteMissRate")); // Cache Misses (false value name)
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
            if (result1 != null) {
                AddToTable(result1);
            }
        }
    }

    public void ExportToExcel(ActionEvent actionEvent) {
        Writer writer = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Comma-separated values (CSV) file", "*.csv"));
        fileChooser.setInitialFileName("Results.csv");
        Window stage = borderPane.getScene().getWindow();
        fileChooser.setTitle("Save Results");

        try {
            File file = fileChooser.showSaveDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile());
            writer = new BufferedWriter(new FileWriter(file));

            String text = "File Name" + ";" + "Block Number" + ";" + "Block Size" + ";" + "Associativity" + ";" + "Replacement"
                    + ";" + "Write Hit" + ";" + "Write Miss" + ";" + "Cache Read Miss Rate" + ";" + "Cache Write Miss Rate"
                    + ";" + "Cache Evictions" + "\n";
            writer.write(text);
            for (Result results : tableResult) {
                text = results.getFileName() + ";" + results.getBlockNumber() + ";" + results.getBlockSize() + ";" + results.getAssociativity()
                        + ";" + results.getReplacement() + ";" + results.getWriteHit() + ";" + results.getWriteMiss() + ";" + results.getCacheReadMissRate()
                        + ";" + results.getCacheWriteMissRate() + ";" + results.getCacheEvictions() + "\n";
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