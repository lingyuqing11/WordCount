import java.io.*;

public class Count {
    public static void main(String[] args)throws NoParameterException{
        /*
        try{
            if(0==args.length){
                throw new IllegalArgumentException("输入数据");
            }
            for(int i=0;i<args.length;i++){
                System.out.println(args[i]);
            }
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        */
        int charFlag=0;
        int wordFlag=0;
        int lineFlag=0;
        if(0==args.length){
            throw new NoParameterException();
        }


        int charNum=0; //字符数
        int wordNum=0; //单词数
        int lineNum=1; //行数
        try {
            FileInputStream fis = new FileInputStream("test.txt");  //字节流
            InputStreamReader isr=new InputStreamReader(fis,"utf-8");  //字符流
            int c=isr.read(); //当前字符
            int before=-1; //前一个字符
            while(c!=-1){
                charNum++;
                try {

                    System.out.print((char)c);
                    if(c==' '||c==','||c=='\n'||c=='\t'){ //以空格，逗号，换行和制表符分割单词
                        //排除连续的分隔符
                        if(before!=-1&&before!=' '&&before!=','&&before!='\n'&&before!='\t') {
                            wordNum++;
                        }
                        if(c=='\n'){ //遇到换行符计算行数
                            lineNum++;
                        }
                    }
                    before=c; //更新前一个字符的值
                    c = isr.read(); //读取下一个字符

                }catch (IOException e){
                    System.out.println(e.getMessage());
                }
            }
            if(charNum!=0&&before!=' '&&before!=','&&before!='\n'&&before!='\t'){ //计算最后一个单词后没有分隔符的情况
                wordNum++;
            }

        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        catch (UnsupportedEncodingException e2){
            System.out.println(e2.getMessage());
        }
        catch (IOException e3){
            System.out.println(e3.getMessage());
        }
        /* 不确定空白文档算不算一行
        if(charNum==0){
            lineNum=0;
        }
        */
        System.out.println("\n字符数："+charNum);
        System.out.println("单词数："+wordNum);
        System.out.println("行数："+lineNum);

    }
}
