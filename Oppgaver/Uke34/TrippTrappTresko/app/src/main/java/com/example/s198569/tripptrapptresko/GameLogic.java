package com.example.s198569.tripptrapptresko;

public class GameLogic {

    enum State{Blank, X, O};

    int n = 3;
    State[][] board = new State[n][n];
    int moveCount;

    public boolean move(int x, int y, State s){

        if(board[x][y] == State.Blank){
            board[x][y] = s;
        }

        board[x][y] = s;
        moveCount++;

        if(moveCount > 3) {
            //check col
            for (int i = 0; i < n; i++) {
                if (board[x][i] != s)
                    break;
                if (i == n - 1) {
                    return true;
                }
            }

            //check row
            for (int i = 0; i < n; i++) {
                if (board[i][y] != s)
                    break;
                if (i == n - 1) {
                    return true;
                }
            }

            //check diag
            if (x == y) {
                for (int i = 0; i < n; i++) {
                    if (board[i][i] != s)
                        break;
                    if (i == n - 1) {
                        return true;
                    }
                }
            }

            //check anti diag
            for (int i = 0; i < n; i++) {
                if (board[i][(n - 1) - i] != s)
                    break;
                if (i == n - 1) {
                    return true;
                }
            }

            //check draw
            if (moveCount == (n ^ 2 - 1)) {
                return true;
            }
        }

        return false;
    }
}