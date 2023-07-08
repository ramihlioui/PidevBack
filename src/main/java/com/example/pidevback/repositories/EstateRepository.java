package com.example.pidevback.repositories;

import com.example.pidevback.dto.HeatmapDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.pidevback.entities.Estate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface EstateRepository extends JpaRepository<Estate, Long> {
    String _query = "SELECT COUNT(*) AS count, AVG(c.lat) AS lat, AVG(c.lng) AS lng, c.cluster FROM ( "
            + "SELECT id, "
            + "ST_ClusterKMeans(ST_MakePoint(latitude, longitude),4) OVER() as cluster, "
            + "ST_X(ST_Centroid(ST_collect(ST_MakePoint(latitude, longitude)))) AS lng, "
            + "ST_Y(ST_Centroid(ST_collect(ST_MakePoint(latitude, longitude)))) AS lat, "
            + "ST_ASTEXT(ST_Centroid(ST_collect(ST_MakePoint(latitude, longitude)))) AS center "
            + "FROM location GROUP BY id"
            + " ) c GROUP BY c.cluster";

    @Modifying
    @Query(value = _query, nativeQuery = true)
    List<Object[]> findHeatmapData();

    List<Estate> findByOwnerId(Long id, final Pageable pageable);

    Estate findByOwnerIdAndId(Long ownerId, Long id);

    List<Estate> findByLocation_LatitudeBetweenAndLocation_LongitudeBetween(
            Long minLat,
            Long maxLat,
            Long minLong,
            Long maxLong,
            final Pageable pageable
    );
}
