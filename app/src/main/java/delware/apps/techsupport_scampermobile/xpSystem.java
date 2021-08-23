package delware.apps.techsupport_scampermobile;

public class xpSystem {
<<<<<<< HEAD
    //Temp Values, each index corresponds to the level
    //Todo real xp level values
    private final int[] levelValues = { 0, 25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 0};
=======
    //Max Level is 20, each index corresponds to levelup requirement, goes up by 50 each time
    private final int[] levelValues = { 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700, 750, 800, 850, 900, 950, 0};
>>>>>>> main

    private String format = "%d / %d | Level %d";

    public void xpCheck(int xpGain, Profile user) {
        int xpReq = levelValues[user.getLevel()];
        int xpAmount = xpGain + user.getXP();

<<<<<<< HEAD
        if(xpAmount > xpReq) {
=======
        if(user.getLevel() == 20) {
            MainActivity.TVXP.setText(String.format(format, "MAX", "MAX", user.getLevel() ));
        } else if(xpAmount > xpReq) {
>>>>>>> main
            xpRollOver(xpAmount, xpReq, user);
        }else if(xpAmount == xpReq) {
            user.setLevel(user.getLevel() + 1);
            user.setXP(0);
            MainActivity.TVXP.setText(String.format(format, 0, levelValues[user.getLevel()], user.getLevel() ));
        } else {
            user.setXP(xpAmount);
            MainActivity.TVXP.setText(String.format(format, xpAmount, xpReq, user.getLevel() ));
        }
        //Update database
        MainActivity.databaseHandler.updateUser(user);
    }

    private void xpRollOver(int totalXp, int reqXP, Profile user) {
        int remainderXP = totalXp - reqXP;
        user.setLevel(user.getLevel() + 1);
        user.setXP(remainderXP);
        MainActivity.TVXP.setText(String.format(format, remainderXP, levelValues[user.getLevel()], user.getLevel() ));
    }
}
