package io.github.krtkush.marsexplorer.RoverExplorer.AboutRover;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import io.github.krtkush.marsexplorer.R;

public class AboutRoverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_rover);
    }

    /**
     * Method to make toast on this activity.
     * @param toastMessage The message to be displayed.
     * @param toastDuration Duration for the toast to be visible.
     */
    protected void showToast(String toastMessage, int toastDuration) {
        Toast.makeText(this, toastMessage, toastDuration).show();
    }
}
