package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.projections.SummaryMinProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true, value = "SELECT TB_SELLER.NAME AS sellerName, SUM(TB_SALES.AMOUNT) AS total " +
            "FROM TB_SALES " +
            "INNER JOIN TB_SELLER " +
            "ON TB_SALES.SELLER_ID = TB_SELLER.ID " +
            "WHERE TB_SALES.DATE BETWEEN :minDate AND :maxDate " +
            "GROUP BY  TB_SELLER.NAME ")
    List<SummaryMinProjection> summaryRepository(String minDate, String maxDate);

    @Query("SELECT new com.devsuperior.dsmeta.dto.ReportDTO(obj.id, obj.date, obj.amount, objSeller.name) " +
            "FROM Sale obj " +
            "INNER JOIN obj.seller objSeller " +
            "WHERE obj.date BETWEEN :minDate AND :maxDate " +
            "AND UPPER(objSeller.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<ReportDTO> reportRepository(String name,LocalDate minDate, LocalDate maxDate, Pageable pageable);

}


