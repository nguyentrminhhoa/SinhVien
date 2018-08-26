package com.example.hoa.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddEdit extends AppCompatActivity {
    Sinhvien sinhvien;
    private static final int MODE_CREATE = 1;
    private static final int MODE_EDIT = 2;
    private  int mode;
    private EditText textHoten;
    private EditText textMssv;
    private EditText textSdt;
    private EditText textNamsinh;
    private EditText textTenlop;

    private boolean needRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        this.textHoten = (EditText)this.findViewById(R.id.text_hoten);
        this.textMssv = (EditText)this.findViewById(R.id.text_mssv);
        this.textSdt = (EditText)this.findViewById(R.id.text_sdt);
        this.textNamsinh = (EditText)this.findViewById(R.id.text_namsinh);
        this.textTenlop = (EditText)this.findViewById(R.id.text_tenlop);

        Intent intent = this.getIntent();
        this.sinhvien = (Sinhvien)intent.getSerializableExtra("sinhvien");
        if (sinhvien == null){
            this.mode = MODE_CREATE;
        }else{
            this.mode=MODE_EDIT;
            this.textHoten.setText(sinhvien.getHoten());
            this.textMssv.setText(sinhvien.getMssv());
            this.textSdt.setText(sinhvien.getSdt());
            this.textNamsinh.setText(sinhvien.getNamsinh());
            this.textTenlop.setText(sinhvien.getTenlop());
        }

    }

    // Click vào nút SAVE

    public void buttonSaveClicked(View view) {
        MyDatabase db = new MyDatabase(this);

        String hoten = this.textHoten.getText().toString();
        String mssv = this.textMssv.getText().toString();
        String sdt = this.textSdt.getText().toString();
        String namsinh = this.textNamsinh.getText().toString();
        String tenlop = this.textTenlop.getText().toString();

        if(hoten.equals("") || mssv.equals("") || sdt.equals("") || namsinh.equals("") || tenlop.equals("")) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập tất cả thông tin", Toast.LENGTH_LONG).show();
            return;
        }
        if(mode==MODE_CREATE){
            this.sinhvien = new Sinhvien(hoten,mssv,sdt,namsinh,tenlop);
            db.addSinhvien(sinhvien);
        }else {
            this.sinhvien.setHoten(hoten);
            this.sinhvien.setMssv(mssv);
            this.sinhvien.setSdt(sdt);
            this.sinhvien.setNamsinh(namsinh);
            this.sinhvien.setTenlop(tenlop);
            db.updateSinhvien(sinhvien);
        }
        this.needRefresh = true;
        this.onBackPressed();

    }

    // Khi click vào nút No thì trờ về main activity
    public void buttonCancelClicked(View view){
        this.onBackPressed();
    }
    // Phản hồi cho activity gọi tới nó
    @Override
    public void finish(){
        Intent data = new Intent();
        data.putExtra("Cần làm mới", needRefresh);

        // activity hoàn thành và trả về dữ liệu
        this.setResult(Activity.RESULT_OK,data);
        super.finish();
    }
}
