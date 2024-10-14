package com.jgen.services;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelUtil {

    public static byte[] createExcelFile(List<?> data, Class<?> clazz) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            EasyExcel.write(outputStream, clazz).sheet("Feuil1").doWrite(data);
            return outputStream.toByteArray();
        }
    }

    public static byte[] createExcelFile(List<?> data1, Class<?> clazz1, List<?> data2, Class<?> clazz2) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ExcelWriterBuilder writerBuilder = EasyExcel.write(outputStream);

            // Écrire la première feuille
            WriteSheet sheet1 = EasyExcel.writerSheet("Planification").head(clazz1).build();
            writerBuilder.sheet().doWrite(data1);

            // Écrire la deuxième feuille
            WriteSheet sheet2 = EasyExcel.writerSheet("Suivi").head(clazz2).build();
            writerBuilder.sheet().doWrite(data2);

            writerBuilder.build().finish();

            return outputStream.toByteArray();
        }
    }
}
