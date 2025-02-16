package auth;

import utils.Utils;

abstract class Visitor {

    private Object token;
    private long timestemp;

    public Object getToken(){return this.token;}
    public boolean setToken(Object token){
        if ((token instanceof Object)){
            this.token = token;
            return true;
        }
        return false;
    }

    public long getTimeStemp(){return this.timestemp;}
    public boolean setTimeStemp(long ts){
        if(ts > 0){
            this.timestemp = ts;
            return true;
        }
        return false;
    }

    public boolean checkTokenExpire(){
        long t = this.getTimeStemp();
        if (System.currentTimeMillis() - t > Utils.TIME_OUT){
            return true;
        }
        return false;
    }

    public abstract void notify(String msg);
}

public class User extends Visitor {
    private final String name;
    private final String passwd;
    private final String phone;
    private final int role;
    private int uid = 0;
    private boolean online;
    private boolean blocked;

    public int getUID(){return this.uid;}
    public String getName(){return this.name;}
    public String getPassword(){return this.passwd;}
    public String getPhoneNumber(){return this.phone;}
    public int getRole(){return this.role;}
    public boolean getOnline(){return this.online;}
    public boolean getBlockStatus(){return this.blocked;}
    public User setUID(int uid){if(uid > 0){this.uid = uid;}return this;}
    public User setOnline(boolean online){this.online = online; return this;}
    public User setBlocked(boolean blocked){this.blocked= blocked; return this;}

    public static class Builder {
        private final String name;
        private final String passwd;

        private int uid;
        private String phone;
        private int role=0;
        private boolean online=false;
        private boolean blocked=false;

        public Builder(String name, String passwd){
            this.name = name;
            this.passwd = passwd;
        }

        public Builder setUID(int uid){
            this.uid= uid;
            return this;
        }

        public Builder setPhoneNumber(String num){
            if ((num instanceof String) && num.length() == Utils.CHINA_MOBILE_PHONE_NUM_LEN){
                this.phone = num;
            }
            return this;
        }

        public Builder setRole(int r){
            this.role = r;
            return this;
        }

        public Builder setOnline(boolean online){
            this.online = online;
            return this;
        }

        public Builder setBlockStatus(boolean blocked){
            this.blocked = blocked;
            return this;
        }

        public synchronized User build(){
            return new User(this);
        }
    }
    
    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", name=" + name +
                ", passwd=" + passwd+
                ", phone=" + phone+
                ", role=" + role+
                ", online=" + online+
                ", blocked=" + blocked+
                "}";
    }

    private User(User.Builder builder) {
        uid = builder.uid;
        name= builder.name;
        passwd= builder.passwd;
        phone= builder.phone;
        role= builder.role;
        online = builder.online;
        blocked= builder.blocked;
    }

    @Override
    public void notify(String msg){
        switch(msg){
            case "OK":
                System.out.println("I am ready, game is begin..");
                break;
            case "WAIT":
                System.out.println("Wait for the other player to start this game.");
                break;
            default:
                System.out.println(msg);
                break;
        }
    }
}