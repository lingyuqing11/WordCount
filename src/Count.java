import ParameterException.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Count {
    /*参数标识，0:未设置该参数，1:已设置该参数*/
    static int charFlag=0;
    static int wordFlag=0;
    static int lineFlag=0;
    static int outputFlag=0;
    static int aFlag=0;
    static int sFlag=0;
    static int eFlag=0;
    /*IO文件名*/
    static String fileName;
    static String inputFile=""; //输入文件名
    static String outputFile=""; //输出文件名
    static String stopFile=""; //停用词文件名


    /*解析参数
    * @param arg :要解析的参数
    * @return int :代表参数种类的整型数（0:[file],1:[-c],2:[-w],3:[-l],4:[-o],5:[-a],6:[-s],7:[-e])
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
            case "-a":{
                if(aFlag==1){
                    throw new ParameterDuplicateException();
                }else{
                    aFlag=1;
                    type=5;
                }
                break;
            }
            case "-s":{
                if(sFlag==1){
                    throw new ParameterDuplicateException();
                }else{
                    sFlag=1;
                    type=6;
                }
                break;
            }
            case "-e":{
                if(eFlag==1){
                    throw new ParameterDuplicateException();
                }else{
                    eFlag=1;
                    type=7;
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

    /*统计单个文件的字符、单词、行数
    * @param:inputfile 输入文件
    * */
    public static void count(File inputFile){
         /*统计变量*/
        int charNum=0; //字符数
        int wordNum=0; //单词数
        int lineNum=1; //行数
        int codeInLine=0; //一行内的代码字符数
        int codeLine=0; //代码行数
        int blankLine=0; //空白行数
        int commentLine=0; //注释行数
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
        /*获取停用词列表*/
        ArrayList<String> stopList=new ArrayList<>();
        if(eFlag==1){
            try {
                BufferedReader bufReader = new BufferedReader(new FileReader(stopFile));
                String line; //记录文件的一行
                while ((line = bufReader.readLine())!= null) {
                    Scanner scanner=new Scanner(line);
                    while (scanner.hasNext()){
                        stopList.add(scanner.next());
                    }
                }
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
            /*
            System.out.println("stoplist:");
            for(String w:stopList){
                System.out.printf(w+" ");
            }
            */
        }
        /*统计代码行，空白行，注释行*/
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String line;
            int blockCommentFlag=0; //区块注释标志
            while ((line=br.readLine())!=null){
                line=line.trim(); //去掉字符串前后的空白
                /*剔除停用词表中的单词*/
                if(eFlag==1){
                    for(String stopWord:stopList){ //遍历停用词表中的每个词
                        Pattern pattern[]=new Pattern[4];
                        pattern[0]=Pattern.compile("(\\s|,)"+stopWord+"(\\s|,)");     //与停用词完全匹配的单词(左右有间隔，位于一行的中间)
                        pattern[1]=Pattern.compile("^"+stopWord+"$");                  //与停用词完全匹配的单词(左右无间隔，一行只有一个单词)
                        pattern[2]=Pattern.compile("^"+stopWord+"(\\s|,)");       //与停用词完全匹配的单词(左侧无间隔，位于一行的开头)
                        pattern[3]=Pattern.compile("(\\s|,)"+stopWord+"$");       //与停用词完全匹配的单词(右侧无间隔，位于一行的结尾)
                        Matcher m[]=new Matcher[4];
                        for(int i=0;i<4;i++){   //初始化Matcher类
                            m[i]=pattern[i].matcher(line);
                        }

                        for(int i=0;i<4;i++) { //匹配
                            while (m[i].find()) {
                                wordNum--;
                            }
                        }
                    }
                }
                /*统计三种行数*/

                if(line.length()<2){
                    if(!line.startsWith("*")) {
                        blankLine++; //空白行

                    }
                    else{
                        commentLine++; //注释行（*）
                    }

                }
                else if(line.startsWith("//")){ //匹配："//XXXXX"
                    commentLine++;
                }
                else if(line.startsWith("/*")){ //匹配区块注释："/*XXXXX or /*XXXXX*/"
                    commentLine++;
                    if(!line.endsWith("*/")) {
                        blockCommentFlag = 1;    //表示区块注释未结束
                    }
                }
                else if(line.startsWith("*")&&line.substring(1).startsWith("/")){
                    blockCommentFlag=0;
                    if(line.substring(2).trim().length()>1) {
                        codeLine++; //匹配："*/XXXXXX"
                    }
                    else{
                        commentLine++; //匹配："*/X"
                    }
                }
                else if(line.endsWith("*/")){
                    // 用于区别"XXX       和 "/*
                    //         XXXXX*/"       XXXXX*/"
                    if(blockCommentFlag==1){
                        commentLine++;
                    }
                    else if(line.substring(1).startsWith("/*")){ //匹配："X/*XXXXXX*/"
                        commentLine++;
                    }
                    else{
                        codeLine++;
                    }
                }
                else {
                    /*一个字符加注释*/
                    String subLine=line.substring(1);
                    subLine=subLine.trim();
                    if(subLine.startsWith("//")||subLine.startsWith("/*")){ //接注释
                        commentLine++;
                    }
                    else{
                        codeLine++;
                    }
                }
            }
        }
        catch (FileNotFoundException e1){
            System.out.println(e1.getMessage());
        }
        catch (IOException e2){
            System.out.println(e2.getMessage());
        }
        /*
         * **********************************输出部分***************************************/
        String fname=inputFile.getName();
        /*控制台输出结果*/
        if(outputFlag==0){
            if (charFlag == 1) {
                System.out.println(fname+",字符数："+charNum);
            }
            if (wordFlag == 1) {
                System.out.println(fname+",单词数："+wordNum);
            }
            if (lineFlag == 1) {
                System.out.println(fname+",行数："+lineNum);
            }
            if(aFlag==1){
                System.out.println(fname+",codeLine/blankLine/commentLine: "+codeLine+"/"+blankLine+"/"+commentLine);
            }
        }
        /*根据-o参数将结果写入文件*/
        else {
            try {
                File file = new File(outputFile);
                if (!file.exists()) { //找不到文件时创建新文件
                    file.createNewFile();
                }
                FileWriter out = new FileWriter(outputFile);
                BufferedWriter bw = new BufferedWriter(out);
                String result = "";
                if (charFlag == 1) {
                    result = fname + ",字符数：" + charNum + "\r\n";
                }
                if (wordFlag == 1) {
                    result = result + fname + ",单词数：" + wordNum + "\r\n";
                }
                if (lineFlag == 1) {
                    result = result + fname + ",行数：" + lineNum + "\r\n";
                }
                if(aFlag==1){
                    result=result+fname+",代码行/空行/注释行: "+codeLine+"/"+blankLine+"/"+commentLine+"\r\n";
                }
                bw.write(result); //文件覆盖式写入
                bw.close();
                out.close();
            } catch (FileNotFoundException e) {
                e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /*获取当前目录下符合条件的文件名
    * @param1 path:遍历起始的路径
    * @param2 filter：筛选文件名的正则表达式
    * */
    public static void getFile(String presentPath,String filter)throws NullPointerException{
        File path=new File(presentPath); //当前目录
        File[] files = path.listFiles(); //获取当前目录下的路径列表
        Pattern pattern=Pattern.compile(filter);   //文件的筛选条件
        if(files==null){
            throw new NullPointerException("no matched file!");
        }
        else {
            for (File f : files) {
                if (!f.isDirectory()) { //对于文件
                    if (pattern.matcher(f.getName()).matches()) { //与正则表达式匹配
                        count(f);
                        System.out.println("match file:" + f.getName());
                    }
                } else {  //对于文件夹，进行递归遍历
                    getFile(f.getPath(), filter);
                }
            }
        }
    }
    /*主程序
    * @param args[]：从控制台读入，控制程序功能，指定输入输出文件
    *    -c:统计字符数
    *    -w:统计单词数
    *    -l:统计行数
    *    -o [outputfile]:结果输出到outputfile
    *    -a:统计代码行，空白行，注释行
    *    -s:遍历指定目录下符合要求的所有文件
    *    -e [stoplist]:统计单词时忽略stoplist中单词
    *    [inputfile]:被统计文件名
    * */
    public static void main(String[] args)throws NoParameterException{

        int paramType=-1; //参数类型
        int paramNum=args.length; //参数个数

        /*
        * ***************   解析参数  **********************/
        if(0==paramNum){ //缺少输入参数
            throw new NoParameterException();
        }
        else{
            for(int i=0;i<paramNum;i++){
                try{

                    if(paramType==4){ //读取-o参数后的输出文件名
                        paramType=parameter(args[i]);
                        if(paramType!=0){
                            throw new NoFileNameException(); //-o参数后没有跟文件名
                        }
                        else{
                            outputFile=fileName;
                        }
                    }
                    else if(paramType==7){ //读取-e参数后的输出文件名
                        paramType=parameter(args[i]);
                        if(paramType!=0){
                            throw new NoFileNameException(); //-e参数后没有跟文件名
                        }
                        else{
                            stopFile=fileName;
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
                catch (NoFileNameException e2){
                    System.out.println(e2.getMessage());
                }
            }
        }
        /*
        * ****************  统计操作部分 *********************/
        if(sFlag==1){  //有-s参数
            String fileType; //批量统计的文件类型
            String startPath; //起始文件路径
            fileType=inputFile.substring(inputFile.indexOf('.')+1); //获取文件类型
            if(inputFile.indexOf('*')>0) {
                startPath = inputFile.substring(0, inputFile.indexOf('*')); //获取文件路径
            }
            else {
                startPath=".";
            }
            try {
                getFile(startPath, ".*\\." + fileType + "$");
            }catch (NullPointerException e){  //指定目录下无符合条件的文件可以统计
                System.out.println(e.getMessage());
            }
        }
        else{  //无-s参数
            File f=new File(inputFile);
            count(f);
        }


    }
}
