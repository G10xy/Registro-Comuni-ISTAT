package it.municipalitiesregistry.service;

import it.municipalitiesregistry.model.RegistryPlaceDTO;
import it.municipalitiesregistry.persistence.entity.RegistryPlaceEntity;
import it.municipalitiesregistry.persistence.entity.RegistryPlaceId;
import it.municipalitiesregistry.persistence.repository.RegistryPlaceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistryPlaceService {

    private final RegistryPlaceRepository registryPlaceRepository;

    @Transactional
    public void saveOrUpdate(RegistryPlaceDTO place) {
        Optional<RegistryPlaceEntity> currentEntity = registryPlaceRepository.findByCompositeId(place.getCodiceCatastaleDelComune(), place.getDenominazioneInItaliano(), place.getDenominazioneUnitaTerritorialeSovracomunale(), place.getDenominazioneRegione());
        if (currentEntity.isPresent()) {
            fromRegistryPlaceCsvToEntity(place, currentEntity.get());
        } else {
            var newEntity = createNewEntity(place);
            registryPlaceRepository.save(newEntity);
        }
    }

    public void updatePastOnes(LocalDateTime dateTime) {
        registryPlaceRepository.updatePastOnes(dateTime);
    }

    private RegistryPlaceEntity createNewEntity(RegistryPlaceDTO place) {
        RegistryPlaceEntity registryPlaceEntity = new RegistryPlaceEntity();
        RegistryPlaceId id = new RegistryPlaceId();
        id.setDenominazioneRegione(place.getDenominazioneRegione());
        id.setCodiceCatastaleDelComune(place.getCodiceCatastaleDelComune());
        id.setDenominazioneInItaliano(place.getDenominazioneInItaliano());
        id.setDenominazioneUnitaTerritorialeSovracomunale(place.getDenominazioneUnitaTerritorialeSovracomunale());
        registryPlaceEntity.setId(id);
        registryPlaceEntity.setCodiceRegione( place.getCodiceRegione() );
        registryPlaceEntity.setCodiceUniteTerritorialeSovracomunale( place.getCodiceUniteTerritorialeSovracomunale() );
        registryPlaceEntity.setCodiceProvinciaStorico( place.getCodiceProvinciaStorico() );
        registryPlaceEntity.setProgressivoDelComune( place.getProgressivoDelComune() );
        registryPlaceEntity.setCodiceComuneFormatoAlfanumerico( place.getCodiceComuneFormatoAlfanumerico() );
        registryPlaceEntity.setDenominazioneItalianaStraniera( place.getDenominazioneItalianaStraniera() );
        registryPlaceEntity.setDenominazioneAltraLingua( place.getDenominazioneAltraLingua() );
        registryPlaceEntity.setCodiceRipartizioneGeografica( place.getCodiceRipartizioneGeografica() );
        registryPlaceEntity.setRipartizioneGeografica( place.getRipartizioneGeografica() );
        registryPlaceEntity.setTipologiaUnitaTerritorialeSovracomunale( place.getTipologiaUnitaTerritorialeSovracomunale() );
        registryPlaceEntity.setFlagComuneCapoluogoDiProvinciaCittaMetropolitanaLiberoConsorzio( place.getFlagComuneCapoluogoDiProvinciaCittaMetropolitanaLiberoConsorzio() );
        registryPlaceEntity.setSiglaAutomobilistica( place.getSiglaAutomobilistica() );
        registryPlaceEntity.setCodiceComuneFormatoNumerico( place.getCodiceComuneFormatoNumerico() );
        registryPlaceEntity.setCodiceComuneNumericoCon110ProvinceDal2010Al2016( place.getCodiceComuneNumericoCon110ProvinceDal2010Al2016() );
        registryPlaceEntity.setCodiceComuneNumericoCon107ProvinceDal2006Al2009( place.getCodiceComuneNumericoCon107ProvinceDal2006Al2009() );
        registryPlaceEntity.setCodiceComuneNumericoCon103ProvinceDal1995Al2005( place.getCodiceComuneNumericoCon103ProvinceDal1995Al2005() );
        registryPlaceEntity.setCodiceNUTS12010( place.getCodiceNUTS12010() );
        registryPlaceEntity.setCodiceNUTS22010( place.getCodiceNUTS22010() );
        registryPlaceEntity.setCodiceNUTS32010( place.getCodiceNUTS32010() );
        registryPlaceEntity.setCodiceNUTS12021( place.getCodiceNUTS12021() );
        registryPlaceEntity.setCodiceNUTS22021( place.getCodiceNUTS22021() );
        registryPlaceEntity.setCodiceNUTS32021( place.getCodiceNUTS32021() );
        registryPlaceEntity.setCurrentValid(true);
        registryPlaceEntity.setUuidCode(UUID.randomUUID());
        return registryPlaceEntity;
    }

    private void fromRegistryPlaceCsvToEntity(RegistryPlaceDTO place, RegistryPlaceEntity entity) {
        entity.setCodiceRegione( place.getCodiceRegione() );
        entity.setCodiceUniteTerritorialeSovracomunale( place.getCodiceUniteTerritorialeSovracomunale() );
        entity.setCodiceProvinciaStorico( place.getCodiceProvinciaStorico() );
        entity.setProgressivoDelComune( place.getProgressivoDelComune() );
        entity.setCodiceComuneFormatoAlfanumerico( place.getCodiceComuneFormatoAlfanumerico() );
        entity.setDenominazioneItalianaStraniera( place.getDenominazioneItalianaStraniera() );
        entity.setDenominazioneAltraLingua( place.getDenominazioneAltraLingua() );
        entity.setCodiceRipartizioneGeografica( place.getCodiceRipartizioneGeografica() );
        entity.setRipartizioneGeografica( place.getRipartizioneGeografica() );
        entity.setTipologiaUnitaTerritorialeSovracomunale( place.getTipologiaUnitaTerritorialeSovracomunale() );
        entity.setFlagComuneCapoluogoDiProvinciaCittaMetropolitanaLiberoConsorzio( place.getFlagComuneCapoluogoDiProvinciaCittaMetropolitanaLiberoConsorzio() );
        entity.setSiglaAutomobilistica( place.getSiglaAutomobilistica() );
        entity.setCodiceComuneFormatoNumerico( place.getCodiceComuneFormatoNumerico() );
        entity.setCodiceComuneNumericoCon110ProvinceDal2010Al2016( place.getCodiceComuneNumericoCon110ProvinceDal2010Al2016() );
        entity.setCodiceComuneNumericoCon107ProvinceDal2006Al2009( place.getCodiceComuneNumericoCon107ProvinceDal2006Al2009() );
        entity.setCodiceComuneNumericoCon103ProvinceDal1995Al2005( place.getCodiceComuneNumericoCon103ProvinceDal1995Al2005() );
        entity.setCodiceNUTS12010( place.getCodiceNUTS12010() );
        entity.setCodiceNUTS22010( place.getCodiceNUTS22010() );
        entity.setCodiceNUTS32010( place.getCodiceNUTS32010() );
        entity.setCodiceNUTS12021( place.getCodiceNUTS12021() );
        entity.setCodiceNUTS22021( place.getCodiceNUTS22021() );
        entity.setCodiceNUTS32021( place.getCodiceNUTS32021() );
    }
}
