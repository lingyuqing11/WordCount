@echo off
rem -----新建测试结果报告文件-----
echo 测试报告如下：>testReport.txt
echo 格式:>>testReport.txt
echo 	测试用例编号>>testReport.txt
echo 	错误行数>>testReport.txt
echo 	^<测试结果>>testReport.txt
echo 	--------->>testReport.txt
echo 	^>预期结果>>testReport.txt
rem ----------开始测试-----------
rem 空白文件
wc.exe -c -w -l -a test/test1.txt -o result/result1.txt
echo test1:>>testReport.txt
diff -w result/result1.txt result/prediction1.txt>>testReport.txt

rem 只有一个单词，测试能否正确统计各种字符
wc.exe -c -w -l -a test/test2.txt -o result/result2.txt
echo test2:>>testReport.txt
diff -w result/result2.txt result/prediction2.txt>>testReport.txt

rem 单行文件，测试能否能识别不同分隔形式的单词(行末无字符）
wc.exe -c -w -l -a test/test3.txt -o result/result3.txt
echo test3:>>testReport.txt
diff -w result/result3.txt result/prediction3.txt>>testReport.txt

rem 单行文件，测试能否能识别不同分隔形式的单词(行末空格）
wc.exe -c -w -l -a test/test4.txt -o result/result4.txt
echo test4:>>testReport.txt
diff -w result/result4.txt result/prediction4.txt>>testReport.txt

rem 单行文件，测试能否能识别不同分隔形式的单词(行末换行）
wc.exe -c -w -l -a test/test5.txt -o result/result5.txt
echo test5:>>testReport.txt
diff -w result/result5.txt result/prediction5.txt>>testReport.txt

rem 多行文件，测试行数统计是否正确(空白行的计算）
wc.exe -c -w -l -a test/test6.txt -o result/result6.txt
echo test6:>>testReport.txt
diff -w result/result6.txt result/prediction6.txt>>testReport.txt

rem 多行文件，测试代码行，注释行，空白行的统计
wc.exe -c -w -l -a test/test7.txt -o result/result7.txt
echo test7:>>testReport.txt
diff -w result/result7.txt result/prediction7.txt>>testReport.txt

rem 测试停用词功能
wc.exe -c -w -l -a test/test8.txt -o result/result8.txt -e stop.txt
echo test8:>>testReport.txt
diff -w result/result8.txt result/prediction8.txt>>testReport.txt

rem 测试遍历子目录
wc.exe -c -w -l -a -s test/*.txt -o result/result9.txt
echo test9:>>testReport.txt
rem diff -w result/result9.txt result/prediction9.txt>>testReport.txt

rem 测试不同文件类型的统计正确性
wc.exe -c -w -l -a -s *.c -o result/result10.txt
echo test10:>>testReport.txt
diff -w result/result10.txt result/prediction10.txt>>testReport.txt

echo finish testing!
pause