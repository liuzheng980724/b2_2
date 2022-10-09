package b2_2;

import java.util.function.UnaryOperator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopUpWindows {

	private int[] minAndMaxNum = new int[2];
	private boolean inputOkDone = false;
	
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
	
	public void ShowAboutMe() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        
        Label copyright = new Label();
        copyright.setText("© Copyright 2022 B2-2. ️");
        Label version = new Label();
        version.setText("Version: 1.2");
        Label empty = new Label();
        empty.setText(" ️");
       
        Button closeButton = new Button("Close");
        closeButton.setOnMouseClicked(event -> {
            stage.close();
        });
        
        VBox vBox = new VBox();
        vBox.getChildren().addAll(copyright,version,empty,closeButton);
        
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox,400,200);
        stage.setScene(scene);
        stage.setTitle("About Me!");
        stage.show();
        stage.setResizable(false);
	}
	
	public int[] changeMaxAndMinNum() {
	while(true) {
		if(inputOkDone) {
			return minAndMaxNum;
		} else {
			ShowAndChangeNum();
		}
	}
	}
	
	public void ShowAndChangeNum() {		
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        
        Label minLabel = new Label();
        minLabel.setText("Min Num: ");
        Label empty = new Label();
        empty.setText(" ️");
        Label maxLabel = new Label();
        maxLabel.setText("Max Num: ");
        
        TextField minInput = new TextField();
        TextField maxInput = new TextField();
        UnaryOperator<Change> filter = change -> {	//Filter allow input numbers.
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change;
            }
            
            return null;
        };
        TextFormatter<String> minInputFormatter = new TextFormatter<>(filter);
        TextFormatter<String> maxInputFormatter = new TextFormatter<>(filter);
        minInput.setTextFormatter(minInputFormatter);	//Only allow input numbers.
        maxInput.setTextFormatter(maxInputFormatter);	//Only allow input numbers.
        
        Button closeButton = new Button("Close");
        closeButton.setOnMouseClicked(event -> {
    		inputOkDone = true;
            stage.close();
        });
        
        Button okButton = new Button("OK");
        okButton.setOnMouseClicked(event -> {
        	if(!minInput.getText().isBlank() & !maxInput.getText().isBlank()) {
        		int minNumber = Integer.parseInt(minInput.getText());
        		int maxNumber = Integer.parseInt(maxInput.getText());
        		System.out.println(minNumber);	//Test code.
        		System.out.println(maxNumber);//Test Code.
        		minAndMaxNum[0] = minNumber;
        		minAndMaxNum[1] = maxNumber;
        	} else {
            	ShowErrorPopWindow("Please Check!! Some fields are blank.");
        	}
    		inputOkDone = true;
        	stage.close();
        });
        
        HBox firstLine = new HBox();
        firstLine.getChildren().addAll(minLabel, empty, minInput);
        firstLine.setPadding(new Insets(15, 15, 15, 15));
        firstLine.setAlignment(Pos.CENTER);
        HBox secondLine = new HBox();
        secondLine.getChildren().addAll(maxLabel, empty, maxInput);
        secondLine.setPadding(new Insets(15, 15, 15, 15));
        secondLine.setAlignment(Pos.CENTER);
        HBox thirdLine = new HBox();
        thirdLine.getChildren().addAll(okButton, empty, closeButton);
        thirdLine.setAlignment(Pos.CENTER);
        thirdLine.setPadding(new Insets(15, 15, 15, 15));
        
        VBox vBox = new VBox();
        vBox.getChildren().addAll(firstLine, secondLine, thirdLine);
        vBox.setPadding(new Insets(40, 15, 15, 15));
        
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox,350,200);
        stage.setScene(scene);
        stage.setTitle("Change Max and Min number");
        stage.showAndWait();
        stage.setResizable(false);
	}
}
