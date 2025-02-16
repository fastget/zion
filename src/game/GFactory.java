package game;

import java.util.Random;
import java.util.Vector;

import auth.User;
import utils.Utils;

public class GFactory{

    Vector<Game> gameQueue;

    public GFactory(){
        gameQueue = new Vector<Game>();
    }

    public Vector<Integer> getAllWaitGamesID(){
        Vector<Integer> gq = new Vector<Integer>();
        for (Game item: gameQueue){
            if (item.status == Utils.STATUS_WAITING){
                gq.add(item.getGameID());
            }
        }
        return gq;
    }

    public void joinGame(int gid, User u){
        for (Game item : gameQueue ){
            if (item.getGameStatus() == Utils.STATUS_WAITING && item.getGameID() == gid){
                Player p = new Player().setAccount(u);
                item.startGame(p);
                break;
            }
        }
    }

    public void joinRandomGame(User u){
        Random random = new Random();
        int randomIndex = random.nextInt(getAllWaitGamesID().size());
        int randomGameID= getAllWaitGamesID().get(randomIndex);
        joinGame(randomGameID, u);
    }

    // return the game id. how to choice ? with user id ? random in the waiting games ?
    public int choiceGame(int uid){
        for (Game item : gameQueue ){
            if (item.getGameStatus() == Utils.STATUS_WAITING){
                return item.getGameIDByPlayerUID(uid);
            }
        }
        return -1;
    }

    public int choiceGame(String uname){
        for (Game item : gameQueue ){
            if (item.getGameStatus() == Utils.STATUS_WAITING){
                return item.getGameIDByPlayerName(uname);
            }
        }
        return -1;
    }

    private int randomIndex(int b, int e){
        return (int) (Math.random() * (e - b + 1)) + b;
    }

    public int createGame(User u){
        if(u != null ){
            int[] colors = {Utils.WHITE, Utils.BLACK};
            int x = randomIndex(0, 1);
            Game g = new Game(new Player().setAccount(u).setPlayerColor(colors[x]));
            g.setGameID(gameQueue.size()+1);
            gameQueue.add(g);
            u.notify("WAIT");
            return g.getGameID();
        }
        return -1;
    }

    // return game id after create a new game by user. the user become a player of game at this time.
    public int createGame(User u, int playerColor){
        if(u != null ){
            Game g = new Game(new Player().setAccount(u).setPlayerColor(playerColor));
            g.setGameID(gameQueue.size()+1);
            gameQueue.add(g);
            u.notify("WAIT");
            return g.getGameID();
        }
        return -1;
    }
}
