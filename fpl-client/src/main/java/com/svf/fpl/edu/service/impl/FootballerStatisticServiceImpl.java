package com.svf.fpl.edu.service.impl;

import com.svf.fpl.edu.dto.Elements;
import com.svf.fpl.edu.dto.FullInfo;
import com.svf.fpl.edu.dto.Picks;
import com.svf.fpl.edu.dto.Teams;
import com.svf.fpl.edu.dto.report.ElementType;
import com.svf.fpl.edu.dto.report.FootballerInjuryDto;
import com.svf.fpl.edu.dto.report.FootballerInjuryReportDto;
import com.svf.fpl.edu.dto.report.TopFootballerDto;
import com.svf.fpl.edu.entity.Footballer;
import com.svf.fpl.edu.entity.LeagueMember;
import com.svf.fpl.edu.repositories.FootballerRepository;
import com.svf.fpl.edu.repositories.LeagueMemberRepository;
import com.svf.fpl.edu.service.FootballerStatisticService;
import com.svf.fpl.edu.service.FplLoaderService;
import com.svf.fpl.edu.util.StringToIntConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class FootballerStatisticServiceImpl implements FootballerStatisticService {
    @Autowired
    private LeagueMemberRepository leagueMemberRepository;
    @Autowired
    private FootballerRepository footballerRepository;
    @Autowired
    private FplLoaderService fplLoaderService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Set<Footballer> addMemberFootballers(LeagueMember leagueMember, List<Elements> elements, List<Picks> currentMemberPlayers) {
        log.info("Started adding new footballers");
        Set<Footballer> footballers = leagueMember.getFootballers();
        Set<Footballer> savedFootballers = currentMemberPlayers.stream()
                .filter(p -> footballers.stream().noneMatch(memberPlayer -> memberPlayer.getElementId() == p.getElement()))
                .map(pick -> saveMemberPlayer(leagueMember, elements, pick))
                .collect(Collectors.toSet());
        log.info("Was added {} footballers", savedFootballers.size());
        return savedFootballers;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean removeMemberFootballers(LeagueMember leagueMember, List<Picks> currentMemberPlayers) {
        log.info("Started removing old footballers");
        Set<Footballer> footballers = leagueMember.getFootballers();
        footballers.stream()
                .filter(f -> currentMemberPlayers.stream().noneMatch(cur -> cur.getElement() == f.getElementId()))
                .forEach(t -> footballerRepository.deleteById(t.getId()));
        log.info("Removing was successfully done");
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Set<Footballer> updateMemberFootballersData(LeagueMember leagueMember, List<Elements> elements, List<Picks> currentMemberPlayers) {
        log.info("Started updating footballers");
        Set<Footballer> footballers = leagueMember.getFootballers();
        List<Picks> selectedPicks = currentMemberPlayers.stream()
                .filter(p -> footballers.stream().anyMatch(f -> f.getElementId() == p.getElement())).collect(Collectors.toList());
        selectedPicks.forEach(p -> footballerRepository.updateFootballerPicks(p.is_captain(), p.getElement()));
        List<Elements> selectElements = elements.stream()
                .filter(el -> footballers.stream().anyMatch(f -> f.getElementId() == el.getId())).collect(Collectors.toList());
        selectElements.forEach(el ->
                footballerRepository.updateFootballerData(el.getChance_of_playing_next_round(), el.getNews(), el.getTotal_points(), el.getPoints_per_game(), el.getId()));
        log.info("Footballers {} were updated", footballers.size());
        return footballers;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Map<ElementType, List<TopFootballerDto>> getBestFootballers(int limit) {
        FullInfo fullInformation = fplLoaderService.getFullInformation();
        List<Elements> elements = fullInformation.getElements();
        List<Teams> teams = fullInformation.getTeams();
        return elements.stream()
                .filter(f -> StringUtils.isBlank(f.getChance_of_playing_next_round()) || f.getChance_of_playing_next_round().equals("100"))
                .collect(Collectors.groupingBy(el -> ElementType.fromId(el.getElement_type()), Collectors.collectingAndThen(Collectors.toList(), f1 -> f1.stream()
                        .sorted(Comparator.comparingInt(Elements::getNow_cost))
                        .sorted(Comparator.comparingDouble(Elements::getPoints_per_game).reversed())
                        .limit(limit)
                        .map(f -> {
                            TopFootballerDto topFootballerDto = new TopFootballerDto();
                            topFootballerDto.setFootballerName(f.getFirst_name() + " " + f.getSecond_name());
                            Optional<Teams> team = teams.stream().filter(t -> t.getCode() == f.getTeam_code()).findAny();
                            topFootballerDto.setTeam(team.map(Teams::getName).get());
                            topFootballerDto.setCost(f.getNow_cost());
                            topFootballerDto.setCreativity(f.getCreativity());
                            ElementType type = ElementType.fromId(f.getElement_type());
                            topFootballerDto.setFootballerType(type.getName());
                            topFootballerDto.setInfluence(f.getInfluence());
                            topFootballerDto.setPointsPerGame(f.getPoints_per_game());
                            topFootballerDto.setTotalPoints(f.getTotal_points());
                            return topFootballerDto;
                        })
                        .collect(Collectors.toList()))));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TopFootballerDto> getTop10BestFootballers(ElementType elementType, int limit) {
        FullInfo fullInformation = fplLoaderService.getFullInformation();
        List<Elements> elements = fullInformation.getElements();
        List<Teams> teams = fullInformation.getTeams();


        List<TopFootballerDto> result = elements
                .stream()
                .filter(f -> f.getElement_type() == elementType.getId())
                .filter(f -> StringUtils.isBlank(f.getChance_of_playing_next_round()) || f.getChance_of_playing_next_round().equals("100"))
                .sorted(Comparator.comparingInt(Elements::getNow_cost))
                .sorted(Comparator.comparingDouble(Elements::getPoints_per_game).reversed())
                .limit(limit)
                .map(f -> {
                    TopFootballerDto topFootballerDto = new TopFootballerDto();
                    topFootballerDto.setFootballerName(f.getFirst_name() + " " + f.getSecond_name());
                    Optional<Teams> team = teams.stream().filter(t -> t.getCode() == f.getTeam_code()).findAny();
                    topFootballerDto.setTeam(team.map(Teams::getName).get());
                    topFootballerDto.setCost(f.getNow_cost());
                    topFootballerDto.setCreativity(f.getCreativity());
                    ElementType type = ElementType.fromId(f.getElement_type());
                    topFootballerDto.setFootballerType(type.getName());
                    topFootballerDto.setInfluence(f.getInfluence());
                    topFootballerDto.setPointsPerGame(f.getPoints_per_game());
                    topFootballerDto.setTotalPoints(f.getTotal_points());
                    return topFootballerDto;
                })
                .collect(Collectors.toList());
//                .forEach(r -> {
//                    System.out.println("Name: " + r.getFirst_name() + " " + r.getSecond_name());
//                    System.out.println("Cost: " + r.getNow_cost());
//                    System.out.println("Points: " + r.getPoints_per_game());
//                });


//        Map<Long, List<TopFootballerDto>> collect = elements
//                .stream()
////                .filter(el -> el.getNow_cost() <= 90)
//                .filter(f -> StringUtils.isNotBlank(f.getChance_of_playing_next_round()) && (f.getChance_of_playing_next_round().equals("0")
//                        || StringToIntConverter.stringToInt(f.getChance_of_playing_next_round()) < 100))
//                .collect(Collectors.groupingBy(Elements::getElement_type, Collectors.collectingAndThen(Collectors.toList(), l -> l.stream()
//                                .sorted(Comparator.comparingInt(Elements::getNow_cost)
//                                                .thenComparingDouble(Elements::getCreativity)
//                                                .reversed()
////                                .thenComparingDouble(Elements::getInfluence)
////                                .reversed()
//                                )
//                                .limit(10)
//                                .map(f -> {
//                                    TopFootballerDto topFootballerDto = new TopFootballerDto();
//                                    topFootballerDto.setFootballerName(f.getFirst_name() + " " + f.getSecond_name());
//                                    topFootballerDto.setCost(f.getNow_cost());
//                                    topFootballerDto.setCreativity(f.getCreativity());
//                                    ElementType type = ElementType.fromId(f.getElement_type());
//                                    topFootballerDto.setFootballerType(type.getName());
//                                    topFootballerDto.setInfluence(f.getInfluence());
//                                    topFootballerDto.setPointsPerGame(f.getPoints_per_game());
//                                    return topFootballerDto;
//                                })
//                                .collect(Collectors.toList())
//                )));
//        List<TopFootballerDto> topFootballerDtos = collect.get(elementType.getId());
//        if (topFootballerDtos == null) {
//            return Collections.emptyList();
//        }
        return result;
    }

    protected Footballer saveMemberPlayer(LeagueMember leagueMember, List<Elements> elements, Picks pick) {
        Footballer memberPlayer = new Footballer();
        memberPlayer.setLeagueMember(leagueMember);
        memberPlayer.setElementId(pick.getElement());
        memberPlayer.setCaptain(pick.is_captain());
        Optional<Elements> optionalElement = elements.stream().filter(el -> el.getId() == pick.getElement()).findFirst();
        if (optionalElement.isPresent()) {
            Footballer player = populatePlayerInfo(memberPlayer, optionalElement.get());
            return footballerRepository.save(player);
        } else {
            log.error("No such player exist with {}", pick.getElement());
            return null;
//            throw new LeagueMemberException("No such player exist");
        }
    }

    private Footballer populatePlayerInfo(Footballer memberPlayer, Elements elements) {
//        return optionalElement.map(o -> {
        memberPlayer.setName(elements.getFirst_name() + " " + elements.getSecond_name());
        memberPlayer.setNews(elements.getNews());
        memberPlayer.setChancePlayingNextGame(elements.getChance_of_playing_next_round());
        memberPlayer.setPointsPerGame(elements.getPoints_per_game());
        memberPlayer.setTotalPoints(elements.getTotal_points());
        return memberPlayer;
//        }).get();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized List<FootballerInjuryReportDto> generateFootballerInjuryReport() {
        try {
            log.info("Start generate injury report");
            Iterable<LeagueMember> members = leagueMemberRepository.findAll();
            List<FootballerInjuryReportDto> result = StreamSupport.stream(members.spliterator(), false)
                    .map(member -> {
                        FootballerInjuryReportDto injuryReportDto = new FootballerInjuryReportDto();
                        injuryReportDto.setMemberName(member.getPlayerName());
                        List<FootballerInjuryDto> news = member.getFootballers().stream()
                                .filter(f -> StringUtils.isNotBlank(f.getChancePlayingNextGame()) && (f.getChancePlayingNextGame().equals("0")
                                        || StringToIntConverter.stringToInt(f.getChancePlayingNextGame()) < 100))
                                .map(p -> new FootballerInjuryDto(p.getName(), p.getNews())).collect(Collectors.toList());
                        injuryReportDto.setFootballerInjurys(news);
                        log.info("Team {} has {} injured footballers", member.getPlayerName(), news.size());
                        return injuryReportDto;
                    }).filter(injury -> !injury.getFootballerInjurys().isEmpty())
                    .collect(Collectors.toList());
            log.info("Team {} has injured players", result.size());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
