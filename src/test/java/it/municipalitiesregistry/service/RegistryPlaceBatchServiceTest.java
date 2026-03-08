package it.municipalitiesregistry.service;

import it.municipalitiesregistry.model.RegistryPlaceCsvDTO;
import it.municipalitiesregistry.persistence.entity.RegistryPlaceEntity;
import it.municipalitiesregistry.persistence.entity.RegistryPlaceId;
import it.municipalitiesregistry.persistence.repository.RegistryPlaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class RegistryPlaceBatchServiceTest {

    @Autowired
    private RegistryPlaceBatchService batchService;

    @Autowired
    private RegistryPlaceRepository repository;

    @BeforeEach
    void cleanUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Insert a new municipality and verify persisted fields")
    void shouldInsertNewMunicipality() {
        LocalDateTime now = LocalDateTime.now();
        RegistryPlaceCsvDTO dto = buildCsvDto("L219", "Torino", "Torino", "Piemonte");

        batchService.saveOrUpdateAll(List.of(dto), now);

        assertEquals(1, repository.count());
        Optional<RegistryPlaceEntity> saved = repository.findById(
                new RegistryPlaceId("Piemonte", "L219", "Torino", "Torino"));
        assertTrue(saved.isPresent());
        assertEquals(now, saved.get().getLastUpdate());
        assertEquals(now, saved.get().getValidFrom());
        assertTrue(saved.get().isCurrentValid());
    }

    @Test
    @DisplayName("Update existing municipality without changing validFrom")
    void shouldUpdateExistingMunicipality() {
        LocalDateTime firstRun = LocalDateTime.now().minusDays(1);
        LocalDateTime secondRun = LocalDateTime.now();

        RegistryPlaceCsvDTO dto = buildCsvDto("L219", "Torino", "Torino", "Piemonte");
        dto.setCodiceRegione("01");

        batchService.saveOrUpdateAll(List.of(dto), firstRun);

        dto.setCodiceRegione("99");
        batchService.saveOrUpdateAll(List.of(dto), secondRun);

        assertEquals(1, repository.count());
        RegistryPlaceEntity updated = repository.findById(
                new RegistryPlaceId("Piemonte", "L219", "Torino", "Torino")).orElseThrow();
        assertEquals("99", updated.getCodiceRegione());
        assertEquals(secondRun, updated.getLastUpdate());
        // validFrom should remain from first insert
        assertEquals(firstRun, updated.getValidFrom());
    }

    @Test
    @DisplayName("Batch insert multiple municipalities in a single call")
    void shouldInsertMultipleMunicipalities() {
        LocalDateTime now = LocalDateTime.now();
        List<RegistryPlaceCsvDTO> dtos = List.of(
                buildCsvDto("L219", "Torino", "Torino", "Piemonte"),
                buildCsvDto("F205", "Milano", "Milano", "Lombardia"),
                buildCsvDto("H501", "Roma", "Roma", "Lazio")
        );

        batchService.saveOrUpdateAll(dtos, now);

        assertEquals(3, repository.count());
    }

    @Test
    @DisplayName("Mark outdated municipalities as invalid with validTo set")
    void shouldMarkPastMunicipalitiesAsInvalid() {
        LocalDateTime firstRun = LocalDateTime.now().minusDays(1);
        LocalDateTime updateThreshold = LocalDateTime.now().minusMinutes(1);

        RegistryPlaceCsvDTO dto = buildCsvDto("L219", "Torino", "Torino", "Piemonte");
        batchService.saveOrUpdateAll(List.of(dto), firstRun);

        RegistryPlaceEntity before = repository.findById(
                new RegistryPlaceId("Piemonte", "L219", "Torino", "Torino")).orElseThrow();
        assertTrue(before.isCurrentValid());

        // Mark past ones as invalid
        batchService.updatePastOnes(updateThreshold);

        RegistryPlaceEntity after = repository.findById(
                new RegistryPlaceId("Piemonte", "L219", "Torino", "Torino")).orElseThrow();
        assertFalse(after.isCurrentValid());
        assertNotNull(after.getValidTo());
    }

    @Test
    @DisplayName("Recently updated municipalities are not marked as invalid")
    void shouldNotMarkRecentMunicipalitiesAsInvalid() {
        LocalDateTime now = LocalDateTime.now();

        RegistryPlaceCsvDTO dto = buildCsvDto("L219", "Torino", "Torino", "Piemonte");
        batchService.saveOrUpdateAll(List.of(dto), now);

        // updatePastOnes with a time before lastUpdate should NOT invalidate
        batchService.updatePastOnes(now.minusHours(1));

        RegistryPlaceEntity entity = repository.findById(
                new RegistryPlaceId("Piemonte", "L219", "Torino", "Torino")).orElseThrow();
        assertTrue(entity.isCurrentValid());
    }

    private RegistryPlaceCsvDTO buildCsvDto(String codiceCatastale, String denominazione,
                                             String provincia, String regione) {
        RegistryPlaceCsvDTO dto = new RegistryPlaceCsvDTO();
        dto.setCodiceCatastaleDelComune(codiceCatastale);
        dto.setDenominazioneInItaliano(denominazione);
        dto.setDenominazioneUnitaTerritorialeSovracomunale(provincia);
        dto.setDenominazioneRegione(regione);
        dto.setCodiceRegione("01");
        dto.setCodiceUniteTerritorialeSovracomunale("001");
        dto.setCodiceProvinciaStorico("001");
        dto.setProgressivoDelComune("001");
        dto.setCodiceComuneFormatoAlfanumerico("TO001");
        dto.setDenominazioneItalianaStraniera(denominazione);
        dto.setDenominazioneAltraLingua("");
        dto.setCodiceRipartizioneGeografica("1");
        dto.setRipartizioneGeografica("Nord-ovest");
        dto.setTipologiaUnitaTerritorialeSovracomunale("Citta metropolitana");
        dto.setFlagComuneCapoluogoDiProvinciaCittaMetropolitanaLiberoConsorzio("1");
        dto.setSiglaAutomobilistica("TO");
        dto.setCodiceComuneFormatoNumerico("001272");
        dto.setCodiceComuneNumericoCon110ProvinceDal2010Al2016("001272");
        dto.setCodiceComuneNumericoCon107ProvinceDal2006Al2009("001272");
        dto.setCodiceComuneNumericoCon103ProvinceDal1995Al2005("001272");
        dto.setCodiceNUTS12010("ITC");
        dto.setCodiceNUTS22010("ITC1");
        dto.setCodiceNUTS32010("ITC11");
        dto.setCodiceNUTS12021("ITC");
        dto.setCodiceNUTS22021("ITC1");
        dto.setCodiceNUTS32021("ITC11");
        return dto;
    }
}
