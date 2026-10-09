package garden;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用社区园艺场景讲清 ArrayList 和 HashMap 的区别。
 *
 * <p>一句话总纲：
 * <ul>
 *   <li><b>ArrayList</b> 是“一本按顺序翻的农事记录本” —— 关心<b>顺序</b>，按位置取。</li>
 *   <li><b>HashMap</b> 是“一叠按编号插的档案卡” —— 关心<b>查找</b>，按编号取。</li>
 * </ul>
 *
 * <p>直接运行本类的 main 方法即可看到全部演示。
 */
public class Demo02ArrayListVsHashMap {

    public static void main(String[] args) {
        section("1. ArrayList：按时间顺序记录农事（关心顺序）");
        demoArrayList();

        section("2. 同样是这些数据，换成 HashMap：按记录号查找（关心查找）");
        demoHashMapLookup();

        section("3. HashMap 最常用的姿势：分组统计");
        demoGroupByPlot();

        section("4. 新手最容易踩的坑：对象作键却不写 equals/hashCode");
        demoEqualsHashCodeTrap();

        section("小结");
        summary();
    }

    // ================================================================
    // 1. ArrayList
    // ================================================================
    private static void demoArrayList() {
        // 泛型 <FarmingRecord> 表示这个 List 里只能放 FarmingRecord
        List<FarmingRecord> records = new ArrayList<>();

        // add() 把元素追加到末尾。谁先 add，谁就在前面 —— 顺序被保留
        records.add(new FarmingRecord("R001", "A-01", "播种", "2026-03-01", "张三"));
        records.add(new FarmingRecord("R002", "A-01", "浇水", "2026-03-05", "张三"));
        records.add(new FarmingRecord("R003", "A-02", "施肥", "2026-03-06", "李四"));
        records.add(new FarmingRecord("R004", "A-01", "打药", "2026-03-12", "李四"));
        records.add(new FarmingRecord("R005", "A-03", "播种", "2026-03-02", "张三"));
        records.add(new FarmingRecord("R006", "A-03", "施肥", "2026-03-08", "张三"));
        records.add(new FarmingRecord("R007", "A-03", "收获", "2026-06-16", "张三"));
        records.add(new FarmingRecord("R008", "A-04", "播种", "2026-03-03", "李四"));
        System.out.println("共 " + records.size() + " 条记录，按录入顺序打印：");
        // 用增强 for 遍历：ArrayList 的遍历就是从头到尾
        for (FarmingRecord r : records) {
            System.out.println("  " + r);
        }

        // get(下标) 按位置取 —— 下标从 0 开始
        System.out.println();
        System.out.println("第 1 条（下标 0）：" + records.get(0));
        System.out.println("最后 1 条（下标 size-1）：" + records.get(records.size() - 1));

        // 想找“记录号是 R004 的那条”，ArrayList 只能一条条比对
        System.out.println();
        System.out.println("要按记录号找 R004，只能从头逐条比对：");
        int steps = 0;
        FarmingRecord found = null;
        for (FarmingRecord r : records) {
            steps++;
            if ("R007".equals(r.getId())) {
                found = r;
                break;
            }
        }
        System.out.println("  比对了 " + steps + " 次才找到：" + found);
        System.out.println("  → 记录一多，这种找法会越来越慢");
    }

    // ================================================================
    // 2. HashMap 按编号查找
    // ================================================================
    private static void demoHashMapLookup() {
        // 键是 String（记录号），值是 FarmingRecord
        Map<String, FarmingRecord> byId = new HashMap<>();

        // put(键, 值)：把数据按“键”放进去
        byId.put("R001", new FarmingRecord("R001", "A-01", "播种", "2026-03-01", "张三"));
        byId.put("R002", new FarmingRecord("R002", "A-01", "浇水", "2026-03-05", "张三"));
        byId.put("R003", new FarmingRecord("R003", "A-02", "施肥", "2026-03-06", "李四"));
        byId.put("R004", new FarmingRecord("R004", "A-01", "打药", "2026-03-12", "李四"));


        System.out.println("放入 " + byId.size() + " 条。注意：打印顺序和放入顺序无关！");
        for (Map.Entry<String, FarmingRecord> e : byId.entrySet()) {
            System.out.println("  " + e.getKey() + " -> " + e.getValue());
        }

        // get(键) 直接取，一步到位
        System.out.println();
        System.out.println("按记录号取 R006：" + byId.get("R006"));
        System.out.println("  → 不管放了多少条，get 都是一步到位");

        // get 一个不存在的键，返回 null（不会抛异常）
        System.out.println();
        System.out.println("取不存在的 R999：" + byId.get("R999") + "（返回 null）");

        // 所以取出来用之前，要么判空，要么用 getOrDefault
        System.out.println("用 getOrDefault 兜底：" + byId.getOrDefault("R999",
                new FarmingRecord("R999", "未知", "未知", "未知", "未知")));

        // containsKey 判断键在不在
        System.out.println("containsKey(\"R002\") = " + byId.containsKey("R002"));
        System.out.println("containsKey(\"R999\") = " + byId.containsKey("R999"));
    }

    // ================================================================
    // 3. 分组统计
    // ================================================================
    private static void demoGroupByPlot() {
        List<FarmingRecord> records = new ArrayList<>();
        records.add(new FarmingRecord("R001", "A-01", "播种", "2026-03-01", "张三"));
        records.add(new FarmingRecord("R002", "A-01", "浇水", "2026-03-05", "张三"));
        records.add(new FarmingRecord("R003", "A-02", "施肥", "2026-03-06", "李四"));
        records.add(new FarmingRecord("R004", "A-01", "打药", "2026-03-12", "李四"));
        records.add(new FarmingRecord("R005", "A-02", "浇水", "2026-03-13", "王五"));

        // 想把记录按地块分组：Map<地块编号, 该地块的记录列表>
        Map<String, List<FarmingRecord>> byPlot = new HashMap<>();

        for (FarmingRecord r : records) {
            String key = r.getPlotCode();
            // 第一次遇到这个地块时，value 还是 null，要先建一个空 List
            if (!byPlot.containsKey(key)) {
                byPlot.put(key, new ArrayList<FarmingRecord>());
            }
            // 再把记录追加到该地块的列表里
            byPlot.get(key).add(r);
        }

        System.out.println("按地块分组结果：");
        for (Map.Entry<String, List<FarmingRecord>> e : byPlot.entrySet()) {
            System.out.println("  地块 " + e.getKey() + " 共 " + e.getValue().size() + " 条：");
            for (FarmingRecord r : e.getValue()) {
                System.out.println("      " + r.getType() + "（" + r.getDate() + "，" + r.getOperator() + "）");
            }
        }

        // 进阶：用 getOrDefault 把上面的 if 判断一行写完
        Map<String, Integer> countByType = new HashMap<>();
        for (FarmingRecord r : records) {
            // 没有这个类型就当 0，然后 +1 放回去
            countByType.put(r.getType(), countByType.getOrDefault(r.getType(), 0) + 1);
        }
        System.out.println();
        System.out.println("按农事类型计数（getOrDefault 一行搞定）：");
        for (Map.Entry<String, Integer> e : countByType.entrySet()) {
            System.out.println("  " + e.getKey() + "：" + e.getValue() + " 次");
        }

        //计算每块地块的累加和
       List<Plot> recode1 = new ArrayList<>();
        recode1.add(new Plot("A-01",12.2,"壤土"));
        recode1.add(new Plot("A-01",15.2,"沙土"));
        recode1.add(new Plot("A-02",22.2,"壤土"));
        recode1.add(new Plot("A-03",15.1,"壤土"));
        recode1.add(new Plot("A-03",12.2,"沙土"));
        recode1.add(new Plot("A-01",10.2,"沙土"));

        Map<String,Double> totalArea = new HashMap<>();
        for (Plot r : recode1){
            totalArea.put(r.getCode(), totalArea.getOrDefault(r.getCode(),0.0)+ r.getArea());
        }
        for (Map.Entry<String,Double> e : totalArea.entrySet()){
            System.out.println("  "+e.getKey()+":"+"共"+e.getValue()+"平方米");
        }
    }

    // ================================================================
    // 4. equals / hashCode 的坑
    // ================================================================
    private static void demoEqualsHashCodeTrap() {
        System.out.println("Plot 类已经正确实现了 equals 和 hashCode（只按 code 比较）：");
        Plot p1 = new Plot("A-01", 12.0, "壤土");
        Plot p2 = new Plot("A-01", 15.5, "沙土");
        System.out.println("  new Plot(\"A-01\", 12.0) 与 new Plot(\"A-01\", 15.5) 是两个不同对象吗？");
        System.out.println("  p1 == p2        : " + (p1 == p2) + "   ← 比较是不是同一个对象");
        System.out.println("  p1.equals(p2)   : " + p1.equals(p2) + "    ← 比较业务上算不算同一块地");
        System.out.println("  p1.hashCode()==p2.hashCode() : " + (p1.hashCode() == p2.hashCode()));

        // 用 p1 作键放进去，用 p2 去取，能取到 —— 因为 equals/hashCode 一致
        Map<Plot, String> owner = new HashMap<>();
        owner.put(p1, "张三");
        System.out.println();
        System.out.println("  用 p1 存入 owner，用 p2 去取：");
        System.out.println("    owner.get(p2) = " + owner.get(p2) + "   ← 取到了！");

        System.out.println();
        System.out.println("如果 Plot 忘了写 hashCode（或只写 equals），这里会返回 null ——");
        System.out.println("数据明明放进去了却取不出来，这是新手最常遇到的灵异问题。");
        System.out.println("规则：equals 用什么字段比较，hashCode 就用什么字段算。");
    }

    // ================================================================
    private static void section(String title) {
        System.out.println();
        System.out.println("========================================================");
        System.out.println("  " + title);
        System.out.println("========================================================");
    }

    private static void summary() {
        System.out.println("  ArrayList                      |  HashMap");
        System.out.println("  -------------------------------|------------------------------");
        System.out.println("  农产品记录本，讲究先后顺序     |  档案卡柜，讲究按编号取");
        System.out.println("  按位置取：get(下标)            |  按键取：get(键)");
        System.out.println("  查值慢（要逐条比对）           |  查值快（几乎一步到位）");
        System.out.println("  允许重复元素                   |  键唯一，重复 put 会覆盖");
        System.out.println("  允许 null 元素                 |  允许一个 null 键");
        System.out.println();
        System.out.println("  怎么选：");
        System.out.println("    · 要保留先后顺序、要按位置访问、要允许重复  → ArrayList");
        System.out.println("    · 要按某个编号/名称快速查找、要去重、要计数  → HashMap");
        System.out.println("    · 本系统里：农事记录列表用 ArrayList；");
        System.out.println("      按批次号查批次、按地块号分组统计，用 HashMap。");
        System.out.println();
        System.out.println("  两者常常一起用，就像上面第 3 个演示：");
        System.out.println("    Map<String, List<农事记录>> —— 外层 HashMap 分组，内层 ArrayList 保序。");
    }
}
