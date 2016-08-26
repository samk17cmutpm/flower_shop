package khoaluan.vn.flowershop.data.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import khoaluan.vn.flowershop.data.model_parse_and_realm.Flower;

/**
 * Created by samnguyen on 8/26/16.
 */
public class FlowerSuggesstion implements Parcelable {
    private List<Flower> flowers;

    public FlowerSuggesstion(List<Flower> flowers) {
        this.flowers = flowers;
    }

    public List<Flower> getFlowers() {
        return flowers;
    }

    public void setFlowers(List<Flower> flowers) {
        this.flowers = flowers;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.flowers);
    }

    protected FlowerSuggesstion(Parcel in) {
        this.flowers = in.createTypedArrayList(Flower.CREATOR);
    }

    public static final Parcelable.Creator<FlowerSuggesstion> CREATOR = new Parcelable.Creator<FlowerSuggesstion>() {
        @Override
        public FlowerSuggesstion createFromParcel(Parcel source) {
            return new FlowerSuggesstion(source);
        }

        @Override
        public FlowerSuggesstion[] newArray(int size) {
            return new FlowerSuggesstion[size];
        }
    };
}
