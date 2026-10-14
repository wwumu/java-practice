package garden.everyday.day4;

import java.text.Format;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Excutor {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("线程练习");
        excutor();
    }


    private static void excutor() throws InterruptedException {
        List<String> code = new ArrayList<>();

        for (int i=0;i<5;i++){
            code.add(String.format("A-%02d",i));
        }

        ExecutorService e = Executors.newFixedThreadPool(3);
        AtomicInteger num = new AtomicInteger();
        List<Future<String>> futures = new ArrayList<>();

        try {
            for (String c : code) {
                Future future = e.submit(
                        new Callable<String>() {
                            @Override
                            public String call() throws Exception {

                                int n = num.incrementAndGet();
                                //模拟线程所需时间
                                Thread.sleep(1000);
                                if (code.indexOf(c)>3){
                                    throw new RuntimeException("模拟线程抛出异常");
                                }
                                System.out.println("地块载入成功，地块名：" + c + "线程编号：" + Thread.currentThread().getName() + "这是第" + n + "个线程");
                                return c + "校验通过";
                            }

                        }
                );
                futures.add(future);
            }
        }finally {
            e.shutdown();
            boolean isshutdow=e.awaitTermination(5, TimeUnit.SECONDS);
            System.out.println("线程是否以关闭："+isshutdow);
        }
        for (Future f : futures){
            try {
                f.get();
                System.out.println("任务"+futures.indexOf(f)+"成功");
            } catch (ExecutionException ex) {
                System.out.println("任务"+futures.indexOf(f)+"失败-"+ex.getCause().getClass().getSimpleName()+ex.getCause().getMessage());
            }
        }
        System.out.println("共执行了"+futures.size()+"个任务");
    }
}
