package work.vuong.template.common.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;

/**
 * Created by oberon (vuongpham) on 05/11/2016.
 */
public class NetworkUtil {

    public static final int PACKET_LOST = -2;
    public static final int PING_FAILED = -1;

    public static String getIpAddress() {
        try {
            for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        String ipAddress = inetAddress.getHostAddress();
                        return ipAddress;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("VUONG", ex.toString());
        }
        return null;
    }

    /**
     * TODO: refactor this, it kind of sucks.
     * Method to get a single ping.
     */
    private static int getPing(String url) throws Exception {
        Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 " + url);
        BufferedReader reader = new BufferedReader(new InputStreamReader(p1.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        String result = builder.toString();

        if (result.contains("100% packet loss")) {
            return PACKET_LOST;
        }

        Pattern timePattern = Pattern.compile("time=[0-9\\.]*\\sms", Pattern.CASE_INSENSITIVE);     //for example: "time=12 ms"
        Matcher matcher = timePattern.matcher(result);
        int returnVal = p1.waitFor();
        boolean reachable = (returnVal == 0);
        if (matcher.find() && reachable) {
            String time = matcher.group();
            int num = Math.round(Float.valueOf(time.substring(5, time.length() - 3)));
            return num;
        }

        return PING_FAILED;
    }

    public static Observable<Integer> getPing() {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(NetworkUtil.getPing("google.nl"));
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

}
