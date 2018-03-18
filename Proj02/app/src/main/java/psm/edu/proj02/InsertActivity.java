package psm.edu.proj02;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity implements android.view.View.OnClickListener{

    private EditText editF;
    private EditText editR;
    private EditText editH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        editF = findViewById(R.id.editF);
        editR = findViewById(R.id.editR);
        editH = findViewById(R.id.editH);
        findViewById(R.id.buttonInsertOk).setOnClickListener(this);
        findViewById(R.id.buttonInsertCancel).setOnClickListener(this);

        Bundle bex = getIntent().getExtras();
        editF.setText(String.valueOf(bex.getDouble("F")));
        editR.setText(String.valueOf(bex.getDouble("R")));
        editH.setText(String.valueOf(bex.getDouble("H")));
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        switch(view.getId()){
            case R.id.buttonInsertOk:
                try{
                    intent.putExtra("F", Double.valueOf(editF.getText().toString()));
                    intent.putExtra("R", Double.valueOf(editR.getText().toString()));
                    intent.putExtra("H", Double.valueOf(editH.getText().toString()));
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                catch(NumberFormatException e){
                    Toast.makeText(this, getString(R.string.incorrectData), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.buttonInsertCancel:
                setResult(Activity.RESULT_CANCELED);
                finish();
                break;
        }
    }
}
