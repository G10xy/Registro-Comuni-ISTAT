package it.municipalitiesregistry.persistence.repository;

import it.municipalitiesregistry.persistence.entity.RegistryPlaceEntity;
import it.municipalitiesregistry.persistence.entity.RegistryPlaceId;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegistryPlaceRepository extends JpaRepository<RegistryPlaceEntity, RegistryPlaceId> {

    @Query("SELECT DISTINCT place.id.denominazioneRegione FROM RegistryPlaceEntity place where place.currentValid = 1")
    List<String> findDistinctDenominazioneRegione();

    @Query("SELECT DISTINCT place.id.denominazioneUnitaTerritorialeSovracomunale FROM RegistryPlaceEntity place where lower(place.id.denominazioneRegione) = lower(:regione) " +
            "and place.currentValid = 1")
    List<String> findDistinctDenominazioneUnitaTerritorialeSovracomunale(@Param("regione") String denominazioneRegione);

    @Query("SELECT DISTINCT place.id.denominazioneInItaliano as name, place.uuidCode as code FROM RegistryPlaceEntity place where lower(place.id.denominazioneRegione) = lower(:regione) " +
            "and lower(place.id.denominazioneUnitaTerritorialeSovracomunale) = lower(:provincia) and place.currentValid = 1 order by name")
    List<Tuple> findDistinctDenominazioneInItaliano(@Param("provincia") String denominazioneUnitaTerritorialeSovracomunale, @Param("regione") String denominazioneRegione);

    @Transactional
    @Modifying
    @Query("update RegistryPlaceEntity place set place.currentValid = 0, place.validTo = :dateTime where place.currentValid = 1 and place.lastUpdate < :dateTime")
    void updatePastOnes(@Param("dateTime") LocalDateTime dateTime);

    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    Optional<RegistryPlaceEntity> findByIdCodiceCatastaleDelComuneAndIdDenominazioneInItalianoAndIdDenominazioneUnitaTerritorialeSovracomunaleAndIdDenominazioneRegione(
            String codiceCatastaleDelComune,
            String denominazioneInItaliano,
            String denominazioneUnitaTerritorialeSovracomunale,
            String denominazioneRegione);

    Optional<RegistryPlaceEntity> findByUuidCode(UUID code);
}
