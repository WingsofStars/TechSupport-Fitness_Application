package delware.apps.techsupport_scampermobile;


import delware.apps.techsupport_scampermobile.Screens.newUserScreen;

public class Profile {
    private int profileID;
    private String userName;
    private String password;
    private double Height;
    private double Weight;
    private int level;
    private int XP;

    public Profile() {profileID = -1;}
    //profile constructor
    public Profile(int profileID, String userName, String password, double height, double weight, int level, int XP) {
        this.profileID = profileID;
        this.userName = userName;
        this.password = password;
        this.Height = height;
        this.Weight = weight;
        this.level = level;
        this.XP = XP;
    }
    //Gives an ID to the profile and returns it also calling the database handler
    public static Profile Create(String userName, String password, double height, double weight, int level, int xp)
    {
        // add data to database
        int id = newUserScreen.db.addNewUser(userName, password, height, weight, level, xp);
        // create profile
        return new Profile(id, userName, password, height, weight, level, xp);
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

    public double getHeight() {
        return Height;
    }

    public void setHeight(double height) {
        Height = height;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double weight) {
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
