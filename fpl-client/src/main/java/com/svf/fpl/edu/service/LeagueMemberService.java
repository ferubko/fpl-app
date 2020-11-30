package com.svf.fpl.edu.service;

import com.svf.fpl.edu.entity.LeagueMember;
import com.svf.fpl.edu.exeptions.LeagueMemberException;

import java.util.List;

public interface LeagueMemberService {
    boolean checkIfLeagueMembersExist() throws LeagueMemberException;

    List<LeagueMember> generateLeagueMembers() throws LeagueMemberException;

    void updateLeagueMembers() throws LeagueMemberException;

    List<LeagueMember> saveLeagueMembers(List<LeagueMember> members) throws LeagueMemberException;

    List<LeagueMember> getSavedLeagueMembers() throws LeagueMemberException;


}
