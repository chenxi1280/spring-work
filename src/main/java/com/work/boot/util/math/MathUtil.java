package com.work.boot.util.math;

public class MathUtil {

    public static Double round(Double oldDouble, int weishu) {
//            Math.pow(x,y)表示x的y次幂，round函数只能四舍五入一位小数
        return Math.round(oldDouble * Math.pow(10, weishu)) / Math.pow(10, weishu);

    }

    //获取几位随机数
    public static String getRandomStr(int weishu) {
        String s = "";
        for (int x = 0; x < weishu; x++) {
            s += (int) (Math.random() * 10);
        }
        return s;
    }

    public static final double earthRadius = 6371.393;//地球半径:km

    /**
     * 将公里数转换为度数
     *
     * @param l 公里
     * @return
     */
    public static Double kmToDegree(Double l) {
        //公式:l(弧长)=degree（圆心角）× π（圆周率）× r（半径）/180
        //转换后的公式：degree（圆心角）=l(弧长) × 180/(π（圆周率）× r（半径）)
        Double degree = (180 / earthRadius / Math.PI) * l;
        return degree;
    }

    /**
     * 将度数转换为公里数
     *
     * @param degree 经纬度数
     * @return
     */
    public static Double degreeToKm(Double degree) {
        //公式:l(弧长)=degree（圆心角）× π（圆周率）× r（半径）/180
        Double l = earthRadius / 180 * Math.PI * degree;//将180放在前面，降低数值
        return l;
    }

    private static final double EARTH_RADIUS = 6371000;//赤道半径(单位m)
    private static final double INTEGR_NUM = 10000;

    /**
     * 基于googleMap中的算法得到两经纬度之间的距离,误差 <= 0.2m
     *
     * @param lng1 第一点的经度
     * @param lat1 第一点的纬度
     * @param lng2 第二点的经度
     * @param lat2 第二点的纬度
     * @return 返回的距离，单位m
     */

    public static double distance(double lng1, double lat1, double lng2, double lat2) {
        double x1 = Math.cos(lat1) * Math.cos(lng1);
        double y1 = Math.cos(lat1) * Math.sin(lng1);
        double z1 = Math.sin(lat1);
        double x2 = Math.cos(lat2) * Math.cos(lng2);
        double y2 = Math.cos(lat2) * Math.sin(lng2);
        double z2 = Math.sin(lat2);
        double lineDistance = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));
        double s = EARTH_RADIUS * Math.PI * 2 * Math.asin(0.5 * lineDistance) / 180;
        return Math.round(s * INTEGR_NUM) / INTEGR_NUM;
    }

    public static void main(String[] args) {//测试
        System.err.println( distance(106.484756,29.53199,106.483713,29.531803));
    }


}
