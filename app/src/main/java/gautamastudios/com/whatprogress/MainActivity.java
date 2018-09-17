package gautamastudios.com.whatprogress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements GoBack {

//    DrawProgress drawProgress;
    LayoutProgress layoutProgress;
//    boolean lol;

    @Override
    public void onClick() {

//        if (lol) {
//            lol = false;
//            drawProgress.setVisibility(View.VISIBLE);
//            layoutProgress.setVisibility(View.GONE);
//        } else {
//            lol = true;
//            drawProgress.setVisibility(View.GONE);
//            layoutProgress.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        drawProgress = findViewById(R.id.drawProgress);
        layoutProgress = findViewById(R.id.layoutProgress);
//        drawProgress.setTotalIndicators(6);
    }

    public void increment(View v) {
//        drawProgress.incrementProgress();
    }

    public void decrement(View v) {
//        drawProgress.decrementProgress();
    }
}
