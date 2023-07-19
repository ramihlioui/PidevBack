package com.example.pidevback.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.pidevback.entities.Estate;

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

    @Query("SELECT DISTINCT e FROM Estate e WHERE " +
            //AREA
            "(:maxArea IS NULL OR e.area <= :maxArea) " +
            "AND (:minArea IS NULL OR e.area >= :minArea) " +
            //PRICE
            "AND (:maxPrice IS NULL OR e.price <= :maxPrice) " +
            "AND (:minPrice IS NULL OR e.price >= :minPrice) " +
            //ROOMS
            "AND (:maxNbRoom IS NULL OR e.details.nbRoom <= :maxNbRoom) " +
            "AND (:minNbRoom IS NULL OR e.details.nbRoom >= :minNbRoom) " +
            //FLOORS
            "AND (:maxFloor IS NULL OR e.details.floors <= :maxFloor) " +
            "AND (:minFloor IS NULL OR e.details.floors >= :minFloor) "
    )
    List<Estate> searchEstates(
            @Param("maxArea") Long maxArea,
            @Param("minArea") Long minArea,
            @Param("maxPrice") Long maxPrice,
            @Param("minPrice") Long minPrice,
            @Param("maxNbRoom") Long maxNbRoom,
            @Param("minNbRoom") Long minNbRoom,
            @Param("maxFloor") Long maxFloor,
            @Param("minFloor") Long minFloor,
            Pageable pageable
    );

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
