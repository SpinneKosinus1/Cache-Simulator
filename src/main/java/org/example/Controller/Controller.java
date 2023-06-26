package org.example.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.example.Model.CacheData.Cache;
import org.example.Model.Model;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Controller {
    // Variables
    // Model
    private Model Model;

    // FilePath
    String filePath;

    @FXML
    private GridPane GridPane;

    @FXML
    private Button export;

    @FXML
    private TextField BlockNumber;

    @FXML
    private TextField BlockSize;

    @FXML
    private TextField Associativity;

    @FXML
    private ComboBox Replacement;

    @FXML
    private ComboBox WriteHit;

    @FXML
    private ComboBox WriteMiss;

    @FXML
    private TextField CacheRead;

    @FXML
    private TextField CacheWrite;

    @FXML
    private TextField CacheEviction;

    public void StartSimulation() {
        if (filePath != null) {
            Model = new Model(Integer.parseInt(BlockNumber.getText()), Integer.parseInt(BlockSize.getText()),
                    Integer.parseInt(Associativity.getText()), Replacement.getValue().toString(),
                    WriteHit.getValue().toString(), WriteMiss.getValue().toString());

            Cache cache = Model.StartSimulation(filePath);

            int temp = (int)(cache.GetCacheReadHitPercentage() * 100);
            double CacheReadHit = ((double) temp) / 100;

            temp = (int)(cache.GetCacheWriteHitPercentage() * 100);
            double CacheWriteHit = ((double) temp) / 100;

            CacheRead.setText(String.valueOf(CacheReadHit) + "%");
            CacheWrite.setText(String.valueOf(CacheWriteHit) + "%");
            CacheEviction.setText(String.valueOf(cache.GetCacheEvictions()));
            export.setDisable(false);
        }
    }

    public void LoadFile(ActionEvent actionEvent) {
        // get the file selected
        FileChooser FileChooser = new FileChooser();
        Window stage = GridPane.getScene().getWindow();
        FileChooser.setTitle("Load TraceFile");

        try {
            File file = FileChooser.showOpenDialog(stage);
            FileChooser.setInitialDirectory(file.getParentFile());
            filePath = file.getPath();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void ExportFile(ActionEvent actionEvent) {
        FileChooser FileChooser = new FileChooser();
        Window stage = GridPane.getScene().getWindow();
        FileChooser.setTitle("Save Results");
        FileChooser.setInitialFileName("Result");
        FileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text file", "*.txt"));

        try {
            String cacheReadHit = CacheRead.getText();
            String cacheWriteHit = CacheWrite.getText();
            String cacheEviction = CacheEviction.getText();

            String output = "Results\n" + "Cache Read Hit Rate in Percent: " + cacheReadHit + "\n"
                    + "Cache Write Hit Rate in Percent: " + cacheWriteHit + "\n" + "Cache Evictions: " + cacheEviction;

            File file = FileChooser.showSaveDialog(stage);
            FileChooser.setInitialDirectory(file.getParentFile());
            filePath = file.getPath();
            Files.writeString(Path.of(filePath), output, StandardCharsets.UTF_8);
        }
        catch (Exception ex) {

        }

    }
}