package com.example.hoa.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private static final int MENU_ITEM_VIEW = 111;
    private static final int MENU_ITEM_EDIT= 222;
    private static final int MENU_ITEM_CREATE = 333;
    private static final int MENU_ITEM_DELETE = 444;

    private static final int MY_REQUEST_CODE = 1000;
    private final List<Sinhvien>sinhvienList=new ArrayList<Sinhvien>();
    private ArrayAdapter<Sinhvien>listViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //lấy listview từ file xml
        listView = (ListView)findViewById(R.id.listView);
        MyDatabase db = new MyDatabase(this);
        db.createDefaultSinhvienNeed();
        List<Sinhvien> list = db.getAllSinhvien();
        this.sinhvienList.addAll(list);
        // Adapter can
        // 1 - Context
        // 2 - Layout cho các dòng.
        // 3 - ID của TextView mà dữ liệu sẽ được ghi vào
        // 4 - Danh sách dữ lieu
        this.listViewAdapter = new ArrayAdapter<Sinhvien>(this,android.R.layout.simple_list_item_1,android.R.id.text1,this.sinhvienList);

        // đăng ký Adapter cho listView
        this.listView.setAdapter(this.listViewAdapter);
        // đăng ký context cho menu listview
        registerForContextMenu(this.listView);
    }
    @Override

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,view,menuInfo);
        menu.setHeaderTitle("Chọn hành động");

        menu.add(0,MENU_ITEM_VIEW,0,"Xem Sinh viên");
        menu.add(0,MENU_ITEM_CREATE,1,"Thêm mới Sinh viên");
        menu.add(0,MENU_ITEM_EDIT,2,"Sửa Sinh viên");
        menu.add(0,MENU_ITEM_DELETE,4,"Xóa Sinh viên");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Sinhvien selectedSinhvien = (Sinhvien)this.listView.getItemAtPosition(info.position);

        if (item.getItemId() == MENU_ITEM_VIEW){
            Toast.makeText(getApplicationContext(),selectedSinhvien.getHoten(),Toast.LENGTH_LONG).show();
        }else if (item.getItemId() == MENU_ITEM_CREATE){
            Intent intent = new Intent(this,AddEdit.class);

            //Start Activity AddEdit, co phan hồi
            this.startActivityForResult(intent,MY_REQUEST_CODE);
        }else if (item.getItemId() == MENU_ITEM_EDIT){
            Intent intent = new Intent(this,AddEdit.class);
            intent.putExtra("sinhvien",selectedSinhvien);

            this.startActivityForResult(intent,MY_REQUEST_CODE);
        }else if (item.getItemId() == MENU_ITEM_DELETE){

            // Hỏi trước khi xóa
            new AlertDialog.Builder(this).setMessage(selectedSinhvien.getHoten() + ".Bạn thực sự muốn xóa sinh viên này? ").setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog,int id){
                    deleteSinhvien(selectedSinhvien);
                }
            })
                    .setNegativeButton("No",null)
                    .show();
        }else {
            return false;
        }
        return true;
    }

    // Khi đồng ý xóa 1 sinh viên
    private void deleteSinhvien(Sinhvien sinhvien){
        MyDatabase db = new MyDatabase(this);
        db.deleteSinhvien(sinhvien);
        this.sinhvienList.remove(sinhvien);

        //Làm mới danh sách
        this.listViewAdapter.notifyDataSetChanged();
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == Activity.RESULT_OK && requestCode == MY_REQUEST_CODE){
            boolean needRefresh = data.getBooleanExtra("cần làm mới",true);

            // làm mới list view
            if (needRefresh){
                this.sinhvienList.clear();
                MyDatabase db = new MyDatabase(this);
                List<Sinhvien> list = db.getAllSinhvien();
                this.sinhvienList.addAll(list);

                this.listViewAdapter.notifyDataSetChanged();
            }
        }
    }
}
