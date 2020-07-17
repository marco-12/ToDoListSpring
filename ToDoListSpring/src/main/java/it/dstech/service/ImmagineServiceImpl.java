package it.dstech.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import it.dstech.models.Immagine;
import it.dstech.repository.ImmagineRepository;

@Service
public class ImmagineServiceImpl implements ImmagineService {

	@Autowired
	private ImmagineRepository immagineRepos;

	@Override
	public Immagine salvaFile(MultipartFile file) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				return null;
			}

			Immagine dbFile = new Immagine(fileName, file.getContentType(), file.getBytes());

			return immagineRepos.save(dbFile);
		} catch (IOException ex) {
			return null;
		}
	}

	@Override
	public Immagine recuperaFile(Long fileId) {
		return immagineRepos.findById(fileId).orElseThrow(() -> null); // se il risultato della ricerca è null, restituisce null, altrimenti restituisce il risultato
	}
}
