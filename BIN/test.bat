@echo off
rem -----�½����Խ�������ļ�-----
echo ���Ա������£�>testReport.txt
echo ��ʽ:>>testReport.txt
echo 	�����������>>testReport.txt
echo 	��������>>testReport.txt
echo 	^<���Խ��>>testReport.txt
echo 	--------->>testReport.txt
echo 	^>Ԥ�ڽ��>>testReport.txt
rem ----------��ʼ����-----------
rem �հ��ļ�
wc.exe -c -w -l -a test/test1.txt -o result/result1.txt
echo test1:>>testReport.txt
diff -w result/result1.txt result/prediction1.txt>>testReport.txt

rem ֻ��һ�����ʣ������ܷ���ȷͳ�Ƹ����ַ�
wc.exe -c -w -l -a test/test2.txt -o result/result2.txt
echo test2:>>testReport.txt
diff -w result/result2.txt result/prediction2.txt>>testReport.txt

rem �����ļ��������ܷ���ʶ��ͬ�ָ���ʽ�ĵ���(��ĩ���ַ���
wc.exe -c -w -l -a test/test3.txt -o result/result3.txt
echo test3:>>testReport.txt
diff -w result/result3.txt result/prediction3.txt>>testReport.txt

rem �����ļ��������ܷ���ʶ��ͬ�ָ���ʽ�ĵ���(��ĩ�ո�
wc.exe -c -w -l -a test/test4.txt -o result/result4.txt
echo test4:>>testReport.txt
diff -w result/result4.txt result/prediction4.txt>>testReport.txt

rem �����ļ��������ܷ���ʶ��ͬ�ָ���ʽ�ĵ���(��ĩ���У�
wc.exe -c -w -l -a test/test5.txt -o result/result5.txt
echo test5:>>testReport.txt
diff -w result/result5.txt result/prediction5.txt>>testReport.txt

rem �����ļ�����������ͳ���Ƿ���ȷ(�հ��еļ��㣩
wc.exe -c -w -l -a test/test6.txt -o result/result6.txt
echo test6:>>testReport.txt
diff -w result/result6.txt result/prediction6.txt>>testReport.txt

rem �����ļ������Դ����У�ע���У��հ��е�ͳ��
wc.exe -c -w -l -a test/test7.txt -o result/result7.txt
echo test7:>>testReport.txt
diff -w result/result7.txt result/prediction7.txt>>testReport.txt

rem ����ͣ�ôʹ���
wc.exe -c -w -l -a test/test8.txt -o result/result8.txt -e stop.txt
echo test8:>>testReport.txt
diff -w result/result8.txt result/prediction8.txt>>testReport.txt

rem ���Ա�����Ŀ¼
wc.exe -c -w -l -a -s test/*.txt -o result/result9.txt
echo test9:>>testReport.txt
rem diff -w result/result9.txt result/prediction9.txt>>testReport.txt

rem ���Բ�ͬ�ļ����͵�ͳ����ȷ��
wc.exe -c -w -l -a -s *.c -o result/result10.txt
echo test10:>>testReport.txt
diff -w result/result10.txt result/prediction10.txt>>testReport.txt

echo finish testing!
pause