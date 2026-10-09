package garden;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 用社区园艺场景讲清 HashSet 与 LinkedList。
 *
 * <p>承接 {@link Demo02ArrayListVsHashMap}：
 * <ul>
 *   <li><b>HashSet</b> = 品种登记簿 —— 只关心“在不在”，不关心顺序，且自动去重。
 *       （它其实就是“只有键、没有值”的 HashMap）</li>
 *   <li><b>LinkedList</b> = 待办农活队列 —— 老在两头加、两头取，几乎不动中间。</li>
 * </ul>
 *
 * <p>直接运行本类的 main 方法即可看到全部演示。
 */
public class Demo03SetAndLinkedList {

    public static void main(String[] args) {
        section("1. HashSet：品种登记簿 —— 自动去重 + 快速判断“在不在”");
        demoHashSet();

        section("2. LinkedList：待办农活队列 —— 两头增删，天然适合排队");
        demoLinkedListQueue();

        section("3. ArrayList vs LinkedList：删除头部元素的代价");
        demoRemoveHeadCost();

        section("小结");
        summary();
    }

    // ================================================================
    // 1. HashSet
    // ================================================================
    private static void demoHashSet() {
        // 品种登记簿：同一品种只应登记一次
        Set<PlantSpecies> catalog = new HashSet<>();

        System.out.println("登记 4 个品种（注意第 3 个和第 1 个重名但科属不同）：");
        // add() 有返回值！返回 true = 真的加进去了；false = 已存在，没加
        System.out.println("  添加 番茄（茄科）  -> " + catalog.add(new PlantSpecies("番茄", "茄科")));
        System.out.println("  添加 黄瓜（葫芦科）-> " + catalog.add(new PlantSpecies("黄瓜", "葫芦科")));
        System.out.println("  添加 番茄（茄科）  -> " + catalog.add(new PlantSpecies("番茄", "茄科"))
                + "   ← 重名，被拒绝");
        System.out.println("  添加 生菜（菊科）  -> " + catalog.add(new PlantSpecies("生菜", "菊科")));

        System.out.println();
        System.out.println("登记簿里实际有 " + catalog.size() + " 个品种：");
        for (PlantSpecies s : catalog) {
            System.out.println("  " + s);
        }
        System.out.println("  → 虽然 add 了 4 次，但只留下 3 个，重复的被自动挡掉");

        // 判断“这个品种登记过没有”，一步到位
        System.out.println();
        boolean hasTomato = catalog.contains(new PlantSpecies("番茄", "茄科"));
        boolean hasCorn = catalog.contains(new PlantSpecies("玉米", "禾本科"));
        System.out.println("contains(番茄) = " + hasTomato + "   ← 不用遍历，直接判断");
        System.out.println("contains(玉米) = " + hasCorn);

        // 遍历顺序：HashSet 不保证顺序
        System.out.println();
        System.out.println("注意：HashSet 的遍历顺序和添加顺序无关，也不保证稳定。");
        System.out.println("      如果需要“按登记先后”展示品种，就得用 List 或 LinkedHashSet。");

        // 另一个常见用法：用 Set 给 List 去重
        List<String> rawCodes = new ArrayList<>();
        rawCodes.add("A-01");
        rawCodes.add("A-02");
        rawCodes.add("A-01");
        rawCodes.add("A-03");
        rawCodes.add("A-02");
        Set<String> distinct = new HashSet<>(rawCodes);
        System.out.println();
        System.out.println("一批地块编号（有重复）：" + rawCodes);
        System.out.println("用 new HashSet<>(list) 去重后：" + distinct);
        System.out.println("  → 这是把 List 去重的最省事写法");
    }

    // ================================================================
    // 2. LinkedList 当队列
    // ================================================================
    private static void demoLinkedListQueue() {
        // 待办农活队列：先安排的先干（FIFO），也可以插队到最前面
        LinkedList<String> todo = new LinkedList<>();

        // addLast / offerLast 加到尾；addFirst / offerFirst 加到头
        todo.offerLast("给 A-01 浇水");
        todo.offerLast("给 A-02 施肥");
        todo.offerLast("清理排水沟");
        System.out.println("排入 3 件农活：" + todo);

        // 紧急任务插到最前面 —— 这是 LinkedList 的拿手操作
        todo.offerFirst("【紧急】A-03 发现蚜虫，先打药");
        System.out.println("插入一件紧急农活后：" + todo);

        // peek = 看一眼队首（不取走）；poll = 取走队首
        System.out.println();
        System.out.println("peekFirst() 看一眼： " + todo.peekFirst() + "   ← 没取走，队列还是 " + todo.size() + " 件");
        System.out.println("pollFirst() 取走：   " + todo.pollFirst());
        System.out.println("pollFirst() 取走：   " + todo.pollFirst());
        System.out.println("剩余队列：" + todo);

        // 当作栈用：push/pop 操作的是头部（后进先出）
        LinkedList<String> undoStack = new LinkedList<>();
        undoStack.push("已提交 R001 浇水记录");
        undoStack.push("已提交 R002 施肥记录");
        System.out.println();
        System.out.println("用 push 当撤销栈：" + undoStack);
        System.out.println("pop() 撤销最近一步：" + undoStack.pop() + "   ← 后进先出");

        // poll 与 remove 的区别：队列空时 poll 返回 null，remove 抛异常
        LinkedList<String> empty = new LinkedList<>();
        System.out.println();
        System.out.println("空队列 pollFirst()  = " + empty.pollFirst() + "   ← 返回 null，安全");
        try {
            empty.removeFirst();
        } catch (java.util.NoSuchElementException e) {
            System.out.println("空队列 removeFirst() 抛 NoSuchElementException ← 所以一般用 poll");
        }
    }

    // ================================================================
    // 3. 删除头部元素的代价
    // ================================================================
    private static void demoRemoveHeadCost() {
        final int N = 50000;

        // 两个都是 List，但底层实现不同，删头部元素的代价差别很大。
        // 注意：这里的 linkedList 声明为 LinkedList（而不是 List），
        // 因为 pollFirst() 定义在 LinkedList / Deque 上，List 接口里没有。
        List<String> arrayList = new ArrayList<>();
        LinkedList<String> linkedList = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            String v = "task" + i;
            arrayList.add(v);
            linkedList.add(v);
        }

        // ArrayList 删除头部：后面的元素要整体往前挪，元素越多越慢
        long t1 = System.nanoTime();
        while (!arrayList.isEmpty()) {
            arrayList.remove(0);
        }
        long costArray = System.nanoTime() - t1;

        // LinkedList 删除头部：只改两个指针，与元素个数无关
        long t2 = System.nanoTime();
        while (!linkedList.isEmpty()) {
            linkedList.pollFirst();
        }
        long costLinked = System.nanoTime() - t2;

        System.out.println("元素个数：" + N);
        System.out.println("ArrayList  反复 remove(0) 耗时：" + (costArray / 1000000) + " ms");
        System.out.println("LinkedList 反复 pollFirst() 耗时：" + (costLinked / 1000000) + " ms");
        System.out.printf("LinkedList 快约 %.0f 倍%n", (double) costArray / Math.max(costLinked, 1));

        System.out.println();
        System.out.println("原因：");
        System.out.println("  ArrayList 底层是连续数组，删掉第 0 个，后面几万个元素都要往前挪一格；");
        System.out.println("  LinkedList 底层是双向链表，删掉第 0 个只改两个指针，跟总数无关。");
        System.out.println();
        System.out.println("但要注意 —— 这不代表 LinkedList 全面更好：");
        System.out.println("  · 按【下标】随机访问：ArrayList 快（直接算地址），LinkedList 慢（要从头数过去）");
        System.out.println("  · 内存占用：LinkedList 每个元素多存两个指针，更费内存");
        System.out.println("  · 实际开发里，ArrayList 是绝大多数场景的默认选择，");
        System.out.println("    只有“频繁在两头增删”（队列、栈）才优先考虑 LinkedList。");
    }

    // ================================================================
    private static void section(String title) {
        System.out.println();
        System.out.println("========================================================");
        System.out.println("  " + title);
        System.out.println("========================================================");
    }

    private static void summary() {
        System.out.println("  HashSet                        |  LinkedList");
        System.out.println("  -------------------------------|------------------------------");
        System.out.println("  品种登记簿，只问“在不在”       |  待办农活队列，讲究谁先谁后");
        System.out.println("  自动去重                       |  允许重复");
        System.out.println("  不保证顺序                     |  严格保留顺序");
        System.out.println("  判断存在快：contains           |  两头增删快：offerFirst/pollFirst");
        System.out.println("  不能按下标取                   |  可以按下标取（但慢）");
        System.out.println();
        System.out.println("  四兄弟到底怎么选：");
        System.out.println("    · 要有顺序、按位置访问、允许重复        -> ArrayList（最常用）");
        System.out.println("    · 要按编号快速查、要计数、要分组        -> HashMap");
        System.out.println("    · 只问“有没有”、要自动去重              -> HashSet");
        System.out.println("    · 频繁在两头增删（队列 / 栈）           -> LinkedList");
        System.out.println();
        System.out.println("  HashSet 与 HashMap 的关系：");
        System.out.println("    HashSet 就是“只有键、没有值”的 HashMap，");
        System.out.println("    所以元素同样必须正确实现 equals + hashCode。");
    }
}
