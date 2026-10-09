package garden;

import java.util.Objects;

/**
 * 一块菜地（例如 A-01，12 平方米，土壤为壤土）。
 *
 * <p><b>重点看 equals 和 hashCode 这两个方法。</b>
 * 当对象要作为 {@link java.util.HashMap} 的“键”时，这两个方法决定了
 * “两个对象算不算同一把钥匙”。如果只写 equals 不写 hashCode，
 * HashMap 会找不到你已经放进去的数据 —— 这是初学者最常踩的坑。
 */
public class Plot {

    /** 地块编号，业务上唯一，例如 A-01 */
    private final String code;
    /** 面积（平方米） */
    private final double area;
    /** 土壤类型 */
    private final String soilType;

    public Plot(String code, double area, String soilType) {
        this.code = code;
        this.area = area;
        this.soilType = soilType;
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

    /**
     * 判定“是不是同一块地”：只看编号，不看面积和土壤。
     * 因为编号是业务主键，A-01 永远只可能有一块。
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Plot other = (Plot) o;
        return Objects.equals(code, other.code);
    }

    /**
     * 编号相同的两块地，必须算出相同的哈希值。
     * 只依据 code 计算，与 equals 保持一致。
     */
    @Override
   public int hashCode() {return Objects.hash(code);}

    @Override
    public String toString() {
        return code + "（" + area + " 平方米，" + soilType + "）";
    }
}
