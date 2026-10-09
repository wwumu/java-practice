package garden.everyday.day1;

import garden.aicode.Plot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapTest {
    public static void main(String[] args) {
        //计算每块地块的累加和
        List<garden.aicode.Plot> recode1 = new ArrayList<>();
        recode1.add(new garden.aicode.Plot("A-01",12.2,"壤土"));
        recode1.add(new garden.aicode.Plot("A-01",15.2,"沙土"));
        recode1.add(new garden.aicode.Plot("A-02",22.2,"壤土"));
        recode1.add(new garden.aicode.Plot("A-03",15.1,"壤土"));
        recode1.add(new garden.aicode.Plot("A-03",12.2,"沙土"));
        recode1.add(new garden.aicode.Plot("A-01",10.2,"沙土"));

        Map<String,Double> totalArea = new HashMap<>();
        for (Plot r : recode1){
            totalArea.put(r.getCode(), totalArea.getOrDefault(r.getCode(),0.0)+ r.getArea());
        }
        for (Map.Entry<String,Double> e : totalArea.entrySet()){
            System.out.println("  "+e.getKey()+":"+"共"+e.getValue()+"平方米");
        }
    }
}

