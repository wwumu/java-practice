package garden.everyday.day3;

/**
 * 一块待导入的地块。本包为了讲 CSV 导入，重写了一个精简版。
 */
public class Plot {

    private final String code;
    private final double area;
    private final String soilType;
    private final String location;

    public Plot(String code, double area, String soilType, String location) {
        this.code = code;
        this.area = area;
        this.soilType = soilType;
        this.location = location;
    }

    public String getCode() {
        return code;
    }

    public double getArea() {
        return area;
    }

    public String getSoilType() {
        return soilType;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return code + "（" + area + " 平方米，" + soilType + "，" + location + "）";
    }
}
