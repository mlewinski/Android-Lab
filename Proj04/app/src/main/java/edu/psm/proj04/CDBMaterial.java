package edu.psm.proj04;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by lewin on 08.05.2018.
 */

public class CDBMaterial {
    private CDBHelper cdbHelper;
    private String[] MATERIAL_RECORD = {CDBHelper.TYP_ID, CDBHelper.TYP_TYP, CDBHelper.TYP_NORM};
    private SQLiteDatabase dbMaterial;

    CDBMaterial(Context context){
        cdbHelper = new CDBHelper(context);
    }

    public void open() throws SQLException {
        dbMaterial = cdbHelper.getWritableDatabase();
    }

    public void close(){ dbMaterial.close(); }

    public void addMaterial(String typ, String norma){
        ContentValues cv = new ContentValues();
        cv.put(CDBHelper.TYP_TYP, typ);
        cv.put(CDBHelper.TYP_NORM, norma);
        dbMaterial.insert(CDBHelper.TABLE_TYPE, null, cv);
    }
    public Cursor getMaterials(){
        return dbMaterial.query(CDBHelper.TABLE_TYPE,
                MATERIAL_RECORD, null, null, null, null,
                CDBHelper.TYP_TYP);
    }
    public void deleteMaterial(int id) {
        dbMaterial.delete(CDBHelper.TABLE_TYPE,
                CDBHelper.TYP_ID + " = " + id, null);
    }
}
