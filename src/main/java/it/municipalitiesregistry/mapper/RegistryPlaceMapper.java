package it.municipalitiesregistry.mapper;


import it.municipalitiesregistry.model.RegistryPlaceDTO;
import it.municipalitiesregistry.model.RegistryPlaceCsvDTO;
import it.municipalitiesregistry.persistence.entity.RegistryPlaceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface RegistryPlaceMapper {

    @Mapping(target = "id.denominazioneRegione", source = "denominazioneRegione")
    @Mapping(target = "id.codiceCatastaleDelComune", source = "codiceCatastaleDelComune")
    @Mapping(target = "id.denominazioneInItaliano", source = "denominazioneInItaliano")
    @Mapping(target = "id.denominazioneUnitaTerritorialeSovracomunale", source = "denominazioneUnitaTerritorialeSovracomunale")
    @Mapping(target = "currentValid", constant = "true")
    RegistryPlaceEntity dtoCsvToEntity(RegistryPlaceCsvDTO place);

    @Mapping(source = "id.denominazioneRegione", target = "denominazioneRegione")
    @Mapping(source = "id.codiceCatastaleDelComune", target = "codiceCatastaleDelComune")
    @Mapping(source = "id.denominazioneInItaliano", target = "denominazioneInItaliano")
    @Mapping(source = "id.denominazioneUnitaTerritorialeSovracomunale", target = "denominazioneUnitaTerritorialeSovracomunale")
    RegistryPlaceDTO fromEntityToResponseDto(RegistryPlaceEntity entity);
}
