package com.mwf.fuzzyquery;

import android.os.Parcel;
import android.os.Parcelable;

public class List_Emp implements Parcelable {
    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getEmpemail() {
        return empemail;
    }

    public void setEmpemail(String empemail) {
        this.empemail = empemail;
    }

    public String getEmpdept() {
        return empdept;
    }

    public void setEmpdept(String empdept) {
        this.empdept = empdept;
    }

    public String empid;
    public String qrcode;
    public String empname;
    public String phonetic;
    public String empemail;
    public String empdept;

    public static final Creator<List_Emp> CREATOR = new Creator<List_Emp>() {
        @Override
        public List_Emp createFromParcel(Parcel in) {
            List_Emp emp = new List_Emp();
            emp.empid = in.readString();
            emp.qrcode = in.readString();
            emp.empname = in.readString();
            emp.phonetic = in.readString();
            emp.empemail = in.readString();
            emp.empdept = in.readString();
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
        parcel.writeString(empid);
        parcel.writeString(qrcode);
        parcel.writeString(empname);
        parcel.writeString(phonetic);
        parcel.writeString(empemail);
        parcel.writeString(empdept);
    }
}
