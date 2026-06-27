package com.dacuoa.repository;

import com.dacuoa.model.Inspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InspectionRepository extends JpaRepository<Inspection, Long> {
    Optional<Inspection> findByBikeSerialNumber(String bikeSerialNumber);

    @Query("SELECT i FROM Inspection i WHERE LOWER(i.inspectorName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(i.bikeSerialNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Inspection> searchByInspectorNameOrSerialNumber(@Param("searchTerm") String searchTerm);
}
