package com.example.hamiltontevin_ce02.modal;

import java.io.Serializable;

public class Employee implements Serializable {
    private  int mId;
    private  int mEmployyeID;
    private  String mFirstName;
    private  String mLAstName;
    private  String  mHireDate;


    private  String mEmploymentStatus;

    public Employee(String mFirstName, String mLAstName, int mEmployyeID, String mHireDate, String mEmploymentStatus) {
        this.mFirstName = mFirstName;
        this.mLAstName = mLAstName;
        this.mEmployyeID = mEmployyeID;
        this.mHireDate = mHireDate;
        this.mEmploymentStatus = mEmploymentStatus;
    }
    public int getmId() {
        return mId;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public String getmLAstName() {
        return mLAstName;
    }

    public int getmEmployyeID() {
        return mEmployyeID;
    }

    public String getmHireDate() {
        return mHireDate;
    }

    public String getmEmploymentStatus() {
        return mEmploymentStatus;
    }



    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public void setmLAstName(String mLAstName) {
        this.mLAstName = mLAstName;
    }

    public void setmEmployyeID(int mEmployyeID) {
        this.mEmployyeID = mEmployyeID;
    }

    public void setmHireDate(String mHireDate) {
        this.mHireDate = mHireDate;
    }

    public void setmEmploymentStatus(String mEmploymentStatus) {
        this.mEmploymentStatus = mEmploymentStatus;
    }
}
