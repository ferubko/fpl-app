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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
public class LoginPageController {

    @Autowired
    private UserService userService;
    @Autowired
    private BotUserService botUserService;

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        AdminUser user = new AdminUser();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid AdminUser user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        AdminUser userExists = userService.findUserByUserName(user.getUserName());
        if (userExists != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "There is already a user registered with the user name provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new AdminUser());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

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
}
