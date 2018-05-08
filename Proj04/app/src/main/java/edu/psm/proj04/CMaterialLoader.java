package edu.psm.proj04;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

/**
 * Created by lewin on 08.05.2018.
 */

public class CMaterialLoader  extends CursorLoader {
    CDBMaterial dbase;
    public CMaterialLoader(Context context, CDBMaterial b) {
        super(context);
        dbase = b;
    }
    @Override
    public Cursor loadInBackground() {
        return dbase.getMaterials();
    }

}
