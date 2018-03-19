import ParameterException.*;

import java.io.*;

public class Count {
    /*参数标识*/
    static int charFlag=0;
    static int wordFlag=0;
    static int lineFlag=0;
    static int outputFlag=0;
    /*IO文件名*/
    static String fileName;

    /*解析参数
    * @param arg :要解析的参数
    * @return int :代表参数种类的整型数（0:[file],1:[-c],2:[-w],3:[-l],4:[-o])
    * */
    public static int parameter(String arg)throws ParameterDuplicateException{
        int type;
        switch (arg) {
            case "-c": { //统计字符
                if (charFlag == 1) {
                    throw new ParameterDuplicateException();
                } else {
                    charFlag = 1;
                    type = 1;
                }
                break;
            }
            case "-w":{ //统计单词
                if (wordFlag == 1) {
                    throw new ParameterDuplicateException();
                } else {
                    wordFlag = 1;
                    type = 2;
                }
                break;
            }
            case "-l":{ //统计行数
                if (lineFlag == 1) {
                    throw new ParameterDuplicateException();
                } else {
                    lineFlag = 1;
                    type = 3;
                }
                break;
            }
            case "-o":{ //输出到指定文件
                if (outputFlag == 1) {
                    throw new ParameterDuplicateException();
                } else {
                    outputFlag = 1;
                    type = 4;
                }
                break;
            }
            default:{ //文件名
                fileName = arg;
                type = 0;
            }
        }
        return type;
    }

    public static void main(String[] args)throws NoParameterException{

        int paramType=-1; //参数类型
        int paramNum=args.length; //参数个数
        String inputFile=""; //输入文件名
        String outputFile=""; //输出文件名
        if(0==paramNum){ //缺少输入参数
            throw new NoParameterException();
        }
        else{
            for(int i=0;i<paramNum;i++){
                try{
                    /*读取-o参数后的输出文件名*/
                    if(paramType==4){

                        paramType=parameter(args[i]);
                        if(paramType!=0){
                            throw new OutputOnlyException(); //-o参数后没有跟文件名
                        }
                        else{
                            outputFile=fileName;
                        }
                    }
                    /*读取其他参数*/
                    else {
                        paramType = parameter(args[i]);
                        if (paramType == 0) {   //参数为文件名
                            inputFile = fileName;
                        }
                    }
                }catch (ParameterDuplicateException e1){
                    System.out.println(e1.getMessage());
                }
                catch (OutputOnlyException e2){
                    System.out.println(e2.getMessage());
                }

            }
        }

        int charNum=0; //字符数
        int wordNum=0; //单词数
        int lineNum=1; //行数
        try {
            FileInputStream fis = new FileInputStream(inputFile);  //字节流
            InputStreamReader isr=new InputStreamReader(fis,"utf-8");  //字符流
            int c=isr.read(); //当前字符
            int before=-1; //前一个字符
            while(c!=-1){
                charNum++;
                try {

                   // System.out.print((char)c);
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
            System.out.println("Can't find file '"+inputFile+"'");
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
        /*控制台输出结果*/
        if(outputFlag==0){
            if (charFlag == 1) {
                System.out.println(inputFile+",字符数："+charNum);
            }
            if (wordFlag == 1) {
                System.out.println(inputFile+",单词数："+wordNum);
            }
            if (lineFlag == 1) {
                System.out.println(inputFile+",行数："+lineNum);
            }
        }
        /*根据-o参数将结果写入文件*/
        else{
            try{
                File file=new File(outputFile);
                if(!file.exists()){ //找不到文件时创建新文件
                    file.createNewFile();
                }
                FileWriter out=new FileWriter(outputFile);
                BufferedWriter bw=new BufferedWriter(out);
                String result="";
                if (charFlag == 1) {
                    result=inputFile+",字符数："+charNum+"\r\n";
                }
                if (wordFlag == 1) {
                    result=result+inputFile+",单词数："+wordNum+"\r\n";
                }
                if (lineFlag == 1) {
                    result=result+inputFile+",行数："+lineNum+"\r\n";
                }
                bw.write(result); //文件覆盖式写入
                bw.close();
                out.close();
            }catch (FileNotFoundException e){
                e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
