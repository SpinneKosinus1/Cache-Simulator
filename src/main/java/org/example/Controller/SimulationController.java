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

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;


public class SimulationController {
    // FilePath
    private String filePath;

    @FXML
    private DialogPane borderPane;

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

    public void LoadFile(ActionEvent actionEvent) {
        // get the file selected
        FileChooser FileChooser = new FileChooser();
        Window stage = borderPane.getScene().getWindow();
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

    public void ProcessResults() {
        if (filePath != null) {
            Model Model = new Model(Integer.parseInt(BlockNumber.getText()), Integer.parseInt(BlockSize.getText()),
                    Integer.parseInt(Associativity.getText()), Replacement.getValue().toString(),
                    WriteHit.getValue().toString(), WriteMiss.getValue().toString());

            Cache cache = Model.StartSimulation(filePath);

            int temp = (int) (cache.GetCacheReadHitPercentage() * 100);
            double CacheReadHit = ((double) temp) / 100;

            temp = (int) (cache.GetCacheWriteHitPercentage() * 100);
            double CacheWriteHit = ((double) temp) / 100;

            System.out.println(String.valueOf(CacheReadHit) + "%");
            System.out.println(String.valueOf(CacheWriteHit) + "%");
            System.out.println(String.valueOf(cache.GetCacheEvictions()));
        }
    }
}
