package svf.fpl.edu.bot.controller;

import com.svf.fpl.edu.entity.AdminUser;
import com.svf.fpl.edu.entity.BotUser;
import com.svf.fpl.edu.service.BotUserService;
import com.svf.fpl.edu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import svf.fpl.edu.bot.dto.BotUserDto;
import svf.fpl.edu.bot.menu.MenuBot;
import svf.fpl.edu.bot.service.FplUpdateService;

import java.util.List;

@Slf4j
@Controller
public class ManagerController {
    @Autowired
    private UserService userService;
    @Autowired
    private BotUserService botUserService;
    @Autowired
    private FplUpdateService fplUpdateService;
    @Autowired
    private MenuBot menuBot;

    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public ModelAndView manager() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<BotUser> botUsers = botUserService.selectBotUsers();
        AdminUser user = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("botUsers", botUsers);
        modelAndView.addObject("userName", "Welcome " + user.getUserName() + "/" + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("manager");
        return modelAndView;
    }

    @RequestMapping(value = "/botusers/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BotUser editUser(@PathVariable("id") long id) {
        return botUserService.selectBotUserById(id);
    }

    @RequestMapping(value = "/new-botusers", method = RequestMethod.POST)
    public String addUser(@ModelAttribute BotUserDto user) {
        botUserService.saveUserBot(user.getChatId(), user.getUserName());
        return "redirect:/manager";
    }

    @RequestMapping(value = "/botusers", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute BotUser editedUser) {
        botUserService.updateUserBotPrivileges(editedUser, editedUser.isAllowedToReceiveMessage());
        return "redirect:/manager";
    }

    @RequestMapping(value = "/deleting-botusers", method = RequestMethod.POST)
    public String deleteUser(@ModelAttribute BotUser user) {
        botUserService.removeUserBot(user);
        return "redirect:/manager";
    }

    @RequestMapping(value = "/data-synchronization", method = RequestMethod.POST)
    public String synchronization() {
        fplUpdateService.updateTeamMember();
        return "redirect:/manager";
    }

    @RequestMapping(value = "/resultTable", method = RequestMethod.POST)
    public String sendResultTable(@ModelAttribute("chatId") Long chatId, BindingResult bindingResult) {
        menuBot.sendStandings(chatId);
        return "redirect:/manager";
    }
}
