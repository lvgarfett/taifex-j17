package com.oneonetrade.fx.repository;

import com.oneonetrade.fx.entity.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<CollectionEntity, Long> {

    @Query(value = "SELECT *\n" +
            "FROM collection\n" +
            "WHERE PARSEDATETIME(BTRIM((fxrate).\"Date\", '\"'), 'yyyyMMdd') " +
            "BETWEEN PARSEDATETIME(:startDate, 'yyyyMMdd') " +
            "AND PARSEDATETIME(:endDate, 'yyyyMMdd') ;", nativeQuery = true)
    List<CollectionEntity> findByDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
