package b2_2;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopUpWindows {

	public void ShowErrorPopWindow(String text) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        
        Label error = new Label();
        error.setText(text);
        Label empty = new Label();
        empty.setText(" ️");
       
        Button closeButton = new Button("Close");
        closeButton.setOnMouseClicked(event -> {
            stage.close();
        });
        
        VBox vBox = new VBox();
        vBox.getChildren().addAll(error, empty,closeButton);
        
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox,300,150);
        stage.setScene(scene);
        stage.setTitle("Attention Please! ");
        stage.show();
        stage.setResizable(false);
	}
}
