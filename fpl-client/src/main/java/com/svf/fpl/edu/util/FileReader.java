package com.svf.fpl.edu.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.svf.fpl.edu.dto.FullInfo;
import com.svf.fpl.edu.dto.LeagueResponse;
import com.svf.fpl.edu.dto.MemberContent;
import com.svf.fpl.edu.dto.MemberPlayer;

import java.io.File;
import java.io.IOException;

public class FileReader {
    public static FullInfo getFullInfo(String url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(url), FullInfo.class);
    }

    public static LeagueResponse readLeague(String url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(url), LeagueResponse.class);
    }

    public static MemberContent readMemberDetails(String url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(url), MemberContent.class);
    }

    public static MemberPlayer readMemberPlayerId(String url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(url), MemberPlayer.class);
    }
}
