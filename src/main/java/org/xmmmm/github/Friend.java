package org.xmmmm.github;

public class Friend {
    private long uin;
    private int friendFlag;
    private int categories;
    //info
    private int face;
    private int infoFlag;
    private String nick;
    //marknames
    private String markname;
    private int type;
    //vipinfo
    private int vipLevel;
    private int isVip;


    public long getUin() {
        return uin;
    }

    public void setUin(long uin) {
        this.uin = uin;
    }

    public int getFriendFlag() {
        return friendFlag;
    }

    public void setFriendFlag(int friendFlag) {
        this.friendFlag = friendFlag;
    }

    public int getCategories() {
        return categories;
    }

    public void setCategories(int categories) {
        this.categories = categories;
    }

    public int getFace() {
        return face;
    }

    public void setFace(int face) {
        this.face = face;
    }

    public int getInfoFlag() {
        return infoFlag;
    }

    public void setInfoFlag(int infoFlag) {
        this.infoFlag = infoFlag;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMarkname() {
        return markname;
    }

    public void setMarkname(String markname) {
        this.markname = markname;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "uin='" + uin + '\'' +
                ", friendFlag='" + friendFlag + '\'' +
                ", categories='" + categories + '\'' +
                ", face='" + face + '\'' +
                ", infoFlag='" + infoFlag + '\'' +
                ", nick='" + nick + '\'' +
                ", markname='" + markname + '\'' +
                ", type='" + type + '\'' +
                ", vipLevel='" + vipLevel + '\'' +
                ", isVip='" + isVip + '\'' +
                '}';
    }
    public static String hash2(long uin){
        String ptvfwebqq="";

        char[] ptb = new char[4];
        for (int i=0;i<ptvfwebqq.length();i++){
            int ptbIndex = i%4;
            ptb[ptbIndex] ^= ptvfwebqq.charAt(i);
        }
        String[] salt={"EC", "OK"};
        char[] uinByte=new char[4];
        uinByte[0] = (char) (((uin >> 24) & 0xFF) ^ salt[0].charAt(0));
        uinByte[1] = (char) (((uin >> 16) & 0xFF) ^ salt[0].charAt(1));
        uinByte[2] = (char) (((uin >> 8) & 0xFF) ^ salt[1].charAt(0));
        uinByte[3] = (char) ((uin & 0xFF) ^ salt[1].charAt(1));

        char[] result=new char[8];
        for (int i = 0; i <8 ; i++) {
            if(i%2==0){
                result[i] = ptb[i>>1];
            }else {
                result[i] = uinByte[i>>1];
            }
        }
        return byte2hex(result);
    }

    public static String byte2hex(char[] bytes){
        char[] hex={'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        String buf="";
        for (int i = 0; i < bytes.length; i++) {
            buf+= (hex[(bytes[i]>>4) & 0xF]);
            buf += (hex[bytes[i] & 0xF]);
        }
        return buf;
    }

}
