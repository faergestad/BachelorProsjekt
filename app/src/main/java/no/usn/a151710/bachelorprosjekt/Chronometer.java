package no.usn.a151710.bachelorprosjekt;

import android.content.Context;

public class Chronometer implements Runnable {

    public static final long MILLIS_TO_MINUTES = 60000;
    public static final long MILLIS_TO_HOURS = 3600000;

    private Context context;
    private long mStartTime;

    private boolean mIsRunning;

    public Chronometer(Context context) {
        this.context = context;
    }

    public void start() {
        mStartTime = System.currentTimeMillis();
        mIsRunning = true;

    }

    public void stop() {

        mIsRunning = false;

    }

    @Override
    public void run() {

        while(mIsRunning) {

            long since = System.currentTimeMillis() - mStartTime;

            int seconds = (int) ((since / 1000) % 60);
            int minutes = (int) ((since / MILLIS_TO_MINUTES) % 60);
            int hours = (int) ((since / MILLIS_TO_HOURS) % 24);

            ((TimeRegistrering)context).oppdaterTimerText(String.format(
                    "%02d:%02d:%02d", hours, minutes, seconds
            ));

        }

    }
}
