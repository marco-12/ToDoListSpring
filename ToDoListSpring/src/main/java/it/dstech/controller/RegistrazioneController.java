package it.dstech.controller;

import java.io.IOException;
import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import it.dstech.models.User;
import it.dstech.service.MailService;
import it.dstech.service.UserRegistrationDao;
import it.dstech.service.UserService;

@Controller
@RequestMapping("/registration")
public class RegistrazioneController {

	@Autowired
    private UserService userService;
    
    @Autowired
	private MailService mailService;

    @ModelAttribute("user")
    public UserRegistrationDao userRegistrationDto() {
        return new UserRegistrationDao();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDao userDto, BindingResult result, Model model) throws MessagingException, IOException {
        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()) {
            return "error";
        }

        userService.save(userDto);
        mailService.inviaMail(userDto.getEmail(), "Registrazione confermata", "L'utente è stato registrato con successo");
        model.addAttribute("successMessage", "L'utente è stato registrato con successo, torna alla pagina di login");
		return "login";
    }
    
}

