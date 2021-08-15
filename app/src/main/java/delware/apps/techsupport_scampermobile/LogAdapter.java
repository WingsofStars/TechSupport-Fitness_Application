package delware.apps.techsupport_scampermobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class LogAdapter extends ArrayAdapter<RunLog> { // adapter so our ListView can us the customized layout
    private Context mcontext;
    int mresource;

    public LogAdapter(@NonNull Context context, int resource, @NonNull ArrayList<RunLog> objects) {
        super(context, resource, objects);
        this.mcontext = context;
        this.mresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String date  = getItem(position).getDate();
        float distance  = getItem(position).getDistance();
        int hours  = getItem(position).getHours();
        float minutes  = getItem(position).getMinutes();
        int calories  = getItem(position).getCalories();
        String type  = getItem(position).getCardioType();


        RunLog runlog = new RunLog(distance, hours, minutes, calories, date, type);

        LayoutInflater inflater = LayoutInflater.from(mcontext);
        convertView = inflater.inflate(mresource, parent,false);

        TextView Distance = convertView.findViewById(R.id.distance2);
        TextView Date = convertView.findViewById(R.id.date);
        TextView Time = convertView.findViewById(R.id.time1);
        TextView Calories = convertView.findViewById(R.id.calories1);
        TextView Speed = convertView.findViewById(R.id.speed1);
        TextView Type = convertView.findViewById(R.id.cardiotype1);

        Date.setText(String.valueOf(date));
        Time.setText(String.format("%.2f",hours + minutes/60) + " Hours");
        Distance.setText(String.valueOf(distance) + " Miles");
        Speed.setText(String.format("%.2f",distance/(Float.valueOf(hours)+(minutes/60))) + " MPH");
        Calories.setText(String.valueOf(calories) + " Calories");
        Type.setText(String.valueOf(type));


        return convertView;

    }
}