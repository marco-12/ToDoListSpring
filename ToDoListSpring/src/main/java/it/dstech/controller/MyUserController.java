package it.dstech.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.dstech.models.Immagine;
import it.dstech.models.User;
import it.dstech.service.ImmagineService;
import it.dstech.service.MailService;
import it.dstech.service.UserService;

@Controller
public class MyUserController {

	@Autowired
	private UserService userService;

	@Autowired
	private MailService mailService;

	@Autowired
	private ImmagineService imageService;

	
	
	@PostMapping("/user")
	public boolean addUser(@RequestBody User user) throws MessagingException {
		if (userService.findByUsernameOrEmail(user)) {
			return false;
		}
		userService.add(user);
		mailService.inviaMail(user.getEmail(), "conferma registrazione", "Registrazione effettuata correttamente");
		return true;
	}

	@GetMapping("/users")
	public List<User> allUsers() {
		return userService.findAll();
	}

	@GetMapping("/users/count")
	public Long count() {
		return userService.count();
	}

	@DeleteMapping("/users/{id}")
	public void delete(@PathVariable String id) {
		Long userId = Long.parseLong(id);
		userService.deleteById(userId);
	}

	@PostMapping("/image")
	public void fileUpload(@RequestParam("file") MultipartFile file) { // upload del fils

		imageService.salvaFile(file);

	}

	@GetMapping("/downloadFile/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) { // servizio di download
		// Load file from database
		Immagine dbFile = imageService.recuperaFile(fileId);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(dbFile.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
				.body(new ByteArrayResource(dbFile.getData()));
	}

}
