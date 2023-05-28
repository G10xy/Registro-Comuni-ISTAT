package it.municipalitiesregistry.persistence.repository;

import it.municipalitiesregistry.persistence.entity.RegistryPlaceEntity;
import it.municipalitiesregistry.persistence.entity.RegistryPlaceId;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegistryPlaceRepository extends JpaRepository<RegistryPlaceEntity, RegistryPlaceId> {

    @Query("SELECT DISTINCT place.id.denominazioneRegione FROM RegistryPlaceEntity place where place.currentValid = true")
    List<String> findDistinctDenominazioneRegione();

    @Query("SELECT DISTINCT place.id.denominazioneUnitaTerritorialeSovracomunale FROM RegistryPlaceEntity place where lower(place.id.denominazioneRegione) = lower(:regione) and place.currentValid = true")
    List<String> findDistinctDenominazioneUnitaTerritorialeSovracomunale(@Param("regione") String denominazioneRegione);

    @Query("SELECT place FROM RegistryPlaceEntity place where lower(place.id.denominazioneRegione) = lower(:regione) and lower(place.id.denominazioneUnitaTerritorialeSovracomunale) = lower(:provincia) and place.currentValid = true order by place.id.denominazioneInItaliano")
    List<RegistryPlaceEntity> findDistinctDenominazioneInItaliano(@Param("provincia") String denominazioneUnitaTerritorialeSovracomunale, @Param("regione") String denominazioneRegione);

    @Query("SELECT place FROM RegistryPlaceEntity place where lower(place.id.denominazioneUnitaTerritorialeSovracomunale) = lower(:provincia) and place.currentValid = true order by place.id.denominazioneInItaliano")
    List<RegistryPlaceEntity> findDistinctDenominazioneInItalianoByProvince(@Param("provincia") String denominazioneUnitaTerritorialeSovracomunale);

    @Query("SELECT place FROM RegistryPlaceEntity place where lower(place.id.denominazioneRegione) = lower(:regione) and place.currentValid = true order by place.id.denominazioneInItaliano")
    List<RegistryPlaceEntity> findDistinctDenominazioneInItalianoByRegion(@Param("regione") String denominazioneRegione);

    Optional<RegistryPlaceEntity> findByIdCodiceCatastaleDelComuneAndCurrentValidTrue(String codiceCatastaleDelComune);

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
