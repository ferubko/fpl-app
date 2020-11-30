package com.svf.fpl.edu.repositories;

import com.svf.fpl.edu.entity.BotUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface BotUserRepository extends CrudRepository<BotUser, Long> {
    Optional<BotUser> findByChatId(Long chatId);

    @Transactional
    @Modifying
    @Query("update BotUser u set u.allowedToReceiveMessage = :isAllowed where u.id =:id")
    void updateUserBotPrivileges(@Param("isAllowed") boolean isAllowed,
                                 @Param("id") Long id);
}
