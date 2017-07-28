package org.proverbio.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Juan Pablo Proverbio <proverbio@nowcreatives.co/>
 */
public class SampleParcelable implements Parcelable
{
    //Constructor that receives a Parcel
    protected SampleParcelable(Parcel in) {
    }

    //The Parcelable CREATOR
    public static final Creator<SampleParcelable> CREATOR = new Creator<SampleParcelable>() {
        @Override
        public SampleParcelable createFromParcel(Parcel in) {
            return new SampleParcelable(in);
        }

        @Override
        public SampleParcelable[] newArray(int size) {
            return new SampleParcelable[size];
        }
    };

    //Describe the kinds of special objects contained polymorphism
    @Override
    public int describeContents() {
        return 0;
    }

    //Writes the object data to a Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {}
}
