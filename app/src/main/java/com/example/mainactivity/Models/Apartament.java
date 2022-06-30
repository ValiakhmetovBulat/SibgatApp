package com.example.mainactivity.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Apartament implements Parcelable {
    private String apartamentName;
    private String apartamentAddress;
    private String apartamentType;
    private String apartamentImage;
    private String apartamentDescription;
    private String apartamentId;

    public Apartament() {

    }

    public Apartament(String apartamentName, String apartamentAddress, String apartamentType, String apartamentImage, String apartamentDescription, String apartamentId) {
        this.apartamentName = apartamentName;
        this.apartamentAddress = apartamentAddress;
        this.apartamentType = apartamentType;
        this.apartamentImage = apartamentImage;
        this.apartamentDescription = apartamentDescription;
        this.apartamentId = apartamentId;
    }

    protected Apartament(Parcel in) {
        apartamentName = in.readString();
        apartamentAddress = in.readString();
        apartamentType = in.readString();
        apartamentImage = in.readString();
        apartamentDescription = in.readString();
        apartamentId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(apartamentName);
        dest.writeString(apartamentAddress);
        dest.writeString(apartamentType);
        dest.writeString(apartamentImage);
        dest.writeString(apartamentDescription);
        dest.writeString(apartamentId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Apartament> CREATOR = new Creator<Apartament>() {
        @Override
        public Apartament createFromParcel(Parcel in) {
            return new Apartament(in);
        }

        @Override
        public Apartament[] newArray(int size) {
            return new Apartament[size];
        }
    };

    public String getApartamentName() {
        return apartamentName;
    }

    public void setApartamentName(String apartamentName) {
        this.apartamentName = apartamentName;
    }

    public String getApartamentAddress() {
        return apartamentAddress;
    }

    public void setApartamentAddress(String apartamentAddress) {
        this.apartamentAddress = apartamentAddress;
    }

    public String getApartamentType() {
        return apartamentType;
    }

    public void setApartamentType(String apartamentType) {
        this.apartamentType = apartamentType;
    }

    public String getApartamentImage() {
        return apartamentImage;
    }

    public void setApartamentImage(String apartamentImage) {
        this.apartamentImage = apartamentImage;
    }

    public String getApartamentDescription() {
        return apartamentDescription;
    }

    public void setApartamentDescription(String apartamentDescription) {
        this.apartamentDescription = apartamentDescription;
    }

    public String getApartamentId() {
        return apartamentId;
    }

    public void setApartamentId(String apartamentId) {
        this.apartamentId = apartamentId;
    }
}
