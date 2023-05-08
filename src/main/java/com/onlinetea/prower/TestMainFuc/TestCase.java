package com.onlinetea.prower.TestMainFuc;

import java.io.*;

public class TestCase {
    public static void main(String[] args) {
        try {


            String content = TestCase.readTxt(new File("D:\\OtherTools\\微软语音合成\\source\\蛊真人分段-310579.txt"));

            String[] cons = content.split("------------");


      for(int i=0;i<60;i++){
        File target =
            new File(
                "D:\\OtherTools\\微软语音合成\\source\\蛊真人-detali\\" + "蛊真人-第" + (i + 1) + "章.txt");
                BufferedWriter bw = new BufferedWriter(new FileWriter(target));
                StringBuffer sb = new StringBuffer();
                write(cons[i], bw);
            }

        } catch (IOException e) {
            e.getMessage();
      }

    }

    /**
     * 读文件
     *
     * @param file 文件
     * @return
     * @throws IOException
     * @throws IOException
     */
    public static String readTxt(File file) throws IOException, IOException {
        String s = "";
        InputStreamReader in = new InputStreamReader(new FileInputStream(file), "UTF-8");
        BufferedReader br = new BufferedReader(in);
        StringBuffer content = new StringBuffer();
        while ((s = br.readLine()) != null) {
            content = content.append(s);
        }
        return content.toString();
    }

    //将读取的路径以及相应的内容写入指定的文件

    private static void write(String str, Writer writer){

        try {

            writer.write(str);

        } catch (Exception e) {

            e.printStackTrace();

        }finally{

            try {

                if(writer!=null)

                    writer.close();

            } catch (Exception e2) {

                e2.printStackTrace();

            }

        }

    }
}
