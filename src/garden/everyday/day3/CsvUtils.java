package garden.everyday.day3;

import java.util.ArrayList;
import java.util.List;

public class CsvUtils {

    private static final char quote = '"';

    public CsvUtils() {
    }

    //读字符串工具，用于去引号并识别引号内的逗号
    public static String[] splitLine(String line){
        List<String> save = new ArrayList<String>();
        StringBuilder piece = new StringBuilder();
        boolean inquote=false;

        for (int i=0;i<line.length();i++){
            char c=line.charAt(i);

            if (inquote){
                if (c==quote){
                    inquote=false;
                }else{
                    piece.append(c);
                }
            }else {
                if (c==quote){
                    inquote=true;
                }else if (c==','){
                    save.add(piece.toString());
                    piece.setLength(0);
                }else {
                    piece.append(c);
                }
            }
        }
        save.add(piece.toString());
        return save.toArray(new String[0]);
    }

    //写入的工具方法，用于加引号
    public static String escape(String raw){
        if (raw==null){
            return "";
        }

        String q = String.valueOf(quote);
        if(raw.indexOf(',')>=0
        ||raw.indexOf('"')>=0
        ||raw.indexOf('\r')>=0
        ||raw.indexOf('\n')>=0){
            String Double=raw.replace(q,q+q);
            return Double=q+Double+q;
        }
        return raw;
    }

    //写入的工具类，将字符按行进行拼接
    public static String joinLine(String... save){
        StringBuilder sb =new StringBuilder();

        for (int i=0;i<save.length;i++){
            if(i>0){
                sb.append(',');
            }
            sb.append(escape(save[i]));
        }
        return sb.toString();
    }

    //将数字转换为字符串类型
    public static String num(double n){return Double.toString(n);}

}



