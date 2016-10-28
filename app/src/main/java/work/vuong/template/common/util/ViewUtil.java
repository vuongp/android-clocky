package work.vuong.template.common.util;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by oberon (vuongpham) on 28/10/2016.
 */

public class ViewUtil {

    /**
     *  Fetches image and displays it to the set imageView
     *
     * @param imageView view to set image to
     * @param url Url to fetch image from
     */
    @BindingAdapter("app:loadUrl")
    public static void setImage(ImageView imageView,String url) {
        Picasso.with(imageView.getContext()).load(url).into(imageView);
    }

}
