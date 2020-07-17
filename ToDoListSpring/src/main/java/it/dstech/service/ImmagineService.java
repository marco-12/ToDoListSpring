package it.dstech.service;

import org.springframework.web.multipart.MultipartFile;
import it.dstech.models.Immagine;

public interface ImmagineService {

	public Immagine salvaFile(MultipartFile file); // upload

	public Immagine recuperaFile(Long fileId); // download

}
