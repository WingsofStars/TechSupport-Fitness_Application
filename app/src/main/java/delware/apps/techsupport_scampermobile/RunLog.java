package delware.apps.techsupport_scampermobile;

public class RunLog {
        public float Distance;
        public int Hours;
        public int Minutes;
        public int calories;
        public float speed;
        public String date;
        public  String cardioType;
        public int UserId;


    public RunLog(float distance, int hours, int minutes, int calories, String date, String cardioType) {
        Distance = distance;
        Hours = hours;
        Minutes = minutes;
        this.calories = calories;;
        this.date = date;
        this.cardioType = cardioType;
        float minutePlaceHolder = (float) minutes;
        System.out.println(minutePlaceHolder);
        speed = distance / (hours + (minutePlaceHolder/60));
        System.out.println("Speed: " + speed);
    }

        public float getDistance() {
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

        public int getMinutes() {
            return Minutes;
        }

        public void setMinutes(int minutes) {
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

        public String getCardioType() {
        return cardioType;
    }

    public void setCardioType(String cardioType) {
        this.cardioType = cardioType;
    }
}
