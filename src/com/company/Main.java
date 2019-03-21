package com.company;

import java.io.*;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
public class Main {


    public static void main(String[] args) throws IOException {
        String bufferLine;
        //boolean flagMode = true;
        String configFile = args[0];
        String textFile = args[1];
        String flagModeArg = args[2];
        String fileNameOut = "outFile.txt";

        Boolean flagMode = Boolean.valueOf(flagModeArg);

        // Прочитать файл конфигурации
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(configFile)); // or file1.txt
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Для чтения текстового файла
        String lineTextFile = readFile(textFile);

        // Дял записи
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileNameOut));

        // Поиск по ключу и замена элемента
        Set<Object> keys = properties.keySet();
        for (Object key : keys) {
            String keyName = (String) key;
            String value = (String) properties.get(key);
            int numbOne = 0;
            int numbTwo = 0;
            StringBuilder result = new StringBuilder();
            while ((numbOne = lineTextFile.indexOf(keyName, numbTwo)) > -1) {
                result.append(lineTextFile, numbTwo, numbOne);
                result.append(value);
                numbTwo = numbOne + keyName.length();
            }

            result.append(lineTextFile, numbTwo, lineTextFile.length());
            bufferLine = result.toString();

            if (bufferLine.contains("'',")) {
                continue;
            } else {
                bufferLine = bufferLine + "\n";
                // Запись результата в файл
                if (!flagMode) {
                    bw.write(bufferLine); // Выполнить режим 1:1 ( одна строка заменяется в одной строке)

                } else {
                    lineTextFile = bufferLine;//
                }
            }

        }
        bw.write(lineTextFile);
        bw.close();
        System.out.println("Done! " + "Create: " + fileNameOut  );

    }

    public static String readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
        return stringBuilder.toString();
    }
}

