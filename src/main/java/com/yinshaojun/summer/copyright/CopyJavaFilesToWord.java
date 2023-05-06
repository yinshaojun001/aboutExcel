package com.yinshaojun.summer.copyright;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class CopyJavaFilesToWord {
    public static void main(String[] args) {
        String projectPath = "E:\\XINMEI\\gfi\\gfi\\app\\core\\src\\main\\java\\com\\xinmei\\gfi\\management\\business\\activity\\service";
        String outputPath = "E:\\XINMEI\\赠险管理系统软件著作权源代码.docx";

        try (XWPFDocument document = new XWPFDocument()) {
            List<File> javaFiles = findJavaFiles(new File(projectPath));
            for (File javaFile : javaFiles) {
                List<String> lines = getLines(javaFile);
                for (String line : lines) {
                    XWPFParagraph paragraph = document.createParagraph();
                    paragraph.createRun().setText(line);
                }
            }

            try (FileOutputStream outputStream = new FileOutputStream(outputPath)) {
                document.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<File> findJavaFiles(File directory) {
        List<File> javaFiles = new ArrayList<>();
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                javaFiles.addAll(findJavaFiles(file));
            } else if (file.getName().endsWith(".java")) {
                javaFiles.add(file);
            }
        }
        return javaFiles;
    }

    private static List<String> getLines(File javaFile) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(javaFile)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
