package edu.psm.proj04;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by lewin on 08.05.2018.
 */

public class CDBHelper extends android.database.sqlite.SQLiteOpenHelper {
    public static final String TABLE_TYPE = "TYPE";
    public static final String TYP_ID = "_id";
    public static final String TYP_TYP = "type";
    public static final String TYP_NORM = "norm";

    private static final String DB_NAME = "Material.db";
    private static final String CREATE_TABLE_TYPE = "CREATE TABLE " + TABLE_TYPE + "("
            + TYP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TYP_TYP + " TEXT, " + TYP_NORM + " TEXT )";
    private static final int DB_VERSION = 1;


    public CDBHelper(Context ctx){
        super(ctx, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_TYPE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPE);
        onCreate(sqLiteDatabase);
    }
}
