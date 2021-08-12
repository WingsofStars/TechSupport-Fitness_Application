package delware.apps.techsupport_scampermobile;

public class xpSystem {
    //Temp Values, each index corresponds to the level
    private final int[] levelValues = { 0, 25, 50, 75, 100};

    private String format = "%d / %d | Level %d";

    public void xpCheck(int xpGain, int currentXP, int userLevel) {
        int xpReq = levelValues[userLevel];
        int xpAmount = xpGain + currentXP;

        if(xpGain + currentXP > xpReq) {
            xpRollOver(xpAmount, xpReq);
        }else if(xpGain + currentXP == xpReq) {
            //Levels up user
            MainActivity.TV.setText(String.format(format, xpAmount, xpReq, userLevel ));
        } else {
            MainActivity.TV.setText(String.format(format, xpAmount, xpReq, userLevel ));
        }
    }

    private void xpRollOver(int totalXp, int reqXP) {
        int remainderXP = totalXp - reqXP;
        //Levels up user
        //Extra xp goes to next level
        //MainActivity.TV.setText(String.format(format, xpAmount, xpReq, userLevel ));
    }
}
