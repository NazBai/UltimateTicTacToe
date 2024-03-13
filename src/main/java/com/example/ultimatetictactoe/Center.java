package com.example.ultimatetictactoe;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Center {
    private StackPane stackPane;
    private Label label;

    public Center(){
        stackPane = new StackPane();
        stackPane.setMinSize(Constants.APP_WITH / 2, Constants.INFO_CENTER_HIGTH / 2);
        stackPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));;

        label = new Label();
        label.setText("Ultimate TicTacToe");
        label.setMinSize(Constants.APP_WITH, Constants.INFO_CENTER_HIGTH);
        label.setFont(Font.font(24));
        label.setAlignment(Pos.CENTER);
        label.setTranslateY(-20);

        stackPane.getChildren().add(label);


    }

    public StackPane getStackPane() {
        return stackPane;
    }


    public void updateMassage(String massage){
        this.label.setText(massage);
    }

}
