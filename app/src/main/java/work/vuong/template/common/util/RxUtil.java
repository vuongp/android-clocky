package work.vuong.template.common.util;

import android.support.annotation.NonNull;

import rx.Subscription;

/**
 * Created by oberon (vuongpham) on 28/10/2016.
 */
public class RxUtil {

    /**
     * Unsubscribes all the subscriptions given
     * @param subscriptions
     */
    public static void unsubscribe(@NonNull Subscription... subscriptions) {
        for (Subscription subscription : subscriptions) {
            if (subscription != null && !subscription.isUnsubscribed()){
                subscription.unsubscribe();
            }
        }
    }

}
