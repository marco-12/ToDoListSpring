package it.dstech.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dstech.models.Attivita;
import it.dstech.repository.AttivitaRepository;


@Service
public class AttivitaServiceImp implements AttivitaService {
	
	@Autowired
	private AttivitaRepository activityRepository;

	@Override
	public Attivita save(Attivita attivita) {
		// TODO Auto-generated method stub
	    /* For solve Failed to convert property value of type 'java.lang.String' to required type 'java.time.LocalDateTime' for property 'expiredDate' */
	    String date = attivita.getDate();
	    String expiredDate = date.replace("T", " ");
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
	    LocalDateTime dateTime = LocalDateTime.parse(expiredDate, formatter);	
	    attivita.setExpiredDate(dateTime);	    
		
		return  activityRepository.save(attivita);
	}

	@Override
	public Attivita findByID(Long id) {
		// TODO Auto-generated method stub
        Optional<Attivita> activity = activityRepository.findById(id);
        if(activity.isPresent()) {
            return activity.get();
        } else {
            return null;
        }
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		activityRepository.deleteById(id);
	}

	@Override
	public List<Attivita> getAllActivities() {
		// TODO Auto-generated method stub
		return (List<Attivita>) activityRepository.findAll();
	}

}
