package com.example.ultimatetictactoe;

import com.example.ultimatetictactoe.Board;
import com.example.ultimatetictactoe.Center;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UltimateBoard{

    private GridPane mainGrid;
    private Center center;
    private Board[][] allBoards = new Board[3][3];
    private Image imageX;
    private Image imageO;
    private Image imageV;
    private ImageView X;
    private ImageView O;
    private ImageView V;
    private String currentPlayer = "X";
    private Image currentImage;
    private String ultimateWinner;
    private boolean ultimateEnd = false;
    private ListView<String> listView;
    public int turnCounter = 0;
    public String player;
    private String vsWho;
    private BorderPane root;
    private boolean PvE = false;
    private David david;

    public UltimateBoard(Center center, BorderPane root) throws FileNotFoundException {
        david = new David(this);

        this.root = root;

        FileInputStream fis1 = new FileInputStream("src/main/resources/Images/Cross.png");
        this.imageX = new Image(fis1);
        X = new ImageView(imageX);
        X.setFitWidth(45);
        X.setFitHeight(45);

        FileInputStream fis2 = new FileInputStream("src/main/resources/Images/Circle.png");
        this.imageO = new Image(fis2);
        O = new ImageView(imageO);
        O.setFitWidth(45);
        O.setFitHeight(45);


        FileInputStream fis3 = new FileInputStream("src/main/resources/Images/Empty.png");
        this.imageV = new Image(fis3);
        V = new ImageView(imageV);
        V.setFitWidth(45);
        V.setFitHeight(45);

        currentImage = imageX;

        this.center = center;
        mainGrid = fillMainGrid();

        //mainGrid.setGridLinesVisible(true);
    }

    public void newGame(){
        root.getChildren().remove(mainGrid);
        mainGrid = fillMainGrid();
        root.setLeft(mainGrid);
        turnCounter = 0;
        currentImage = imageX;
        currentPlayer = "X";
        turnOnTitles();
        ultimateEnd = false;

        if(player.equals("X")){
            david.setPlayer("O");
        } else {
            david.setPlayer("X");
        }

        if(isPvE()){
            david.makeAMove(allBoards);
        }
    }

    public GridPane fillMainGrid(){
        GridPane result = new GridPane();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                Board board = new Board(this, j, i);
                result.add(board.getGridPane(), j, i);
                allBoards[j][i] = board;
            }
        }

        result.setVgap(10);
        result.setHgap(10);

        return result;

    }

    public Board isAnyBoardIsSelected(){
        Board result;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(allBoards[i][j].getIsSelected()){
                    result = allBoards[i][j];
                    return result;
                }
            }
        }

        return null;
    }

    public void turnOnTitles(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                allBoards[i][j].setAvailable(true);
            }
        }

    }


    public void declareWinner(Board board){


        ImageView winnerWiew;

        mainGrid.getChildren().remove(board.getGridPane());


        if(board.isSlatmate()){
            winnerWiew = new ImageView(imageV);
            board.setWinner(" ");
        }
        else if(currentPlayer.equals("X")){
            winnerWiew = new ImageView(imageX);
            board.setWinner("X");
        }
        else {
            winnerWiew = new ImageView(imageO);
            board.setWinner("O");
        }

        winnerWiew.setFitWidth(140);
        winnerWiew.setFitHeight(140);

        Button button = new Button();
        button.setMinSize(140, 140);
        button.setGraphic(winnerWiew);
        button.setOnAction( e -> {
            mainGrid.getChildren().remove(button);
            mainGrid.add(board.getGridPane(), board.getGridIndex1(), board.getGridIndex2());
        });
        mainGrid.add(button, board.getGridIndex1(), board.getGridIndex2());

    }

    public void checkAvailable(int buttonIndex){

        boolean stop = false;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(allBoards[i][j].getCurentBoardIndex() == buttonIndex && Character.isDigit(allBoards[i][j].getWinner().charAt(0))){
                    allBoards[i][j].setAvailable(true);
                } else if (allBoards[i][j].getCurentBoardIndex() == buttonIndex && !Character.isDigit(allBoards[i][j].getWinner().charAt(0))){
                    for (int k = 0; k < 3; k++) {
                        for (int l = 0; l < 3; l++) {
                            if(Character.isDigit(allBoards[k][l].getWinner().charAt(0))){
                                allBoards[k][l].setAvailable(true);
                            }
                        }
                    }
                    stop = true;
                } else if (!stop){
                    allBoards[i][j].setAvailable(false);
                }
            }

        }

        if(isPvE()){
            davidMakeAMove();
        }

    }


    public void checkForWinner(){
        if(checkColumns() || checkRaws() || checkDiagonals()){
            ultimateWinner = getCurrentPlayer();
            ultimateEnd = true;
            declareUltimateWinner(currentPlayer);
        } else if (checkSlatmate()){
            ultimateWinner = " ";
            ultimateEnd = true;
            declareUltimateWinner(" ");
        }

    }

    public boolean checkColumns(){

        for (int i = 0; i < 3; i++) {
            if(allBoards[0][i].getWinner().equals(allBoards[1][i].getWinner()) && allBoards[0][i].getWinner().equals(allBoards[2][i].getWinner()) && !Character.isDigit(allBoards[0][i].getWinner().charAt(0))){
                return true;
            }
        }
        return false;
    }

    public boolean checkRaws(){

        for (int i = 0; i < 3; i++) {
            if(allBoards[i][0].getWinner().equals(allBoards[i][1].getWinner()) && allBoards[i][0].getWinner().equals(allBoards[i][2].getWinner()) && !Character.isDigit(allBoards[i][0].getWinner().charAt(0))){
                return true;
            }

        }

        return false;
    }

    public boolean checkDiagonals(){
        return (allBoards[0][0].getWinner().equals(allBoards[1][1].getWinner()) && allBoards[0][0].getWinner().equals(allBoards[2][2].getWinner()) && !Character.isDigit(allBoards[0][0].getWinner().charAt(0)))
                || (allBoards[0][2].getWinner().equals(allBoards[1][1].getWinner()) && allBoards[0][2].getWinner().equals(allBoards[2][0].getWinner()) && !Character.isDigit(allBoards[0][2].getWinner().charAt(0)));
    }

    public boolean checkSlatmate(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(Character.isDigit(allBoards[i][j].getWinner().charAt(0))){
                    return false;
                }
            }
        }
        return true;
    }

    public void declareUltimateWinner(String ultimateWinner){
        if(ultimateWinner.equals(" ")){
            center.updateMassage("Even");
            disableAllTitles();
        }else{
            center.updateMassage("Player " + currentPlayer + " is ultimate winner");
            disableAllTitles();
        }

    }

    public void disableAllTitles(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                allBoards[i][j].setAvailable(false);
            }
        }
    }

    public void selectBoard(String index){
        if(Character.isDigit(index.charAt(0))){
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(allBoards[i][j].getCurentBoardIndex() == Integer.parseInt(index)){
                        allBoards[i][j].setSelected(true);
                    }
                }
            }
        }

    }

    public void sendList(ListView<String> listView){
        this.listView = listView;
    }

    public void addRow(String data){
        listView.getItems().add(data);
    }

    public void davidMakeAMove(){
        david.makeAMove(allBoards);
    }


    public GridPane getMainGrid() {
        return mainGrid;
    }



    public Center getCenter() {
        return center;
    }

    public ImageView getO() {
        return O;
    }

    public ImageView getV() {
        return V;
    }

    public ImageView getX() {
        return X;
    }

    public Image getImageO() {
        return imageO;
    }

    public Image getImageV() {
        return imageV;
    }

    public Image getImageX() {
        return imageX;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String curentPlayer) {
        this.currentPlayer = curentPlayer;
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }

    public boolean isUltimateEnd() {
        return ultimateEnd;
    }

    public void setSelectedPlayer(String player){
        this.player = player;
    }

    public void setVsWho(String s){
        if(s.equals("PvE")){
            PvE = true;
        }
        vsWho = s;
    }

    public boolean isPvE() {
        return PvE;
    }

    public String getPlayer(){
        return  player;
    }
}