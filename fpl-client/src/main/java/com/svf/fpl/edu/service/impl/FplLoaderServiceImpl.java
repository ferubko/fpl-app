package com.svf.fpl.edu.service.impl;

import com.svf.fpl.edu.client.FantasyPremierLeagueClient;
import com.svf.fpl.edu.dto.Elements;
import com.svf.fpl.edu.dto.FullInfo;
import com.svf.fpl.edu.dto.MemberPlayer;
import com.svf.fpl.edu.dto.Picks;
import com.svf.fpl.edu.entity.LeagueMember;
import com.svf.fpl.edu.exeptions.FantasyPremierLeagueException;
import com.svf.fpl.edu.service.FootballerStatisticService;
import com.svf.fpl.edu.service.FplLoaderService;
import com.svf.fpl.edu.service.LeagueMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class FplLoaderServiceImpl implements FplLoaderService {
    @Autowired
    private FantasyPremierLeagueClient fplClient;
    @Autowired
    private LeagueMemberService leagueMemberService;
    @Autowired
    private FootballerStatisticService footballerStatisticService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized void synchronizeFplData() throws FantasyPremierLeagueException {
        boolean isMembersEmpty = leagueMemberService.checkIfLeagueMembersExist();
        if (isMembersEmpty) {
            List<LeagueMember> leagueMembers = leagueMemberService.generateLeagueMembers();
            log.info("Found {} team players", leagueMembers);
            leagueMemberService.saveLeagueMembers(leagueMembers);
        } else {
            leagueMemberService.updateLeagueMembers();
        }
        List<LeagueMember> savedLeagueMembers = leagueMemberService.getSavedLeagueMembers();
        FullInfo fullInformation = getFullInformation();
        List<Elements> elements = fullInformation.getElements();
        savedLeagueMembers.forEach(leagueMember -> {
            List<Picks> currentMemberPlayers = getCurrentMemberPlayers(leagueMember.getEntry(), leagueMember.getCurrentEvent());
            footballerStatisticService.removeMemberFootballers(leagueMember, currentMemberPlayers);
            footballerStatisticService.addMemberFootballers(leagueMember, elements, currentMemberPlayers);
            footballerStatisticService.updateMemberFootballersData(leagueMember, elements, currentMemberPlayers);
        });
    }

    @Override
    @Cacheable(value = "fullInformation")
    public FullInfo getFullInformation() throws FantasyPremierLeagueException {
        log.info("Get full information");
        FullInfo fullInformation = fplClient.getFullInformation();
        return fullInformation;
    }

    @Override
    @Cacheable(value = "players")
    public List<Picks> getCurrentMemberPlayers(long entity, int currentWeek) throws FantasyPremierLeagueException {
        log.info("Get current footbalers");
        MemberPlayer players = fplClient.getMemberPlayerIds(entity, currentWeek);
        return players.getPicks();
    }
}
