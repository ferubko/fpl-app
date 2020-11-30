package com.svf.fpl.edu.service;

import com.svf.fpl.edu.dto.Elements;
import com.svf.fpl.edu.dto.Picks;
import com.svf.fpl.edu.dto.report.ElementType;
import com.svf.fpl.edu.dto.report.FootballerInjuryReportDto;
import com.svf.fpl.edu.dto.report.TopFootballerDto;
import com.svf.fpl.edu.entity.Footballer;
import com.svf.fpl.edu.entity.LeagueMember;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FootballerStatisticService {

    List<FootballerInjuryReportDto> generateFootballerInjuryReport();

    Set<Footballer> addMemberFootballers(LeagueMember leagueMember, List<Elements> elements, List<Picks> currentMemberPlayers);

    boolean removeMemberFootballers(LeagueMember leagueMember, List<Picks> currentMemberPlayers);

    Set<Footballer> updateMemberFootballersData(LeagueMember leagueMember, List<Elements> elements, List<Picks> currentMemberPlayers);

    List<TopFootballerDto> getTop10BestFootballers(ElementType elementType, int limit);

    Map<ElementType, List<TopFootballerDto>> getBestFootballers(int limit);
}
