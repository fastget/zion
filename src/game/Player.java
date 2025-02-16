package game;

import java.util.Vector;

import auth.User;
import utils.Utils;

public class Player {
    private User account;
    private Vector<Stone> stones;
    private int color; //player color
    private boolean selected;

    public User getAccount(){return this.account;}
    public Vector<Stone> getStones(){return this.stones;}
    public boolean getSelected(){return this.selected;}
    public int getPlayerColor(){return this.color;}

    public Player setSelected(boolean flag){this.selected = flag; return this;}
    public Player setPlayerColor(int color){this.color = color; return this;}
    public Player setAccount(User u){if (u != null){this.account = u;} return this;}

    public Player(){
        account = null;
        color = Utils.EMPTY;
        selected = false;
        stones = new Vector<Stone>();
    }
}