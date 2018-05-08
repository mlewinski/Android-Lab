package edu.psm.proj04;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    private CDBMaterial cdbMaterial;
    private SimpleCursorAdapter scAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(this);
        cdbMaterial = new CDBMaterial(this);
        cdbMaterial.open();
        scAdapter=new SimpleCursorAdapter(
                this, R.layout.list_item, null,
                new String[]{CDBHelper.TYP_ID,
                        CDBHelper.TYP_TYP, CDBHelper.TYP_NORM},
                new int[]{R.id.tvID, R.id.tvName, R.id.tvNorm}, 0);
        ((ListView)findViewById(R.id.listView)).setAdapter(
                scAdapter);
        getLoaderManager().initLoader(1, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id,Bundle args){
        return new CMaterialLoader(this, cdbMaterial);
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader,
                               Cursor data) {
        scAdapter.swapCursor(data);
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        scAdapter.swapCursor(null);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button:
                cdbMaterial.addMaterial(
                        ((EditText)findViewById(
                                R.id.editText)).getText().toString(),
                        ((EditText)findViewById(
                                R.id.editText1)).getText().toString());
                getLoaderManager().getLoader(1).forceLoad();
                break;
        }
    }
}
