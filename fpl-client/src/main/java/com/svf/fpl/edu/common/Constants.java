package com.svf.fpl.edu.common;

public class Constants {
    public static final String FPL_LOGIN_URL = "https://users.premierleague.com/accounts/login/";

    public static final String FPL_FULL_INFO_URL = "https://fantasy.premierleague.com/api/bootstrap-static/";

    public static final String FPL_TEAM_INFO_URL = "https://fantasy.premierleague.com/api/entry/4854039/";//додаткова інфа про учасника ліги
    public static final String FPL_LEAGUE_INFO_URL = "https://fantasy.premierleague.com/api/leagues-classic/586649/standings/";//учасники ліги
    //    String FPL_MEMBER_CONTENT = "https://fantasy.premierleague.com/api/my-team/%d/"; //
    public static final String FPL_MEMBER_CONTENT = "https://fantasy.premierleague.com/api/entry/%d/"; //iнформація учасника
    //    String FPL_MEMBER_PICKS = "https://fantasy.premierleague.com/api/entry/1113809/event/11/picks/"; //гравці учасника ліги
    public static final String FPL_MEMBER_PICKS = "https://fantasy.premierleague.com/api/entry/%d/event/%d/picks/"; //гравці учасника ліги
    public static final String FPL_PLAYER_HISTORY = "https://fantasy.premierleague.com/api/element-summary/%d/"; //історія гравця


    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";
}
