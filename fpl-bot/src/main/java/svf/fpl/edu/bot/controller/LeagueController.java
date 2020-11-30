package svf.fpl.edu.bot.controller;

import com.svf.fpl.edu.entity.LeagueMember;
import com.svf.fpl.edu.service.LeagueMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import svf.fpl.edu.bot.dto.BestFootballersDto;
import svf.fpl.edu.bot.service.LeagueService;

import java.util.List;

@Controller
@RequestMapping("/league")
@Slf4j
public class LeagueController {
    @Autowired
    private LeagueMemberService leagueMemberService;
    @Autowired
    private LeagueService leagueService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public ModelAndView getLeagueMembers() {
        ModelAndView view = new ModelAndView();
        List<LeagueMember> leagueMembers = leagueMemberService.getSavedLeagueMembers();
        view.addObject("members", leagueMembers);
        view.setViewName("league");
        return view;
    }

    @RequestMapping(value = "/team", method = RequestMethod.GET)
    public ModelAndView getTeam(@RequestParam("limit") int limit) {
        if (limit == 0) {
            limit = 10;
        }
        ModelAndView view = new ModelAndView();
        BestFootballersDto bestFootballersDto = leagueService.generateTopPlayers(limit);
        view.addObject("goalkeepers", bestFootballersDto.getGoalkeepers());
        view.addObject("defenders", bestFootballersDto.getDefenders());
        view.addObject("strikers", bestFootballersDto.getMiddleFields());
        view.addObject("forwards", bestFootballersDto.getForwards());
        view.setViewName("team");
        return view;
    }
}
