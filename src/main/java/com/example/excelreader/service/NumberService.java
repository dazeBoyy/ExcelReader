package com.example.excelreader.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.PriorityQueue;

@Service
public class NumberService {
    public int findNthMaxFromFile(String filePath, int n) throws IOException {
        try (FileInputStream file = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            PriorityQueue<Integer> minHeap = new PriorityQueue<>();

            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                    int num = (int) cell.getNumericCellValue();
                    if (minHeap.size() < n) {
                        minHeap.offer(num);
                    } else if (num > minHeap.peek()) {
                        minHeap.poll();
                        minHeap.offer(num);
                    }
                }
            }

            if (minHeap.size() < n) {
                throw new IllegalArgumentException("В файле недостаточно чисел");
            }

            return minHeap.peek();
        } catch (IOException e) {
            throw new IOException("Ошибка чтения файла: " + e.getMessage());
        }
    }
}
