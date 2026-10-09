package garden;

import java.util.Objects;

/**
 * 一个植物品种（例如“番茄”“黄瓜”）。
 *
 * <p>它会作为 {@link java.util.HashSet} 的元素。HashSet 判断“是否重复”
 * 用的就是 equals + hashCode，所以这两个方法必须成对正确实现 ——
 * 和 {@link Plot} 是同一个道理。
 */
public class PlantSpecies {

    /** 品种名，业务上唯一 */
    private final String name;
    /** 科属，用于连作障碍提示（同科不宜连作） */
    private final String family;

    public PlantSpecies(String name, String family) {
        this.name = name;
        this.family = family;
    }

    public String getName() {
        return name;
    }

    public String getFamily() {
        return family;
    }

    /** 品种名相同就算同一个品种，科属不参与比较 */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PlantSpecies other = (PlantSpecies) o;
        return Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name + "（" + family + "）";
    }
}
