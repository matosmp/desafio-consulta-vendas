package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.projections.SummaryMinProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;

    public SaleMinDTO findById(Long id) {
        Optional<Sale> result = repository.findById(id);
        Sale entity = result.get();
        return new SaleMinDTO(entity);
    }

    public List<SummaryDTO> summaryService(String minDate, String maxDate) {
        minDate = minDate;
        maxDate = maxDate;

        if (minDate.isBlank() && maxDate.isBlank()) {
            LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
            LocalDate result2 = today.minusYears(1L);
            maxDate = String.valueOf(today);
            minDate = String.valueOf(result2);
        }
        List<SummaryMinProjection> list = repository.summaryRepository(minDate, maxDate);
        List<SummaryDTO> result = list.stream().map(x -> new SummaryDTO(x)).toList();

        return result;
    }

    public Page<ReportDTO> reportService(String minDate, String maxDate, String name, Pageable pageable) {
        minDate = minDate;
        maxDate = maxDate;

        if(minDate.isBlank() && maxDate.isBlank()){
            LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
            LocalDate result2 = today.minusYears(1L);
            maxDate = String.valueOf(today);
            minDate = String.valueOf(result2);
        }

        Page<ReportDTO> result = repository.reportRepository(name, LocalDate.parse(minDate), LocalDate.parse(maxDate), pageable);

        return result;
    }


}
