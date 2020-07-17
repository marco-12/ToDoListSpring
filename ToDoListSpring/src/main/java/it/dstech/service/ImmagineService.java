package it.dstech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dstech.models.User;
import it.dstech.repository.ImmagineRepository;

@Service
public class ImmagineService {

    @Autowired
    private ImmagineRepository immagineRepository;

    public User getFile(Long id) {
        return immagineRepository.findById(id).orElseThrow(() -> null);
    }
}
