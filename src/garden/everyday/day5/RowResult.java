package garden.everyday.day5;

public class RowResult {

    private final int Lineid;
    private final String plotcode;
    private final boolean success;
    private final boolean skipper;
    private final String massage;

    public RowResult(int lineid, boolean success, String plotcode, boolean skipper, String massage) {
        Lineid = lineid;
        this.success = success;
        this.skipper = skipper;
        this.plotcode = plotcode;
        this.massage = massage;
    }

    public static RowResult ok(int lineid,String plotcode,String massage){
        return new RowResult(lineid,true,plotcode,false,massage);
    }

    public static RowResult fail(int lineid,String plotcode,String massage){
        return new RowResult(lineid,false,plotcode,false,massage);
    }

    public static RowResult skip(int lineid,String massage){
        return new RowResult(lineid,false,"",true,massage);
    }

    public int getLineid() {
        return Lineid;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getPlotcode() {
        return plotcode;
    }

    public boolean isSkipper() {
        return skipper;
    }

    public String getMassage() {
        return massage;
    }

    @Override
    public String toString() {
        return "第"+getLineid()+"个任务"+"地块编号："+getPlotcode()+
                (isSkipper()?"跳过":(isSuccess()?"成功":"失败"))
                +getMassage();

    }
}
