package com.svf.fpl.edu.client;

import com.svf.fpl.edu.dto.*;
import com.svf.fpl.edu.exeptions.FantasyPremierLeagueException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static com.svf.fpl.edu.common.Constants.*;


@Slf4j
@Component("fplClient")
public class FantasyPremierLeagueClient {

    @Value("${fpl.finalCookie}")
    private String finalCookie;
    @Value("${fpl.username}")
    private String fplUsername;
    @Value("${fpl.password}")
    private String fplPassword;

    @Autowired
    private RestTemplate restTemplate;

    public String login() {
        log.info("Login into FPL");
        String tempCookies = restTemplate.execute(FPL_LOGIN_URL, HttpMethod.POST,
                request -> {
                    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                    map.add("login", fplUsername);
                    map.add("password", new String(Base64.decodeBase64(fplPassword)));
                    map.add("app", "plfpl-web");
                    map.add("redirect_uri", "https://fantasy.premierleague.com/");
                    new FormHttpMessageConverter().write(map, MediaType.APPLICATION_FORM_URLENCODED, request);
                }, response -> response.getHeaders().getFirst(HttpHeaders.SET_COOKIE));
        finalCookie = tempCookies;
        return tempCookies;
    }

    public FullInfo getFullInformation() {
        log.info("Get fantasy full information");
        try {
            return restTemplate.getForObject(FPL_FULL_INFO_URL, FullInfo.class);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new FantasyPremierLeagueException("Error in get full information", ex);
        }
    }

    public LeagueResponse getLeagueInformation() {
        log.info("Get fantasy league information");
        try {
            HttpHeaders requestHeaders = getHttpHeaders();
            HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
            HttpEntity<LeagueResponse> rssResponse = restTemplate.exchange(
                    FPL_LEAGUE_INFO_URL,
                    HttpMethod.GET,
                    requestEntity,
                    LeagueResponse.class);
            LeagueResponse body = rssResponse.getBody();
            return body;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new FantasyPremierLeagueException("Eror in get league information", ex);
        }
    }

    public MemberContent getMemberDetails(Long memberId) {
        log.info("Get league member details: " + memberId);
        if (memberId == null) {
            throw new FantasyPremierLeagueException("member id is null");
        }
        try {
            HttpHeaders requestHeaders = getHttpHeaders();
            String url = String.format(FPL_MEMBER_CONTENT, memberId);
            log.info("Connect to link: " + url);
            HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
            HttpEntity<MemberContent> rssResponse = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    MemberContent.class);
            return rssResponse.getBody();
//            return content;
        } catch (Exception ex) {
            log.warn(ex.getMessage());
            throw new FantasyPremierLeagueException("Error in get member Details", ex);
        }
    }

    public MemberPlayer getMemberPlayerIds(Long memberId, Integer currentWeek) {
        log.info("Get league members players: " + memberId + " current week " + currentWeek);
        if (memberId == null) {
            throw new FantasyPremierLeagueException("member id is null");
        }
        if (currentWeek == null) {
            throw new FantasyPremierLeagueException("current week id is null");
        }
        try {
            HttpHeaders requestHeaders = getHttpHeaders();
            String url = String.format(FPL_MEMBER_PICKS, memberId, currentWeek);
            log.info("Connect to link: " + url);
            HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
            HttpEntity<MemberPlayer> rssResponse = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    MemberPlayer.class);
            return rssResponse.getBody();
        } catch (Exception ex) {
            log.warn(ex.getMessage());
            throw new FantasyPremierLeagueException("Error in get member players", ex);
        }
    }

    public Player getPlayerHistoty(Long playerId) {
        log.info("Get player History: " + playerId);
        try {
            if (playerId == null) {
                throw new FantasyPremierLeagueException("player id is null");
            }
            HttpHeaders requestHeaders = getHttpHeaders();
            String url = String.format(FPL_PLAYER_HISTORY, playerId);
            log.info("Connect to link: " + url);
            HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
            HttpEntity<Player> rssResponse = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    Player.class);
            return rssResponse.getBody();
        } catch (Exception ex) {
            log.warn(ex.getMessage());
            throw new FantasyPremierLeagueException("Error on get player History", ex);
        }
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("Cookie", finalCookie);
        requestHeaders.add("Sec-Fetch-User", "?1");
        requestHeaders.add("cache-control", "no-cache");
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        requestHeaders.add("user-agent", "Mozilla/5FootballerStatisticServiceImpl.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        return requestHeaders;
    }
}
