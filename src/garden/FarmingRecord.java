package garden;

/**
 * 一条农事记录（例如：2026-03-15 给 A-01 地块浇水）。
 *
 * <p>这个类故意写得很简单，只承载数据。它的作用是充当
 * {@link java.util.ArrayList} 里的元素类型，以及后面 HashMap 分组时的“值”。
 */
public class FarmingRecord {

    /** 记录编号，唯一 */
    private final String id;
    /** 地块编号，例如 A-01 */
    private final String plotCode;
    /** 农事类型，例如 播种 / 浇水 / 施肥 / 打药 */
    private final String type;
    /** 记录日期 */
    private final String date;
    /** 操作人 */
    private final String operator;

    public FarmingRecord(String id, String plotCode, String type, String date, String operator) {
        this.id = id;
        this.plotCode = plotCode;
        this.type = type;
        this.date = date;
        this.operator = operator;
    }

    public String getId() {
        return id;
    }

    public String getPlotCode() {
        return plotCode;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s %s 由 %s 操作（记录号 %s）",
                date, plotCode, type, operator, id);
    }
}
