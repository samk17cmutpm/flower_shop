package khoaluan.vn.flowershop.data.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by samnguyen on 9/10/16.
 */
public class ActionDefined implements Parcelable {
    private int go;

    public ActionDefined(int go) {
        this.go = go;
    }

    public int getGo() {
        return go;
    }

    public void setGo(int go) {
        this.go = go;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.go);
    }

    protected ActionDefined(Parcel in) {
        this.go = in.readInt();
    }

    public static final Parcelable.Creator<ActionDefined> CREATOR = new Parcelable.Creator<ActionDefined>() {
        @Override
        public ActionDefined createFromParcel(Parcel source) {
            return new ActionDefined(source);
        }

        @Override
        public ActionDefined[] newArray(int size) {
            return new ActionDefined[size];
        }
    };
}
