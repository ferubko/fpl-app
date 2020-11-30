package com.svf.fpl.edu.service;

import com.svf.fpl.edu.entity.BotUser;
import com.svf.fpl.edu.repositories.BotUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BotUserService {
    @Autowired
    private BotUserRepository botUserRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<BotUser> selectBotUsers() {
        log.info("Getting all bot users");
        return (List<BotUser>) botUserRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public BotUser selectBotUserById(Long id) throws RuntimeException {
        log.info("Getting bot user by id {}", id);
        return botUserRepository.findById(id).orElseThrow(() -> new RuntimeException("No such user found"));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public BotUser selectBotUserFromChat(Long chatId) {
        log.info("Getting bot user by chat {}", chatId);
        Optional<BotUser> byChatId = botUserRepository.findByChatId(chatId);
        return byChatId.orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public BotUser saveUserBot(Long chatId, String username) {
        BotUser existUser = selectBotUserFromChat(chatId);
        if (existUser != null) {
            log.warn("User with chat id {} already exist", chatId);
            return existUser;
        }
        BotUser user = BotUser.builder()
                .chatId(chatId)
                .userName(username)
                .allowedToReceiveMessage(false)
                .build();
        BotUser savedUser = botUserRepository.save(user);
        log.info("Saved user with {} chat and username {}", chatId, username);
        return savedUser;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateUserBotPrivileges(BotUser user, Boolean isAllowed) {
//    public void updateUserBotPrivileges(long botUserId, Boolean isAllowed) {
        log.info("Updating user {} to privileges {}", user.getChatId(), isAllowed);
//        log.info("Updating user {} to privileges {}", botUserId, isAllowed);
        Optional<BotUser> existingUserOptional = botUserRepository.findById(user.getId());
        BotUser editedUser = existingUserOptional.orElseThrow();
        editedUser.setAllowedToReceiveMessage(isAllowed);
        botUserRepository.save(editedUser);
//        botUserRepository.updateUserBotPrivileges(isAllowed, user.getId());
        log.info("Finished updating");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void removeUserBot(BotUser user) {
        log.info("Remove user {}", user.getChatId());
        botUserRepository.delete(user);
        log.info("Finished removing");
    }
}
