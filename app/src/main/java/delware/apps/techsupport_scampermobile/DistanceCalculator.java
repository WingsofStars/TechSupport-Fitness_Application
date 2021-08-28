package delware.apps.techsupport_scampermobile;

public class DistanceCalculator {
    static final double _eQuatorialEarthRadius = 6378.1370D;
    static final double _d2r = (Math.PI / 180D);

    public double getDistanceM(double lat1, double long1, double lat2, double long2){
        double dlong = (long2 - long1) * _d2r;
        double dlat = (lat2 - lat1) * _d2r;
        double a = Math.pow(Math.sin(dlat / 2D), 2D) + Math.cos(lat1 * _d2r) * Math.cos(lat2 * _d2r)
                * Math.pow(Math.sin(dlong / 2D), 2D);
        double c = 2D * Math.atan2(Math.sqrt(a), Math.sqrt(1D - a));
        double d = _eQuatorialEarthRadius * c;
        //converts d to meters
        return (double) (1000 * d);
        //if you want to convert to miles and don't forget to refactor
        //return (int) (0.62137119 * d);
    }
}
