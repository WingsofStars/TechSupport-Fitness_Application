package delware.apps.techsupport_scampermobile;


import delware.apps.techsupport_scampermobile.Screens.newUserScreen;

public class Profile {
    private int profileID;
    private String userName;
    private String password;
    private String salt;
    private double Height;
    private double Weight;
    private int age;
    private String gender;
    private int level;
    private int XP;

    public Profile() {profileID = -1;}

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    //profile constructor
    public Profile(int profileID, String userName, String password, String salt, double height, double weight, int age, String gender, int level, int XP) {
        this.profileID = profileID;
        this.userName = userName;
        this.password = password;
        this.salt = salt;
        this.Height = height;
        this.Weight = weight;
        this.age = age;
        this.gender = gender;
        this.level = level;
        this.XP = XP;
    }
    //Gives an ID to the profile and returns it also calling the database handler
    public static Profile Create(String userName, String password, String salt, double height, double weight, int age, String gender, int level, int xp)
    {
        // add data to database
        int id = newUserScreen.db.addNewUser(userName, password, salt, height, weight, age, gender, level, xp);
        // create profile
        return new Profile(id, userName, password,salt, height, weight, age, gender, level, xp);
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

    public int getAge(){return age;}

    public void setAge(int age){this.age = age;}

    public String getGender(){return gender;}

    public void setGender(String gender){this.gender = gender;}

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
