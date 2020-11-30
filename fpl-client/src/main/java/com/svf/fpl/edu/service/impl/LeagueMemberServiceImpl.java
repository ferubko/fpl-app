package com.svf.fpl.edu.service.impl;

import com.svf.fpl.edu.client.FantasyPremierLeagueClient;
import com.svf.fpl.edu.dto.LeagueResponse;
import com.svf.fpl.edu.dto.MemberContent;
import com.svf.fpl.edu.entity.LeagueMember;
import com.svf.fpl.edu.exeptions.LeagueMemberException;
import com.svf.fpl.edu.repositories.LeagueMemberRepository;
import com.svf.fpl.edu.service.LeagueMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LeagueMemberServiceImpl implements LeagueMemberService {
    @Autowired
    private FantasyPremierLeagueClient fplClient;
    @Autowired
    private LeagueMemberRepository leagueMemberRepository;

    @Override
    public boolean checkIfLeagueMembersExist() throws LeagueMemberException {
        long leagueSize = leagueMemberRepository.count();
        log.info("League entry is {}", leagueSize);
        return leagueSize == 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<LeagueMember> generateLeagueMembers() throws LeagueMemberException {
        LeagueResponse leagueInformation = fplClient.getLeagueInformation();
        return leagueInformation.getStandings().getResults().stream()
                .map(r -> {
                    LeagueMember leagueMember = new LeagueMember();
                    leagueMember.setPlayerName(r.getPlayer_name());
                    leagueMember.setEntry(r.getEntry());
                    leagueMember.setTotal(r.getTotal());
                    return leagueMember;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateLeagueMembers() throws LeagueMemberException {
        log.info("Starts updating members");
        LeagueResponse leagueInformation = fplClient.getLeagueInformation();
        List<LeagueMember> members = leagueInformation.getStandings().getResults().stream()
                .map(r -> {
                    LeagueMember leagueMember = new LeagueMember();
                    MemberContent memberDetails = fplClient.getMemberDetails(r.getEntry());
                    leagueMember.setEntry(r.getEntry());
                    leagueMember.setCurrentEvent(memberDetails.getCurrent_event());
                    leagueMember.setTotal(r.getTotal());
                    leagueMember.setPoints(memberDetails.getSummary_event_points());
                    return leagueMember;
                }).peek(m -> {
                    Optional<LeagueMember> savedMemberOptional = leagueMemberRepository.findByEntry(m.getEntry());
                    savedMemberOptional.ifPresentOrElse(leagueMember -> {
                        leagueMember.setPoints(m.getPoints());
                        leagueMember.setTotal(m.getTotal());
                        leagueMember.setCurrentEvent(m.getCurrentEvent());
                        LeagueMember save = leagueMemberRepository.save(leagueMember);
                        m.setId(save.getId());
                    }, () -> log.info("No such member by entry {}", m.getEntry()));
                })
                .collect(Collectors.toList());
        log.info("Members updating finished");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<LeagueMember> saveLeagueMembers(List<LeagueMember> leagueMembers) throws LeagueMemberException {
        List<LeagueMember> foundedLeagueMembers =
                leagueMembers.stream()
                        .map(leagueMember -> {
                            MemberContent memberDetails = fplClient.getMemberDetails(leagueMember.getEntry());
                            leagueMember.setCurrentEvent(memberDetails.getCurrent_event());
                            leagueMember.setPoints(memberDetails.getSummary_event_points());
                            return leagueMember;
                        }).collect(Collectors.toList());
        leagueMemberRepository.saveAll(foundedLeagueMembers);
        log.info("Teams {} were added", foundedLeagueMembers.size());
        return foundedLeagueMembers;
    }

    @Override
    public List<LeagueMember> getSavedLeagueMembers() throws LeagueMemberException {
        Iterable<LeagueMember> allMembers = leagueMemberRepository.findAll();
        List<LeagueMember> result = new ArrayList<>();
        allMembers.forEach(result::add);
        log.info("Member size: {}", result.size());
        return result.stream().sorted((leagueMember, t1) -> t1.getTotal() - leagueMember.getTotal()).collect(Collectors.toList());
    }
}
