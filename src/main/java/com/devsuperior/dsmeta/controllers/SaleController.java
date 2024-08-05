package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

    @Autowired
    private SaleService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
        SaleMinDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/summary")
    public ResponseEntity<List<SummaryDTO>> getSummary(
            @RequestParam(value = "minDate", required = false, defaultValue = "") String minDate,
            @RequestParam(value = "maxDate", required = false, defaultValue = "") String maxDate) {
        List<SummaryDTO> result = service.summaryService(minDate, maxDate);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/report")
    public ResponseEntity<Page<ReportDTO>> getReport(
            @RequestParam(name = "minDate", required = false, defaultValue = "") String minDate,
            @RequestParam(name = "maxDate", required = false, defaultValue = "") String maxDate,
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            Pageable pageable) {
        Page<ReportDTO> list = service.reportService(minDate, maxDate, name, pageable);
        return ResponseEntity.ok(list);
    }


}
