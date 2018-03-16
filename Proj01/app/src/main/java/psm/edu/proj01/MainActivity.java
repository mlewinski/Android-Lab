package psm.edu.proj01;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements android.view.View.OnClickListener{

    private Button buttonPK;
    private Button buttonDial;
    private Button buttonContacts;
    private Button buttonContactInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonPK = (Button) findViewById(R.id.buttonPK);
        buttonDial = (Button) findViewById(R.id.buttonDial);
        buttonContacts = (Button) findViewById(R.id.buttonContacts);
        buttonContacts = (Button) findViewById(R.id.buttonContactInfo);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch(view.getId()){
            case R.id.buttonPK:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.pk.edu.pl"));
                startActivity(intent);
                break;
            case R.id.buttonDial:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse(""));
                startActivity(intent);
                break;
            case R.id.buttonContacts:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"));
                startActivity(intent);
                break;
            case R.id.buttonContactInfo:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people/1"));
                startActivity(intent);
                break;
        }
    }
}
