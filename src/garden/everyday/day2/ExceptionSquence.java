package garden.everyday.day2;

import garden.aicode.day2.Plot;

public class ExceptionSquence {
    public static void main(String[] args) {

        System.out.println("当return在try中时：");
        RunReturnintry();

        System.out.println();

        System.out.println("当return在catch中时：");
        RunReturnincatch();

        System.out.println();

        System.out.println("当return在finally中时(仅练习)：");
        RunReturninfinally();

    }
    private static void RunReturnintry(){
        System.out.println("获取到返回值："+ Returnintry());
    }
    private static String Returnintry(){
        try {
            System.out.println("try将会被返回“已受理”");
            return "已受理";
        }finally {
            System.out.println("finally:这句始终会执行");
        }
    }

    private static void RunReturnincatch(){
        System.out.println("获取到返回值："+ Returnincatch());
    }
    private static String Returnincatch(){
        try {
            System.out.println("try:测试return在catch里时的执行顺序");
            throw  new  IllegalArgumentException("申请被拒绝");
        }catch (IllegalArgumentException e){
            System.out.println("catch将会返回“被拒绝”");
            return "被拒绝";
        }finally {
            System.out.println("finally:这句始终会执行");
        }
    }
    private static void RunReturninfinally(){
        System.out.println("获取到返回值："+ Returninfinally());
    }
    private static String Returninfinally(){
        try {
            System.out.println("try:下面会抛出异常，测试finally中return的后果");
            throw new IllegalArgumentException("数据库连接断了");
        }catch (IllegalArgumentException e){
            System.out.println("catch:catch的返回指会消失，仅有finally的返回值");
            return "数据库连接断了";
        }finally {
            System.out.println("finally:如return写在finally中，异常和返回值将会消失");
            return "异常消失了，但并没有被解决";
        }

    }
}
