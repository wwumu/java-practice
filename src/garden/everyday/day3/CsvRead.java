package garden.everyday.day3;

import java.nio.file.Path;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CsvRead {
    public static void main(String[] args) throws IOException{
        Path csv = Paths.get("data", "plot-csvtest.csv");
        read(csv);
    }

    private static void read(Path csv)throws IOException {
        try(BufferedReader br = Files.newBufferedReader(csv, StandardCharsets.UTF_8)) {
            String line;
            int Nline=0;
            while ((line= br.readLine())!=null){
                Nline++;
                System.out.println(line);
            }
            System.out.println("共"+Nline+"行");
        }
    }
}
