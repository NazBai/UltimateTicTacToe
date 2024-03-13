package com.example.ultimatetictactoe;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class David {
    private String player;
    private boolean isDavidsTurn = false;
    private UltimateBoard ultimateBoard;


    public David(UltimateBoard ultimateBoard){
        this.ultimateBoard = ultimateBoard;
    }

    public void makeAMove(Board[][] boards){

        if(ultimateBoard.getCurrentPlayer().equals(player)){

            List<Board> list = new ArrayList<Board>();

            for (int i = 0; i <3 ; i++) {
                for (int j = 0; j < 3; j++) {
                    if(boards[i][j].isAvailable()){
                        list.add(boards[i][j]);
                    }
                }
            }

            int chousenBoard = (int) (Math.random() * list.size()) + 1;

            Board board = list.get(chousenBoard-1);

            List<Button> buttonList = new ArrayList<Button>();

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(Character.isDigit(board.getBoard()[i][j].getText().charAt(0))){
                        buttonList.add(board.getBoard()[i][j]);
                    }
                }

            }

            int chosenButton = (int) (Math.random() * buttonList.size()) + 1;

            buttonList.get(chosenButton-1).fire();
            try {
                wait(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
