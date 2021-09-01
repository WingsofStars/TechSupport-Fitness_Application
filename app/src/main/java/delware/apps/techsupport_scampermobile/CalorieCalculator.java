package delware.apps.techsupport_scampermobile;

import java.util.ArrayList;

import delware.apps.techsupport_scampermobile.Screens.trackingScreen;

public class CalorieCalculator {
    public delware.apps.techsupport_scampermobile.Screens.trackingScreen trackingScreen = new trackingScreen();
    //used to assign a MET value via speed
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

        //weightKg = profile.getWeight() * 0.45359237;
        heightCm = profile.getHeight() * 2.54;
        age = profile.getAge();
        sex = profile.getGender();

        calculateBMR();

    }

    //Equations for BMR
    //Women: 10 ⨉ weight (kg) + 6.25 ⨉ height (cm) – 5 ⨉ age (years) – 161
    //Men: 10 ⨉ weight (kg) + 6.25 ⨉ height (cm) – 5 ⨉ age (years) + 5.

    public static double calculateBMR(){
        if (profile.getGender().equals("Male")){
            BMR = (88.362 + (13.397 * weightKg) + (4.799 * heightCm) - (5.677 * age));
        }else if (profile.getGender().equals("Female")){
            BMR = 447.593 + (9.247 * weightKg) + (3.098 * heightCm) - (4.330 * age);
        }
        return BMR;
    }
    //Call this to get the total estimated calories burned
    public double caloriesBurned(double speed){
        double calories = ((getMETValue(speed) * BMR)/24);
        System.out.println("Calories: " + calories);
        return calories;
    }


    private double getMETValue(double speedMPH){

        for(int i = 0; i < walkrunMET.size(); i++)
        {
            METCutoff cutoff = walkrunMET.get(i);
            if(speedMPH <= cutoff.cutoff)
                return cutoff.value;
        }


        return -1.0;
    }



}
