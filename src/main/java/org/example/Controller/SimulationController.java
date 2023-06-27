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

    public Result ProcessResults() {
        if (filePath != null) {
            Model Model = new Model(Integer.parseInt(BlockNumber.getText()), Integer.parseInt(BlockSize.getText()),
                    Integer.parseInt(Associativity.getText()), Replacement.getValue().toString(),
                    WriteHit.getValue().toString(), WriteMiss.getValue().toString());

            Cache cache = Model.StartSimulation(filePath);

            int temp = (int) (cache.GetCacheReadHitPercentage() * 100);
            double CacheReadHit = ((double) temp) / 100;
            String cacheReadHit = CacheReadHit + "%";

            temp = (int) (cache.GetCacheWriteHitPercentage() * 100);
            double CacheWriteHit = ((double) temp) / 100;
            String cacheWriteHit = CacheWriteHit + "%";

            String cacheEvictions = String.valueOf(cache.GetCacheEvictions());

            System.out.println(cacheReadHit);
            System.out.println(cacheWriteHit);
            System.out.println(cacheEvictions);


            Result result = new Result("Test", BlockNumber.getText(), BlockSize.getText(), Associativity.getText(),
                    Replacement.getValue().toString(), WriteHit.getValue().toString(), WriteMiss.getValue().toString(),
                    cacheReadHit, cacheWriteHit, cacheEvictions);
            return result;
        }
        return null;
    }
}
