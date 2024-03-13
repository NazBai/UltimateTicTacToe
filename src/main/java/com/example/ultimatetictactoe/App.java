package com.example.ultimatetictactoe;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class App extends Application {

    private Center center;
    private UltimateBoard ultimateBoard;
    private BorderPane root;
    private LeftPanel leftPanel;

    @Override
    public void start(Stage stage) throws IOException {
        root = new BorderPane();
        Scene scene = new Scene(root, Constants.APP_WITH, Constants.APP_HIGTH);
        initRoot(root);
        stage.setTitle("Ultimate TicTacToe");
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                Board tmp = ultimateBoard.isAnyBoardIsSelected();

                if(tmp != null){
                    tmp.triger(keyEvent.getText());
                }
                else {
                    ultimateBoard.selectBoard(keyEvent.getText());
                }
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    public void initRoot(BorderPane root) throws FileNotFoundException {
        initInfoCenter(root);
        initBoard(root);
        inhitLeft(root);
    }

    public void inhitLeft(BorderPane root){
        leftPanel = new LeftPanel(ultimateBoard, root);
        root.setRight(leftPanel.getvBox());
    }

    public void initBoard(BorderPane root) throws FileNotFoundException {
        ultimateBoard = new UltimateBoard(center, root);
        root.setLeft(ultimateBoard.getMainGrid());
        //root.getChildren().add(board.getPane());
    }

    public void initInfoCenter(BorderPane root){
        this.center = new Center();
        root.setTop(center.getStackPane());
    }

    public void startNewGame() throws FileNotFoundException {
        initRoot(root);
    }


    public static void main(String[] args) {
        launch();
    }
}