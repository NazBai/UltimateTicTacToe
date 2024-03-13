package com.example.ultimatetictactoe;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class LeftPanel {

    private String selectedDifficulty;
    private String selectedOpponent = "PvP";
    private String selectedPlayer = "X";

    private VBox vBox;
    private GridPane gridPane;
    private Label choseOpponent;
    private Label choseXO;
    private Label choseDifficulty;
    private RadioButton pve;
    private RadioButton pvp;
    private RadioButton X;
    private RadioButton O;
    private ComboBox<String> difficultyList;
    private ToggleGroup opponentGroup;
    private ToggleGroup playerGroupe;
    private Button button;
    private UltimateBoard ultimateBoard;
    private ListView<String> listView;
    //private ObservableList data = FXCollections.observableArrayList();

    public LeftPanel(UltimateBoard ultimateBoard, BorderPane root){

        this.ultimateBoard = ultimateBoard;

        gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        choseOpponent = new Label("Chose Opponent:");

        opponentGroup = new ToggleGroup();

        pve = new RadioButton("PvE");
        pve.setToggleGroup(opponentGroup);

        pvp = new RadioButton("PvP");
        pvp.setToggleGroup(opponentGroup);
        pvp.setSelected(true);

        opponentGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                RadioButton rb = (RadioButton)opponentGroup.getSelectedToggle();
                selectedOpponent = rb.getText();
            }
        });

        choseXO = new Label("Chose X or O:");
        playerGroupe = new ToggleGroup();
        X = new RadioButton("X");
        X.setToggleGroup(playerGroupe);
        X.setSelected(true);
        O = new RadioButton("O");
        O.setToggleGroup(playerGroupe);
        playerGroupe.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                RadioButton rb = (RadioButton) playerGroupe.getSelectedToggle();

                selectedPlayer = rb.getText();
            }
        });



        choseDifficulty = new Label("Chose difficulty:");

        difficultyList = new ComboBox<String>();
        difficultyList.getItems().addAll("Essy", "Medium", "Hard");
        difficultyList.valueProperty().addListener(new ChangeListener<String>() {

            @Override public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                System.out.println(t1);
                selectedDifficulty = t1;
            }
        }
        );

        button = new Button("Start");
        button.setMinSize(80, 50);
        button.setOnAction(e -> {
            ultimateBoard.setSelectedPlayer(selectedPlayer);
            ultimateBoard.setVsWho(selectedOpponent);
            vBox.getChildren().remove(listView);
            listView = new ListView<String>();
            listView.setPrefHeight(400);
            listView.setPrefWidth(150);
            ultimateBoard.sendList(listView);
            listView.getItems().add("Turn Position Player");
            vBox.getChildren().add(listView);
            ultimateBoard.newGame();
        });

        gridPane.add(choseOpponent, 0, 0);
        gridPane.add(pve, 1, 0);
        gridPane.add(pvp, 2, 0);
        gridPane.add(choseXO, 0, 1);
        gridPane.add(X, 1, 1);
        gridPane.add(O, 2, 1);
        gridPane.add(choseDifficulty, 0, 2);
        gridPane.add(difficultyList, 1, 2);
        gridPane.add(button, 0, 3);

        listView = new ListView<String>();
        listView.setPrefHeight(400);
        listView.setPrefWidth(150);
        ultimateBoard.sendList(listView);
        listView.getItems().add("Turn Position Player");

        vBox = new VBox();

        vBox.getChildren().add(gridPane);
        vBox.getChildren().add(listView);





    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public VBox getvBox() {
        return vBox;
    }
}
