package khoaluan.vn.flowershop;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import khoaluan.vn.flowershop.font_support.CustomViewWithTypefaceSupport;
import khoaluan.vn.flowershop.font_support.TextField;
import khoaluan.vn.flowershop.utils.ImageUniversalUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by samnguyen on 7/19/16.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder(getApplicationContext())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/roboto_light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .addCustomViewWithSetTypeface(CustomViewWithTypefaceSupport.class)
                .addCustomStyle(TextField.class, R.attr.textFieldStyle)
                .build()
        );
        initializeImageLoader();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initializeImageLoader() {
        ImageUniversalUtils.imageLoader = ImageLoader.getInstance();
        ImageUniversalUtils.options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(false)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .delayBeforeLoading(0)
                .displayer(new SimpleBitmapDisplayer()) // default
                .build();
        ImageUniversalUtils.imageLoader.init(ImageLoaderConfiguration.createDefault(this));
    }
}
