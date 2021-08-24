package delware.apps.techsupport_scampermobile;

public class RunLog {
        public float Distance;
        public int Hours;
        public float Minutes;
        public int calories;
        public float speed;
        public String date;
        public  String cardioType;
        public int UserId;


    public RunLog(float distance, int hours, float minutes, int calories, String date, String cardioType) {
        Distance = distance;
        Hours = hours;
        Minutes = minutes;
        this.calories = calories;;
        this.date = date;
        this.cardioType = cardioType;
        speed = distance / (hours + (minutes/60));
    }

<<<<<<< HEAD
        public float getDistance() {
=======
    public float getDistance() {
>>>>>>> main
            return Distance;
        }

        public void setDistance(float distance) {
            Distance = distance;
        }

        public int getHours() {
            return Hours;
        }

        public void setHours(int hours) {
            Hours = hours;
        }

        public float getMinutes() {
            return Minutes;
        }

        public void setMinutes(float minutes) {
            Minutes = minutes;
        }

        public int getCalories() {
            return calories;
        }

        public void setCalories(int calories) {
            this.calories = calories;
        }

        public float getSpeed() {
            return speed;
        }

        public void setSpeed(float speed) {
            this.speed = speed;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

<<<<<<< HEAD
        public String getCardioType() {
        return cardioType;
    }

        public void setCardioType(String cardioType) {
=======
    public String getCardioType() {
        return cardioType;
    }

    public void setCardioType(String cardioType) {
>>>>>>> main
        this.cardioType = cardioType;
    }
}
