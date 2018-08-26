package com.example.hoa.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by hoa on 8/26/2018.
 */

public class MyDatabase extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";
    // phien ban
    private static final int DATABASE_VERSION = 1;
    // ten CSDL
    private static final String DATABASE_NAME = "SinhVien_DD";
    // ten bang: Sinhvien
    private static final String TABLE_SINHVIEN = "Sinhvien";

    private static final String COLUMN_SINHVIEN_STT = "Sinhvien_Stt";
    private static final String COLUMN_SINHVIEN_HOTEN = "Sinhvien_Hoten";
    private static final String COLUMN_SINHVIEN_MSSV = "Sinhvien_Mssv";
    private static final String COLUMN_SINHVIEN_SDT = "Sinhvien_Sdt";
    private static final String COLUMN_SINHVIEN_NAMSINH = "Sinhvien_Namsinh";
    private static final String COLUMN_SINHVIEN_TENLOP = "Sinhvien_Tenlop";


    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Tao bảng
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabase.onCreate ...");
        //Script de tao bảng
        String script = "CREATE_TABLE" + TABLE_SINHVIEN + "(" + COLUMN_SINHVIEN_STT + "INTEGER PRIMARY KEY," + COLUMN_SINHVIEN_HOTEN + "TEXT," + COLUMN_SINHVIEN_MSSV + "TEXT," + COLUMN_SINHVIEN_SDT + "TEXT," + COLUMN_SINHVIEN_NAMSINH + "TEXT," + COLUMN_SINHVIEN_TENLOP + "TEXT" + ")";
        // chay lenh tao bang
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "MyDatabase.onUpgrade ...");
        // drop bang neu no ton tai
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_SINHVIEN);
        // tao lại
        onCreate(db);
    }

    // Neu trong bang chua co dl thi chen 2 dl
    public void createDefaultSinhvienNeed() {
        int count = this.getSinhvienCount();
        if (count == 0) {
            Sinhvien sinhvien1 = new Sinhvien("Nguyen Tran Minh Hoa", "B1310395", "0899001709", "1995", "DI13Z6A1");
            Sinhvien sinhvien2 = new Sinhvien("Nguyen Van A", "B1231233", "0999999999", "1996", "DI13Z6A2");
            this.addSinhvien(sinhvien1);
            this.addSinhvien(sinhvien2);
        }
    }

    public void addSinhvien(Sinhvien sinhvien) {
        Log.i(TAG, "MyDatabase.addSinhvien ..." + sinhvien.getHoten());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SINHVIEN_HOTEN, sinhvien.getHoten());
        values.put(COLUMN_SINHVIEN_MSSV, sinhvien.getMssv());
        values.put(COLUMN_SINHVIEN_SDT, sinhvien.getSdt());
        values.put(COLUMN_SINHVIEN_NAMSINH, sinhvien.getNamsinh());
        values.put(COLUMN_SINHVIEN_TENLOP, sinhvien.getTenlop());

        // chèn 1 dòng dl vào bảng
        db.insert(TABLE_SINHVIEN, null, values);

        // đóng kết nối csdl
        db.close();
    }

    public Sinhvien getSinhvien(int id) {
        Log.i(TAG, "Mydatabase.getSinhvien ..." + id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SINHVIEN, new String[]{COLUMN_SINHVIEN_STT, COLUMN_SINHVIEN_HOTEN, COLUMN_SINHVIEN_MSSV, COLUMN_SINHVIEN_SDT, COLUMN_SINHVIEN_NAMSINH, COLUMN_SINHVIEN_TENLOP}, COLUMN_SINHVIEN_STT + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Sinhvien sinhvien = new Sinhvien(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
        // return sinhvien
        return sinhvien;
    }

    public List<Sinhvien> getAllSinhvien() {
        Log.i(TAG, "MyDatabse.getAllSinhvien ... ");
        List<Sinhvien> sinhvienList = new ArrayList<Sinhvien>();
        // select all query
        String selectQuery = "SELECT * FROM" + TABLE_SINHVIEN;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Duyệt con trỏ để thêm vào danh sách
        if (cursor.moveToFirst()) {
            do {
                Sinhvien sinhvien = new Sinhvien();
                sinhvien.setStt(Integer.parseInt(cursor.getString(0)));
                sinhvien.setHoten(cursor.getString(1));
                sinhvien.setMssv(cursor.getString(2));
                sinhvien.setSdt(cursor.getString(3));
                sinhvien.setNamsinh(cursor.getString(4));
                sinhvien.setTenlop(cursor.getString(5));

                // thêm vào danh sách
                sinhvienList.add(sinhvien);
            } while (cursor.moveToNext());
        }
        return sinhvienList;
    }

    public int getSinhvienCount() {
        Log.i(TAG, "Mydatabase.getSinhvienCount ...");
        String countQuery = "SELECT * FROM " + TABLE_SINHVIEN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int updateSinhvien(Sinhvien sinhvien) {
        Log.i(TAG, "MyDatabase.updateSinhvien ..." + sinhvien.getHoten());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SINHVIEN_HOTEN, sinhvien.getHoten());
        values.put(COLUMN_SINHVIEN_MSSV, sinhvien.getMssv());
        values.put(COLUMN_SINHVIEN_SDT, sinhvien.getSdt());
        values.put(COLUMN_SINHVIEN_NAMSINH, sinhvien.getNamsinh());
        values.put(COLUMN_SINHVIEN_TENLOP, sinhvien.getTenlop());

        // update tt sinh vien
        return db.update(TABLE_SINHVIEN, values, COLUMN_SINHVIEN_STT + "=?", new String[]{String.valueOf(sinhvien.getStt())});

    }
    public void deleteSinhvien(Sinhvien sinhvien){
        Log.i(TAG,"MyDatabase.updateSinhvien ..." + sinhvien.getHoten());
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SINHVIEN,COLUMN_SINHVIEN_STT + "=?",new String[]{String.valueOf(sinhvien.getStt())});
        db.close();
    }
}
