package gautamastudios.com.whatprogress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    DrawProgress drawProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawProgress = findViewById(R.id.drawProgress);
        drawProgress.setTotalIndicators(6);
    }

    public void increment(View v) {
        drawProgress.incrementProgress();
    }

    public void decrement(View v) {
        drawProgress.decrementProgress();
    }
}
