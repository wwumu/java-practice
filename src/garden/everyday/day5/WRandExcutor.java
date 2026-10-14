package garden.everyday.day5;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import garden.everyday.day3.CsvUtils;
import garden.everyday.day3.Plot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;

public class WRandExcutor {

    /*READER 和 WRITER 以及线程的综合练习
    * 对文件进行读取-处理（打印处理结果）-写入（处理时的失败和成功报告）
    * */

    private final int EXCUTOR=4;

    //安全的存放子线程的处理结果，避免因为并行处理导致的数据重复存储
    private final Set<String> Only= Collections.synchronizedSet(new HashSet<String>());

    public static void main(String[] args) throws IOException, InterruptedException {
        WRandExcutor wRandExcutor = new WRandExcutor();
        Path path = Paths.get("data","plots_to_import.csv");

        if (!Files.exists(path)){
            System.out.println("该文件已存在："+path.toAbsolutePath());
            return;
        }

        List<RowResult> results = wRandExcutor.Import(path);
        System.out.println("结果汇总：");
        int ok=0;
        int fail=0;
        int skip=0;

        for (RowResult r : results){
            System.out.println(r);
            if (r.isSkipper()){
                skip++;
                continue;
            }
            if (r.isSuccess()){
                ok++;
            }else {
                fail++;
            }
        }
        System.out.println("共成功"+ok+"个任务，"+"失败"+fail+"个任务，"+"跳过"+skip+"个任务.");
    }


    public List<RowResult> Import(Path CsvPath) throws IOException, InterruptedException {
        List<RowResult> results = new ArrayList<>();
        ExecutorService pool =Executors.newFixedThreadPool(EXCUTOR);

        try {
            List<String> Lines = new ArrayList<>();
            int LineNo = 0;

            try(BufferedReader br = Files.newBufferedReader(CsvPath, StandardCharsets.UTF_8)) {
                String line;
                while ((line = br.readLine()) !=null){
                LineNo++;
                if (line.trim().isEmpty()||LineNo==1||line.startsWith("#")){
                    results.add(RowResult.skip(LineNo,"跳过：表头/空行/注释"));
                    continue;
                }
                Lines.add(LineNo+"\t"+line);
            }
        }

            List<Future<RowResult>> futures =new ArrayList<>();

            for (String item : Lines) {
                final String raw = item;
                futures.add(pool.submit(new Callable<RowResult>() {
                    @Override
                    public RowResult call() throws Exception {
                        return Executor(raw, Only);
                    }
                }));
            }

            for (Future<RowResult> f : futures) {
                try {
                    results.add(f.get());
                } catch (ExecutionException e) {
                    Throwable curse = e.getCause();
                    results.add(RowResult.fail(-1,"","任务失败；发生系统错误"+curse.getClass().getSimpleName()+"-"+curse.getMessage()));
                }
            }

    }finally {
            pool.shutdown();
            if(!pool.awaitTermination(3, TimeUnit.SECONDS)){
                pool.shutdownNow();
            }
        }
        Collections.sort(results, new java.util.Comparator<RowResult>() {
            @Override
            public int compare(RowResult a,RowResult b) {
                return Integer.compare(a.getLineid(), b.getLineid());
            }
        });

        Writer(results);
        return results;
        }

        private static RowResult Executor(String raw,Set<String> Only){
            int num = raw.indexOf("\t");
            int lineNo = Integer.parseInt(raw.substring(0,num));

            String line = raw.substring(num+1);

            String[] strings = CsvUtils.splitLine(line);

            String code = strings[0].trim();
            String TextArea = strings[1].trim();
            String soilType = strings[2].trim();
            String location = strings[3].trim();
            double area;

            try {
                area = Double.parseDouble(TextArea);
            }catch (NumberFormatException e){
                return RowResult.fail(lineNo,code,"失败：地块面积不是数字"+TextArea);
            }

            if (area<=0){
                return RowResult.fail(lineNo,code,"失败：地块面积小于零"+area);
            }

            if (code.isEmpty()){
                return RowResult.fail(lineNo,code,"失败：地块编号为空"+code);
            }

            if (!Only.add(code)){
                return RowResult.fail(lineNo,code,"失败：地块编号重复"+code);
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return RowResult.fail(lineNo,code,"失败：数据处理错误");
            }

            Plot plot = new Plot(code,area,soilType,location);

            return RowResult.ok(lineNo,code,"成功:"+plot);
        }

        private static void Writer(List<RowResult> results) throws IOException {
        Path okpath = Paths.get("data","plot-ok");
        Path failpath = Paths.get("data","plot-fail");

        try(BufferedWriter okbw = Files.newBufferedWriter(okpath,StandardCharsets.UTF_8);
            BufferedWriter failbw = Files.newBufferedWriter(failpath,StandardCharsets.UTF_8)
        ) {
            okbw.write(CsvUtils.joinLine("lineNo", "plotCode", "message"));
            okbw.newLine();
            failbw.write(CsvUtils.joinLine("lineNo", "plotCode", "message"));
            failbw.newLine();

            for (RowResult result : results){
                String r = result.toString();
                if (result.isSkipper()){
                    continue;
                }
                if (result.isSuccess()){
                    okbw.write(CsvUtils.joinLine(r));
                    okbw.newLine();
                }else {
                    failbw.write(CsvUtils.joinLine(r));
                    failbw.newLine();
                }
            }
        }
            System.out.println("成功任务的汇总报告："+okpath.toAbsolutePath());
            System.out.println("失败任务的汇总报告："+failpath.toAbsolutePath());
    }

}
