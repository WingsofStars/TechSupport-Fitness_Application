package delware.apps.techsupport_scampermobile;

import java.util.ArrayList;

public class CalorieCalculator {

    private static class METCutoff
    {
        METCutoff(double c, double v){
            cutoff = c;
            value = v;
        }
        public double cutoff;
        public double value;
    }

    static Profile profile;// = MainActivity.databaseHandler.getUserByID(Integer.parseInt(MainActivity.currentID));
    static Tracking_Settings s = new Tracking_Settings();
    private static double METValue;
    private static double BMR;
    private static double weightKg;
    private static double heightCm;
    private static int age;
    private static String sex;

    private static ArrayList<METCutoff> walkrunMET = new ArrayList<>();

    public static void setProfile(Profile p)
    {
        profile = p;

        walkrunMET.add(new METCutoff(2.0, 2.0));
        walkrunMET.add(new METCutoff(3.0, 2.5));
        walkrunMET.add(new METCutoff(3.5, 4.5));
        walkrunMET.add(new METCutoff(4.5, 6.0));
        walkrunMET.add(new METCutoff(5, 8.3));
        walkrunMET.add(new METCutoff(6, 9.8));
        walkrunMET.add(new METCutoff(7, 11.0));
        walkrunMET.add(new METCutoff(8, 11.8));
        walkrunMET.add(new METCutoff(9, 12.8));
        walkrunMET.add(new METCutoff(10, 14.5));
        walkrunMET.add(new METCutoff(11, 16.0));
        walkrunMET.add(new METCutoff(12, 19.0));
        walkrunMET.add(new METCutoff(13, 19.8));
        walkrunMET.add(new METCutoff(14, 23.0));
        walkrunMET.add(new METCutoff(100, 24.0));

        weightKg = profile.getWeight() * 0.45359237;
        heightCm = profile.getHeight() * 2.54;
        age = profile.getAge();
        sex = profile.getGender();

    }

    //Equations for BMR
    //Women: 10 ⨉ weight (kg) + 6.25 ⨉ height (cm) – 5 ⨉ age (years) – 161
    //Men: 10 ⨉ weight (kg) + 6.25 ⨉ height (cm) – 5 ⨉ age (years) + 5.

    public static double calculateBMR(){
        if (profile.getGender().equals("Male")){
            BMR = ((10 * weightKg) + (6.25 * heightCm) - (5 * age) + 5);
        }else if (profile.getGender().equals("Female")){
            BMR = ((10 * weightKg) + (6.25 * heightCm) - (5 * age) - 161);
        }
        return BMR;
    }
    //use if Tracking settings has speed already
    public static double caloriesWalking(){
        //in meters per second to KPH
        double calories = ((s.currentSpeed * 3.6) * 1.17);
        return 0.0;
    }

    public static double getMETValue(double speedMPS){
        double speedMPH = speedMPS * 2.2369362920544;

        for(int i = 0; i < walkrunMET.size(); i++)
        {
            METCutoff cutoff = walkrunMET.get(i);
            if(speedMPH <= cutoff.cutoff)
                return cutoff.value;
        }

        //4.5mph is the high for walking
        return -1.0;
    }



}
