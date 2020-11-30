package com.svf.fpl.edu.service;

import com.svf.fpl.edu.dto.FullInfo;
import com.svf.fpl.edu.dto.Picks;
import com.svf.fpl.edu.exeptions.FantasyPremierLeagueException;

import java.util.List;

public interface FplLoaderService {
    void synchronizeFplData() throws FantasyPremierLeagueException;

    FullInfo getFullInformation() throws FantasyPremierLeagueException;

    List<Picks> getCurrentMemberPlayers(long entity, int currentWeek) throws FantasyPremierLeagueException;
}