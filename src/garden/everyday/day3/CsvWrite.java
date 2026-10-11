package garden.everyday.day3;

import javax.imageio.IIOException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvWrite {
    public static void main(String[] args) throws IOException{
        System.out.println("文件已写入至：");
        write();
    }

    private static void write()throws IOException {
        Path csv= Paths.get("data","plot-csvtest.csv");
        List<Plot> plots=new ArrayList<>();
        plots.add(new Plot("A-01",12.1,"土壤","一号楼，东侧"));
        plots.add(new Plot("A-02",15.1,"土壤","一号楼，西侧"));

        try(BufferedWriter bw = Files.newBufferedWriter(csv, StandardCharsets.UTF_8)) {
            bw.write(CsvUtils.joinLine("code", "area", "soilType", "location"));
            bw.newLine();

            for (Plot p : plots){
                bw.write(CsvUtils.joinLine(p.getCode(),CsvUtils.num(p.getArea()),
                        p.getSoilType(),p.getLocation()));
                bw.newLine();
            }
        }
        System.out.println(csv.toAbsolutePath());
    }
}
