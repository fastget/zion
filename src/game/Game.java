package game;

import java.util.Vector;

import utils.Utils;

public class Game{

    private int gid;
    private int[][] board;
    private Vector<Player> players;
    private boolean pass;
    public int status;

   private boolean isValidMove(int row, int col) {
        return board[col][row] == 0; // Placeholder for valid move check
    }

    private void actionStoneAt(int uid, int row, int col){
        for (Player item: players){
            if (uid == item.getAccount().getUID() && item.getSelected()){
                Stone s = null;
                if (!pass){
                    board[col][row] = item.getPlayerColor();
                    s = new Stone().setStoneCol(col, Utils.BOARDSIZE)
                    .setStoneRow(row, Utils.BOARDSIZE).setStoneStep(item.getStones().size());
                }else{
                    s = new Stone().setStoneStep(item.getStones().size()); 
                }
                if(s != null){item.getStones().add(s);} 
                item.setSelected(false);
            }else if( uid != item.getAccount().getUID() && !item.getSelected()){
                item.setSelected(true);
            }
        }
    }

    private int eraseStoneAt(int row, int col) {
        if(row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
            return Utils.EMPTY;
        }
        return board[col][row]=0;
    }

    public int getGameID(){return gid;}
    public Game setGameID(int gid){if(gid > 0){this.gid = gid;} return this;}
    public Game(Player p){
        players = new Vector<Player>(2);
        players.add(p);
        board = new int[Utils.BOARDSIZE+1][Utils.BOARDSIZE+1];
    }

    public void startGame(Player p){
        if (players.size() == 1){
            int color = players.lastElement().getPlayerColor();
            int otherColor = (color == Utils.BLACK ? Utils.WHITE : Utils.BLACK);
            p.setPlayerColor(otherColor);
            players.add(p);
            changeGameStatus(Utils.STATUS_PLAYING);
            for (Player item : players){
                // black player first in this game.
                if (item.getPlayerColor() == Utils.BLACK){
                    item.setSelected(true);
                }
                item.getAccount().notify("OK");
            }
        }
    }

    public void placeStone(int uid,int row, int col) {
        if (isValidMove(row, col)) {
            actionStoneAt(uid, row, col);
       }
    }

    public void undoMove(int uid, int row, int col){
        // remove stone in player records and erase stone from board with row and col
        // if i do this , did i need remove the other player's last record too ?
        for (Player item : players){
            if (uid == item.getAccount().getUID()){
                item.getStones().removeLast();
            }
        }
        eraseStoneAt(row, col);
    }

    public int getStoneAt(int row, int col) {
        if(row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
            return Utils.EMPTY;
        }
        return board[col][row];
    }

   public void resetGame() {
        board = new int[Utils.BOARDSIZE+1][Utils.BOARDSIZE+1];
        this.status = Utils.STATUS_WAITING;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getGameStatus() { return this.status;}
    public void changeGameStatus(int status){
        if ( status >= Utils.STATUS_WAITING && status <= Utils.STATUS_FINISH){
            this.status = status;
        }
    }

    public int getGameIDByPlayerName(String name){
        for (Player item : players){
            try {
                if (item.getAccount().getName().equals(name)){
                    return this.gid;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
       }
        return Utils.EMPTY;
    }

    public int getGameIDByPlayerUID(int uid){
        for (Player item : players){
            try {
                if (item.getAccount().getUID() == uid){
                    return this.gid;
                }
            } catch (Exception e) {
                // log for debug ?
                System.out.println(e);
            }
       }
       return Utils.EMPTY;
    }
}