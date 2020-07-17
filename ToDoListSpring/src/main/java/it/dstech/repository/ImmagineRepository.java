package it.dstech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dstech.models.Immagine;

@Repository
public interface ImmagineRepository extends JpaRepository<Immagine, Long> {

}
