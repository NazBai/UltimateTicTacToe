package com.example.ultimatetictactoe;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class Board {
    private static int buttonIndex = 1;
    private static int boardIndex = 1;
    private int curentBoardIndex;
    private GridPane gridPane;
    private Button [][] board = new Button[3][3];
    private UltimateBoard ultimateBoard;
    private boolean endOfGame = false;
    private ImageView curentImageView;
    private Image curentImage;
    private Image imageX;
    private Image imageO;
    private Image imageV;
    private String winner = "1";
    private boolean isAvailable;
    private boolean isSlatmate = false;
    private int gridIndex1;
    private int gridIndex2;
    private boolean isSelected = false;

    public Board(UltimateBoard ultimateBoard, int gridIndex1, int gridIndex2){

        this.gridIndex1 = gridIndex1;
        this.gridIndex2 = gridIndex2;

        isAvailable = true;

        imageX = ultimateBoard.getImageX();
        imageO = ultimateBoard.getImageO();
        imageV = ultimateBoard.getImageV();
        curentImage = imageX;
        curentImageView = new ImageView(curentImage);
        curentImageView.setFitWidth(40);
        curentImageView.setFitHeight(40);

        this.ultimateBoard = ultimateBoard;

        if(boardIndex <= 9){
            curentBoardIndex = boardIndex;
            boardIndex++;
        } else {
          boardIndex = 1;
          curentBoardIndex = boardIndex;
          boardIndex++;
        }

        gridPane = fillGrid();
    }

    public GridPane fillGrid(){
        GridPane result = new GridPane();

        for (int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 3 ; j++) {

                Button button = new Button();

                if(buttonIndex <= 9){
                    button.setText("" + buttonIndex);
                    buttonIndex++;
                } else {
                    buttonIndex = 1;
                    button.setText("" + buttonIndex);
                    buttonIndex++;
                }


                button.setMinSize(50, 50);
                button.setAlignment(Pos.CENTER);
                ImageView empty = new ImageView(imageV);
                empty.setFitHeight(45);
                empty.setFitWidth(45);
                button.setGraphic(empty);
                button.setStyle("-fx-background-color: green; -fx-font-size: 1px Thoma");
                button.setContentDisplay(ContentDisplay.CENTER);
                button.setDisable(true);



                button.setOnAction( e -> {
                    if(Character.isDigit(winner.charAt(0)) && !ultimateBoard.isUltimateEnd()){

                        if(Character.isDigit(button.getText().charAt(0)) && !endOfGame){

                            int buttonIndex = Integer.parseInt(button.getText());

                            ImageView imageView = new ImageView(ultimateBoard.getCurrentImage());
                            imageView.setFitHeight(45);
                            imageView.setFitWidth(45);
                            button.setGraphic(imageView);

                            button.setText(ultimateBoard.getCurrentPlayer());

                            ultimateBoard.turnCounter++;
                            ultimateBoard.addRow(createData(buttonIndex, curentBoardIndex));

                            isWin();


                            if(endOfGame){
                                ultimateBoard.declareWinner(this);
                                ultimateBoard.checkForWinner();
                                if(!ultimateBoard.isUltimateEnd()){
                                    nextTurn(buttonIndex);
                                }
                            }else{
                                nextTurn(buttonIndex);
                            }

                        }
                    }
                    else {
                        ultimateBoard.declareWinner(this);
                    }


                });

                result.add(button, j, i);
                board[j][i] = button;
            }
        }

        return result;
    }

    public void isWin(){
        if(checkColumns() || checkRaws() || checkDiagonals()){
            endOfGame = true;
        } else if(checkSlatmate()){
            endOfGame = true;
            isSlatmate = true;
        }

    }

    public boolean checkColumns(){

        for (int i = 0; i < 3; i++) {
            if(board[0][i].getText().equals(board[1][i].getText()) && board[0][i].getText().equals(board[2][i].getText()) && !Character.isDigit(board[0][i].getText().charAt(0))){
                return true;
            }

        }

        return false;
    }

    public boolean checkRaws(){

        for (int i = 0; i < 3; i++) {
            if(board[i][0].getText().equals(board[i][1].getText()) && board[i][0].getText().equals(board[i][2].getText()) && !Character.isDigit(board[i][0].getText().charAt(0))){
                return true;
            }
        }

        return false;
    }

    public boolean checkDiagonals(){
        return (board[0][0].getText().equals(board[1][1].getText()) && board[0][0].getText().equals(board[2][2].getText()) && !Character.isDigit(board[0][0].getText().charAt(0)))
                || (board[0][2].getText().equals(board[1][1].getText()) && board[0][2].getText().equals(board[2][0].getText()) && !Character.isDigit(board[0][2].getText().charAt(0)));
    }

    public boolean checkSlatmate(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(Character.isDigit(board[i][j].getText().charAt(0))){
                    return false;
                }
            }
        }
        return true;
    }

    public void nextTurn(int buttonIndex){

        if(ultimateBoard.getCurrentPlayer().equals("X")){
            ultimateBoard.setCurrentPlayer("O");
            ultimateBoard.setCurrentImage(imageO);
        }else if(ultimateBoard.getCurrentPlayer().equals("O")){
            ultimateBoard.setCurrentPlayer("X");
            ultimateBoard.setCurrentImage(imageX);
        }

        ultimateBoard.checkAvailable(buttonIndex);


    }

    public void setAvailable(boolean isAvailable){
        this.isAvailable = isAvailable;

        if(isAvailable){
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j].setStyle("-fx-background-color: green; -fx-font-size: 1px Thoma");
                    board[i][j].setDisable(false);
                }
            }
        } else {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j].setStyle("-fx-background-color: red; -fx-font-size: 1px Thoma");
                    if(Character.isDigit(winner.charAt(0))){
                        board[i][j].setDisable(true);
                    }
                }
            }
        }
    }

    public void triger(String index){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(board[i][j].getText().equals(index)){
                    board[i][j].fire();
                    isSelected = false;
                    return;
                }
            }
        }
    }

    public String createData(int buttonIndex, int boardIndex){
        String boardInfo = "";
        String titleInfo = "";
        String playerInfo = ultimateBoard.getCurrentPlayer();
        String turn = "" + ultimateBoard.turnCounter;

        switch (buttonIndex){
            case 1:
                titleInfo = "NW";
                break;
            case 2:
                titleInfo = "N";
                break;
            case 3:
                titleInfo = "NE";
                break;
            case 4:
                titleInfo = "W";
                break;
            case 5:
                titleInfo = "C";
                break;
            case 6:
                titleInfo = "E";
                break;
            case 7:
                titleInfo = "SW";
                break;
            case 8:
                titleInfo = "S";
                break;
            case 9:
                titleInfo = "SE";
                break;
            default: titleInfo = "ERROR";
        }

        switch (boardIndex){
            case 1:
                boardInfo = "NW";
                break;
            case 2:
                boardInfo = "N";
                break;
            case 3:
                boardInfo = "NE";
                break;
            case 4:
                boardInfo = "W";
                break;
            case 5:
                boardInfo = "C";
                break;
            case 6:
                boardInfo = "E";
                break;
            case 7:
                boardInfo = "SW";
                break;
            case 8:
                boardInfo = "S";
                break;
            case 9:
                boardInfo = "SE";
                break;
            default: boardInfo = "ERROR";
        }

        return turn + "       " + boardInfo + "/" + titleInfo + "        " + playerInfo;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public String getWinner() {
        return winner;
    }

    public int getCurentBoardIndex() {
        return curentBoardIndex;
    }

    public int getGridIndex1() {
        return gridIndex1;
    }

    public int getGridIndex2() {
        return gridIndex2;
    }

    public void setWinner(String winner){
        this.winner = winner;
    }

    public boolean getIsSelected(){
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSlatmate() {
        return isSlatmate;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public Button[][] getBoard() {
        return board;
    }
}
