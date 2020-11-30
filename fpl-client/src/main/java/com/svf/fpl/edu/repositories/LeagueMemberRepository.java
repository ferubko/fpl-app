package com.svf.fpl.edu.repositories;

import com.svf.fpl.edu.entity.LeagueMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LeagueMemberRepository extends CrudRepository<LeagueMember, Long> {
    Optional<LeagueMember> findByEntry(Long aLong);

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    @Modifying(clearAutomatically = true)
//    @Query("update LeagueMember lm set lm.total=:total, lm.points=:points, lm.currentEvent=:currentEvent where lm.entry =:entry")
//    void updateFootballerData(
//            @Param("total") Integer total,
//            @Param("points") Integer points,
//            @Param("currentEvent") Integer currentEvent,
//            @Param("entry") Long entry);
}
