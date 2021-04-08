package student_player;

import boardgame.Move;
import boardgame.Player;
import boardgame.Board;
import boardgame.BoardState;

import pentago_twist.PentagoBoard;
import pentago_twist.PentagoPlayer;
import pentago_twist.RandomPentagoPlayer;
import pentago_twist.PentagoBoardState;

public class Quickplay {
    public static void main() {
        boolean gameOver = false;
        String name = "rand";

        //create 2 players
      
        RandomPentagoPlayer playerRand = new RandomPentagoPlayer();
        StudentPlayer playerStudent = new StudentPlayer();

        //PentagoBoard board = new PentagoBoard();
        PentagoBoardState boardState = new PentagoBoardState(); //REMEMMBER YOU MDAE CONSTRUCTOR PUBLIC


        //random player get a move
        Move randMove  = playerRand.chooseMove(boardState);
        
        //get a student player's move
        playerStudent.chooseMove(boardState);

        //clone the board and get necc info
        PentagoBoardState boardClone = (PentagoBoardState)boardState.clone();
        int turnPlayer = boardClone.getTurnPlayer();

        PentagoBoard boardPB = new PentagoBoard();
        //PentagoBoardState = boardPB.getBoardState();

        /*yeah you could create one state for the actual game board, then
         clone and pass it to your chooseMove() method, process the move on the game board,
          then switch and do the same thing with the other player*/

        //PentagoPlayer can also call .chooseMove
        //PentagoBoard board = playerRand.createBoard(); //returns new PentagoBoard? or not, just board    

        //player selects move
        //playerStudent.chooseMove(PentagoBoardState boardState)

        //Player playerRand = new RandomPentagoPlayer();
        //Player playerStudent = new StudentPlayer();


        //Player player;
        //int playerID;
        //Board board;
        //boolean gameOver = false;
    }
    /*
    public void selectMove(){

    }


    public Move chooseMove(PentagoBoardState boardState) {
        return boardState.getRandomMove();
    }

    //in pentago board state
    public Move selectRandomMove() {
        ArrayList<PentagoMove> moves = getAllLegalMoves();
        return moves.get(rand.nextInt(moves.size()));
    }

    */
}