package delware.apps.techsupport_scampermobile;


public class Profile {
    private int profileID;
    private String userName;
    private String password;
    private String Height;
    private String Weight;
    private int level;
    private int XP;

    public Profile() {}
    //profile constructor
    public Profile(int profileID, String userName, String password, String Height, String Weight, int level, int XP) {
        this.profileID = profileID;
        this.userName = userName;
        this.password = password;
        this.Height = Height;
        this.Weight = Weight;
        this.level = level;
        this.XP = XP;
    }

    /*public int getProfileID() {
        return profileID;
    }

    public void setProfileID(int profileID) {
        this.profileID = profileID;
    }*/

    public int getUserID(){return profileID;}

    public void setUserID(int profileID){this.profileID = profileID;}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXP() {return XP;}

    public void setXP(int XP) {
        this.XP = XP;
    }
}
