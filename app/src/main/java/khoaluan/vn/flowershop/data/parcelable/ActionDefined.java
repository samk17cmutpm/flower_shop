package khoaluan.vn.flowershop.data.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by samnguyen on 9/10/16.
 */
public class ActionDefined implements Parcelable {
    private int go;

    private boolean edit;

    public ActionDefined(int go) {
        this.go = go;
    }

    public int getGo() {
        return go;
    }

    public void setGo(int go) {
        this.go = go;
    }

    public ActionDefined(int go, boolean edit) {
        this.go = go;
        this.edit = edit;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.go);
        dest.writeByte(this.edit ? (byte) 1 : (byte) 0);
    }

    protected ActionDefined(Parcel in) {
        this.go = in.readInt();
        this.edit = in.readByte() != 0;
    }

    public static final Creator<ActionDefined> CREATOR = new Creator<ActionDefined>() {
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
