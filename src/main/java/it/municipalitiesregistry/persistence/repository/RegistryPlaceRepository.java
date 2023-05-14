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

    @Transactional
    @Modifying
    @Query("update RegistryPlaceEntity place set place.currentValid = false, place.validTo = :dateTime where place.currentValid = true and place.lastUpdate < :dateTime")
    void updatePastOnes(@Param("dateTime") LocalDateTime dateTime);

    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    @Query("select place from RegistryPlaceEntity place where place.id.codiceCatastaleDelComune = :codiceCatastaleDelComune " +
            "and place.id.denominazioneInItaliano = :denominazioneInItaliano " +
            "and place.id.denominazioneUnitaTerritorialeSovracomunale = :denominazioneUnitaTerritorialeSovracomunale " +
            "and place.id.denominazioneRegione = :denominazioneRegione")
    Optional<RegistryPlaceEntity> findByCompositeId(
            @Param("codiceCatastaleDelComune") String codiceCatastaleDelComune,
            @Param("denominazioneInItaliano") String denominazioneInItaliano,
            @Param("denominazioneUnitaTerritorialeSovracomunale") String denominazioneUnitaTerritorialeSovracomunale,
            @Param("denominazioneRegione") String denominazioneRegione);
}
