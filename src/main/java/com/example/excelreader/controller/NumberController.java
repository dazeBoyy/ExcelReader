package com.example.excelreader.controller;

import com.example.excelreader.service.NumberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NumberController {
    private final NumberService numberService;

    public NumberController(NumberService numberService) {
        this.numberService = numberService;
    }

    @Operation(summary = "Получить N-ное максимальное число из файла")
    @GetMapping("/nth-max")
    public ResponseEntity<?> getNthMax(
            @Parameter(
                    description = "Путь к файлу .xlsx",
                    schema = @Schema(defaultValue = "/app/data/test1.xlsx"), // Значение по умолчанию
                    example = "/app/data/test1.xlsx"
            ) @RequestParam String filePath,
            @Parameter(
                    description = "Число N",
                    schema = @Schema(defaultValue = "2"), // Значение по умолчанию
                    example = "2"
            )
            @RequestParam int n)  {
        try {
            int result = numberService.findNthMaxFromFile(filePath, n);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}