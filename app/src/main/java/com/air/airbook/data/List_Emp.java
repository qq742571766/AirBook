package com.air.airbook.data;

import android.os.Parcel;
import android.os.Parcelable;

public class List_Emp implements Parcelable {
    private String bookid;//1

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBookkind() {
        return bookkind;
    }

    public void setBookkind(String bookkind) {
        this.bookkind = bookkind;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getPrinttime() {
        return printtime;
    }

    public void setPrinttime(String printtime) {
        this.printtime = printtime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLendtime() {
        return lendtime;
    }

    public void setLendtime(String lendtime) {
        this.lendtime = lendtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLender() {
        return lender;
    }

    public void setLender(String lender) {
        this.lender = lender;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    private String barcode;//Air-D-008-0072
    private String bookname;//LoadRunner性能测试巧匠训练营
    private String bookkind;//软件相关
    private String writer;//赵强、邹伟伟、任健勇
    private String press;//机械工业出版社
    private String printtime;//2015-01-01
    private String price;//69.00
    private String lendtime;//2017-08-31
    private String status;//未借出
    private String lender;//林郝
    private String department;//研发

    public static final Creator<List_Emp> CREATOR = new Creator<List_Emp>() {
        @Override
        public List_Emp createFromParcel(Parcel in) {
            List_Emp emp = new List_Emp();
            emp.bookid = in.readString();
            emp.barcode = in.readString();
            emp.bookname = in.readString();
            emp.bookkind = in.readString();
            emp.writer = in.readString();
            emp.press = in.readString();
            emp.printtime = in.readString();
            emp.price = in.readString();
            emp.lendtime = in.readString();
            emp.status = in.readString();
            emp.lender = in.readString();
            emp.department = in.readString();
            return emp;
        }

        @Override
        public List_Emp[] newArray(int size) {
            return new List_Emp[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bookid);
        parcel.writeString(barcode);
        parcel.writeString(bookname);
        parcel.writeString(bookkind);
        parcel.writeString(writer);
        parcel.writeString(press);
        parcel.writeString(printtime);
        parcel.writeString(price);
        parcel.writeString(lendtime);
        parcel.writeString(status);
        parcel.writeString(lender);
        parcel.writeString(department);
    }
}
