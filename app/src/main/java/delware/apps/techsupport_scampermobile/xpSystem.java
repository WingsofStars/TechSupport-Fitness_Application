package delware.apps.techsupport_scampermobile;

public class xpSystem {
    //Temp Values, each index corresponds to the level
    //Todo real xp level values
    private final int[] levelValues = { 0, 25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500};

    private String format = "%d / %d | Level %d";

    public void xpCheck(int xpGain, Profile user) {
        int xpReq = levelValues[user.getLevel()];
        int xpAmount = xpGain + user.getXP();

        if(xpAmount > xpReq) {
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
