package it.dstech.controller;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.TimeZone;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.dstech.models.Attivita;
import it.dstech.models.MyRunnable;
import it.dstech.models.User;
import it.dstech.service.AttivitaService;
import it.dstech.service.MailService;
import it.dstech.service.UserService;



@EnableScheduling
@Controller
public class WebController {

	@Autowired
	private UserService userService;

	@Autowired
	private AttivitaService activityService;

	@Autowired
	private TaskScheduler scheduler;
	
	@Autowired
	private MailService mailService;

	@RequestMapping(value = { "/login", "/" }, method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value = {"/user/home"}, method=RequestMethod.GET)
    public String userIndex(Model model, Attivita attivita) throws MessagingException {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findByEmail(auth.getName());   	    
	    List<Attivita> activities = user.getActivities();	    
	    model.addAttribute("authUser", user.getEmail());
	    model.addAttribute("authUserImage", Base64.getEncoder().encodeToString(user.getImageProfile()));
        model.addAttribute("activities", activities);
        model.addAttribute("activity", new Attivita());
        model.addAttribute("title", "Activities");    		    
        return "user/index";
    }
    
    @PostMapping(value="/save")
    public String save (@ModelAttribute Attivita attivita, RedirectAttributes redirectAttributes, Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findByEmail(auth.getName());   	
	    attivita.setUser(user);
	    Attivita currActivity = activityService.save(attivita);
        userService.addActivities(user, currActivity);
        if(currActivity != null) {
            LocalDateTime date = currActivity.getExpiredDate();
			int minute = date.getMinute();
			int hours = date.getHour();
			int day = date.getDayOfMonth();
			int month = date.getMonth().getValue();
			String expression = "";
			if ((minute - 5) < 0) {
				expression += " 0 " + (minute + 5) + " " + (hours - 1) + " " + day + " " + month + " ?";
				if((hours - 1) < 0) {
					expression += " 0 " + (minute + 5) + " " + (hours + 23) + " " + day + " " + month + " ?";
				}
			} else {
				expression += " 0 " + (minute - 5) + " " + hours + " " + day + " " + month + " ?";
			}
			CronTrigger trigger = new CronTrigger(expression, TimeZone.getTimeZone(TimeZone.getDefault().getID()));
			MyRunnable myRunnable = new MyRunnable(currActivity, mailService);
            scheduler.schedule(myRunnable, trigger);
            redirectAttributes.addFlashAttribute("successmessage", "Activity is saved successfully");
            return "redirect:/user/home";
        }else {
            model.addAttribute("errormessage", "Activity is not save, Please try again");
            model.addAttribute("activity", attivita);
            return "user/index";
        }
    }
}
