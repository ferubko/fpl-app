package com.svf.fpl.edu.repositories;

import com.svf.fpl.edu.entity.Footballer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FootballerRepository extends CrudRepository<Footballer, Long> {
    @Transactional
    @Modifying
    @Query("update Footballer f set f.isCaptain = :isCaptain where f.elementId =:elementId")
    void updateFootballerPicks(@Param("isCaptain") boolean isCaptain,
                               @Param("elementId") Long elementId);

    @Transactional
    @Modifying
    @Query("update Footballer f set f.chancePlayingNextGame=:nextGame, f.news=:news, f.totalPoints=:totalPoints, f.pointsPerGame=:pointsPerGame where f.elementId =:elementId")
    void updateFootballerData(
            @Param("nextGame") String nextGame,
            @Param("news") String news,
            @Param("totalPoints") Integer totalPoints,
            @Param("pointsPerGame") Double pointsPerGame,
            @Param("elementId") Long elementId);
}
