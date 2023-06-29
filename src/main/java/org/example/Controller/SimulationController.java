package org.example.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.example.Model.Memory.Cache;
import org.example.Model.Model;
import org.example.Model.Result;

import java.io.File;


public class SimulationController {
    // FilePath
    private String filePath;
    private String fileName;

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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load TraceFile");
        Window stage = borderPane.getScene().getWindow();

        try {
            File file = fileChooser.showOpenDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile());
            filePath = file.getPath();
            fileName = file.getName();
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


            return new Result(fileName, BlockNumber.getText(), BlockSize.getText(), Associativity.getText(),
                    Replacement.getValue().toString(), WriteHit.getValue().toString(), WriteMiss.getValue().toString(),
                    cacheReadHit, cacheWriteHit, cacheEvictions);
        }
        return null;
    }
}
