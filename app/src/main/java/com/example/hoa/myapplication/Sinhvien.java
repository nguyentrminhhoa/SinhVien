package com.example.hoa.myapplication;

/**
 * Created by hoa on 8/26/2018.
 */
import java.io.Serializable;

public class Sinhvien implements Serializable{
    private int stt;
    private String Hoten;
    private String Mssv;
    private String Sdt;
    private String Namsinh;
    private String Tenlop;
    public Sinhvien(){

    }
    public Sinhvien (String Hoten, String Mssv, String Sdt, String Namsinh, String Tenlop){
        this.Hoten=Hoten;
        this.Mssv=Mssv;
        this.Sdt=Sdt;
        this.Namsinh=Namsinh;
        this.Tenlop=Tenlop;
    }
    public Sinhvien (int stt,String Hoten, String Mssv, String Sdt, String Namsinh, String Tenlop){
        this.stt=stt;
        this.Hoten=Hoten;
        this.Mssv=Mssv;
        this.Sdt=Sdt;
        this.Namsinh=Namsinh;
        this.Tenlop=Tenlop;
    }
    public int getStt(){
        return stt;
    }
    public void setStt(int stt){
        this.stt=stt;
    }

    public String getHoten(){
        return Hoten;
    }
    public void setHoten(String Hoten){
        this.Hoten=Hoten;
    }

    public String getMssv(){
        return Mssv;
    }
    public void setMssv(String Mssv){
        this.Mssv=Mssv;
    }

    public String getSdt(){
        return Sdt;
    }
    public void setSdt(String Sdt){
        this.Sdt=Sdt;
    }

    public String getNamsinh(){
        return Namsinh;
    }
    public void setNamsinh(String Namsinh){
        this.Namsinh=Namsinh;
    }

    public String getTenlop(){
        return Tenlop;
    }
    public void setTenlop(String Tenlop){
        this.Tenlop=Tenlop;
    }

    @Override
    public String toString(){
        return this.Hoten;
    }
}
