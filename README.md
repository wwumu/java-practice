# practice —— Java 集合练习（社区园艺场景）

## 文件说明

| 文件 | 作用 |
|---|---|
| `src/garden/FarmingRecord.java` | 一条农事记录（日期、地块、类型、操作人）。列表元素 / 分组的值 |
| `src/garden/Plot.java` | 一块菜地。**重点看 equals / hashCode** —— 对象作 HashMap 键时必须正确实现 |
| `src/garden/PlantSpecies.java` | 一个植物品种。**元素作 HashSet 成员**，同样必须有 equals / hashCode |
| `src/garden/Demo02ArrayListVsHashMap.java` | 演示 2：ArrayList vs HashMap（顺序 vs 查找、分组计数、对象作键的坑） |
| `src/garden/Demo03SetAndLinkedList.java` | 演示 3：HashSet vs LinkedList（去重 vs 队列、删头部的性能差异） |

## 在 IDEA 里怎么跑

1. `File` → `Open`，选择 `E:\file\毕设\practice` 目录（**不是**选 `src`）；
2. 等 IDEA 右下角索引进度条走完；
3. 打开 `Demo02ArrayListVsHashMap.java` 或 `Demo03SetAndLinkedList.java`，
   点 `main` 方法左边的绿色三角 → `Run '...main()'`；
4. 在下方 Run 窗口看输出。

**跑之前先确认 SDK 没问题**：`File` → `Project Structure` → `Project`，
`SDK` 应显示 `1.8`，`Language level` 为 `8`。若显示红色或 `<No SDK>`，
在 `SDK` 下拉里选 `1.8`（`Add SDK` → `JDK` → 选 `C:\Program Files\Java\jdk1.8.0_241`）。

> 本项目的 SDK 已经配置正确（`.idea/misc.xml` 里写的是 `project-jdk-name="1.8"`，
> 而 IDEA 中注册的 `1.8` 指向 `C:/Program Files/Java/jdk1.8.0_241`，是完整 JDK），
> 正常打开即可直接运行。

## 用命令行跑（不想开 IDEA 时）

```bat
cd /d E:\file\毕设\practice
"C:\Program Files\Java\jdk1.8.0_241\bin\javac" -encoding UTF-8 -d out src\garden\*.java
"C:\Program Files\Java\jdk1.8.0_241\bin\java" -Dfile.encoding=UTF-8 -cp out garden.Demo02ArrayListVsHashMap
"C:\Program Files\Java\jdk1.8.0_241\bin\java" -Dfile.encoding=UTF-8 -cp out garden.Demo03SetAndLinkedList
```

注意：**不要**直接敲 `javac`。这台机器的 `JAVA_HOME` 指向 `E:\jdk`，
那是一个 **JRE（只有 `java.exe`，没有 `javac.exe` 和 `tools.jar`）**，
所以命令行里的 `javac` 很可能找不到，必须写全路径。

## 看代码的建议顺序

1. `Demo02.demoArrayList()`：理解 List 的 `add` / `get` / 遍历；
2. `Demo02.demoHashMapLookup()`：理解 Map 的 `put` / `get` / `containsKey` / `getOrDefault`；
3. `Demo02.demoGroupByPlot()`：实际开发里最常用的写法（分组 + 计数）；
4. `Demo02.demoEqualsHashCodeTrap()`：**对象作键的坑都在这**；
5. `Demo03.demoHashSet()`：去重与 `contains`，注意 `add()` 有返回值；
6. `Demo03.demoLinkedListQueue()`：`offerFirst` / `pollFirst` / `push` / `pop` 的用法；
7. `Demo03.demoRemoveHeadCost()`：用实测数据看删头部的性能差异。

## 四兄弟怎么选

| 需求 | 选择 |
|---|---|
| 要有顺序、按位置访问、允许重复 | **ArrayList**（最常用，默认选它） |
| 要按编号快速查、要计数、要分组 | **HashMap** |
| 只问“有没有”、要自动去重 | **HashSet** |
| 频繁在两头增删（队列 / 栈） | **LinkedList** |

补充两点：

- **HashSet 就是“只有键、没有值”的 HashMap**，所以元素同样必须正确实现 `equals` + `hashCode`。
- **LinkedList 不是普遍比 ArrayList 好**：按【下标】随机访问它反而慢（要从头数过去），
  每个元素还多存两个指针、更费内存。只有“频繁在两头增删”才优先选它。

## 自己动手改一改（建议）

1. 在 `Demo02.demoArrayList()` 里把 4 条记录加到 8 条，看「逐条比对」的次数怎么变；
2. 把 `Plot.hashCode()` 注释掉，再跑 `Demo02` 第 4 个演示，
   亲眼看到 `owner.get(p2)` 变成 `null`；
3. 把 `PlantSpecies.hashCode()` 注释掉，再跑 `Demo03` 第 1 个演示，
   看 HashSet 会不会把重名品种放进去两次（会）；
4. 把 `Demo03.demoRemoveHeadCost()` 里的 `N` 从 50000 改成 200000，
   观察两者耗时差距如何拉大；
5. 试着写一个 `Map<String, Double>`，统计每个地块的**总面积**
   （提示：值用 `Double`，用 `getOrDefault(code, 0.0) + area` 累加）。
