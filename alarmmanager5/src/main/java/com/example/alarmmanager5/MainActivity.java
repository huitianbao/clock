package com.example.alarmmanager5;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private AlarmManager alarmManager;
    private PendingIntent pi;
    private Calendar calendar=Calendar.getInstance();
    private TimePicker timePicker;
    private DatePicker datePicker;
    private Button button_con;

    private int c_hour,c_min;
    private int d_year,d_month,d_day;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 真机
        setContentView(R.layout.activity_main);

        timePicker=(TimePicker)findViewById(R.id.my_timePicker);
        datePicker=(DatePicker)findViewById(R.id.my_datePicker);
        d_year=calendar.get(Calendar.YEAR);
        d_month=calendar.get(Calendar.MONTH);
        d_day=calendar.get(Calendar.HOUR_OF_DAY);

        date=new Date(d_year-1900,d_month,d_day,c_hour,c_min,0);


        datePicker.init(d_year,d_month,d_day, new DatePicker.OnDateChangedListener(){

            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                int month_tmp;
                MainActivity.this.d_year = i;
                MainActivity.this.d_month = i1;
                MainActivity.this.d_day = i2;

                month_tmp=d_month+1;


                Toast.makeText(MainActivity.this, "您选择的日期：" + d_year + "年  "
                        + month_tmp + "月  " + d_day + "日", Toast.LENGTH_SHORT).show();
            }
        });

        button_con=(Button)findViewById(R.id.button_con);

        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                c_hour=i;
                c_min=i1;
            }
        });

        button_con.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                long t=calendar_set();
              //  long t=date.getTime();
                bindViews(t);

            }
        });
    }

    private long calendar_set(){
        long time;
        calendar.set(Calendar.YEAR,d_year);
        calendar.set(Calendar.MONTH,d_month);
        calendar.set(Calendar.DAY_OF_MONTH,d_day);

        calendar.set(Calendar.HOUR_OF_DAY,c_hour);
        calendar.set(Calendar.MINUTE,c_min);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        time=calendar.getTimeInMillis();
        return time;
    }


    private void bindViews(long tt) {

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, ClockActivity.class);
        pi = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,tt, pi);

    }
}

