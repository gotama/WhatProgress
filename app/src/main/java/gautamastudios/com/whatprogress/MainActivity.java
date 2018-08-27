package gautamastudios.com.whatprogress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    DrawProgress drawProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawProgress = findViewById(R.id.drawProgress);
        drawProgress.setTotalIndicators(6);
        drawProgress.setProgress(6);
    }
}
