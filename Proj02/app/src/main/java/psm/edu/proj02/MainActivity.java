package psm.edu.proj02;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textF, textR, textH, textSigma, textUMax;
    private CPlate plate;

    private void showParams(){
        textF.setText(String.format(Locale.getDefault(), "F=%6.3f [N]", plate.getF()));
        textR.setText(String.format(Locale.getDefault(), "R=%6.3f [mm]", plate.getR()));
        textH.setText(String.format(Locale.getDefault(), "H=%6.3f [mm]", plate.getH()));
        textSigma.setText(String.format(Locale.getDefault(), "Sigma=%6.3f [MPa]", plate.calculateSigma()));
        textUMax.setText(String.format(Locale.getDefault(), "Umax=%6.3f [mm]", plate.calculateUMax()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        plate = new CPlate(1500., 250., 4.5);
        textF = findViewById(R.id.textF);
        textR = findViewById(R.id.textR);
        textH = findViewById(R.id.textH);
        textSigma = findViewById(R.id.textSigma);
        textUMax = findViewById(R.id.textUMax);
        showParams();
        findViewById(R.id.buttonCalculate).setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                intent.putExtra("F", plate.getF());
                intent.putExtra("R", plate.getR());
                intent.putExtra("H", plate.getH());
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 1:
                if(resultCode == Activity.RESULT_OK){
                    Bundle bex = data.getExtras();
                    plate.setF(bex.getDouble("F"));
                    plate.setR(bex.getDouble("R"));
                    plate.setH(bex.getDouble("H"));
                    showParams();
                }
                break;
        }
    }
}
