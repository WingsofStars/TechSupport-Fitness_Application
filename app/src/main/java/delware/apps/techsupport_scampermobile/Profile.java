package delware.apps.techsupport_scampermobile;


public class Profile {
    private int profileID;
    private String userName;
    private String password;
    private String Height;
    private String Weight;
    private int level;

    public Profile() {}

    public Profile(int profileID, String userName, String password, String Height, String Weight, int level) {
        this.profileID = profileID;
        this.userName = userName;
        this.password = password;
        this.Height = Height;
        this.Weight = Weight;
        this.level = level;
    }
}
