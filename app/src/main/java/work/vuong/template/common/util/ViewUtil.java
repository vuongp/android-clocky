package work.vuong.template.common.util;

import android.databinding.BindingAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by oberon (vuongpham) on 28/10/2016.
 */

public class ViewUtil {

    /**
     * Fetches image and displays it to the set imageView
     *
     * @param imageView view to set image to
     * @param url       Url to fetch image from
     */
    @BindingAdapter("app:loadUrl")
    public static void setImage(ImageView imageView, String url) {
        Picasso.with(imageView.getContext()).load(url).into(imageView);
    }

    /**
     * Disables a view for the specified time given.
     * <p>
     * Returns the subscription
     */
    public static Subscription disableView(FrameLayout frame, int time, TimeUnit timeUnit) {
        frame.setEnabled(false);
        return Observable.timer(time, timeUnit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    frame.setEnabled(true);
                });
    }
}
