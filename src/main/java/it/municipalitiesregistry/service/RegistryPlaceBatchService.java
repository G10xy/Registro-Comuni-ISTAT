package it.municipalitiesregistry.service;

import it.municipalitiesregistry.mapper.RegistryPlaceMapper;
import it.municipalitiesregistry.model.RegistryPlaceCsvDTO;
import it.municipalitiesregistry.persistence.entity.RegistryPlaceEntity;
import it.municipalitiesregistry.persistence.repository.RegistryPlaceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistryPlaceBatchService {

    private final RegistryPlaceRepository registryPlaceRepository;
    private final RegistryPlaceMapper mapper;

    @Transactional
    public void saveOrUpdate(RegistryPlaceCsvDTO place) {
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

    private RegistryPlaceEntity createNewEntity(RegistryPlaceCsvDTO place) {
        return mapper.dtoCsvToEntity(place);
    }

    private void fromRegistryPlaceCsvToEntity(RegistryPlaceCsvDTO place, RegistryPlaceEntity entity) {
        entity.setCodiceRegione(place.getCodiceRegione());
        entity.setCodiceUniteTerritorialeSovracomunale(place.getCodiceUniteTerritorialeSovracomunale());
        entity.setCodiceProvinciaStorico(place.getCodiceProvinciaStorico());
        entity.setProgressivoDelComune(place.getProgressivoDelComune());
        entity.setCodiceComuneFormatoAlfanumerico(place.getCodiceComuneFormatoAlfanumerico());
        entity.setDenominazioneItalianaStraniera(place.getDenominazioneItalianaStraniera());
        entity.setDenominazioneAltraLingua(place.getDenominazioneAltraLingua());
        entity.setCodiceRipartizioneGeografica(place.getCodiceRipartizioneGeografica());
        entity.setRipartizioneGeografica(place.getRipartizioneGeografica());
        entity.setTipologiaUnitaTerritorialeSovracomunale(place.getTipologiaUnitaTerritorialeSovracomunale());
        entity.setFlagComuneCapoluogoDiProvinciaCittaMetropolitanaLiberoConsorzio(place.getFlagComuneCapoluogoDiProvinciaCittaMetropolitanaLiberoConsorzio());
        entity.setSiglaAutomobilistica(place.getSiglaAutomobilistica());
        entity.setCodiceComuneFormatoNumerico(place.getCodiceComuneFormatoNumerico());
        entity.setCodiceComuneNumericoCon110ProvinceDal2010Al2016(place.getCodiceComuneNumericoCon110ProvinceDal2010Al2016());
        entity.setCodiceComuneNumericoCon107ProvinceDal2006Al2009(place.getCodiceComuneNumericoCon107ProvinceDal2006Al2009());
        entity.setCodiceComuneNumericoCon103ProvinceDal1995Al2005(place.getCodiceComuneNumericoCon103ProvinceDal1995Al2005());
        entity.setCodiceNUTS12010(place.getCodiceNUTS12010());
        entity.setCodiceNUTS22010(place.getCodiceNUTS22010());
        entity.setCodiceNUTS32010(place.getCodiceNUTS32010());
        entity.setCodiceNUTS12021(place.getCodiceNUTS12021());
        entity.setCodiceNUTS22021(place.getCodiceNUTS22021());
        entity.setCodiceNUTS32021(place.getCodiceNUTS32021());
    }
}
