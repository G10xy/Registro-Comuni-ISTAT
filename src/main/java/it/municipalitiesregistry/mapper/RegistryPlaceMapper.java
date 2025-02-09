package it.municipalitiesregistry.mapper;


import it.municipalitiesregistry.model.RegistryPlaceCsvDTO;
import it.municipalitiesregistry.model.RegistryPlaceDTO;
import it.municipalitiesregistry.persistence.entity.RegistryPlaceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
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


    @Mapping(source = "codiceRegione", target = "codiceRegione")
    @Mapping(source = "codiceUniteTerritorialeSovracomunale", target = "codiceUniteTerritorialeSovracomunale")
    @Mapping(source = "codiceProvinciaStorico", target = "codiceProvinciaStorico")
    @Mapping(source = "progressivoDelComune", target = "progressivoDelComune")
    @Mapping(source = "codiceComuneFormatoAlfanumerico", target = "codiceComuneFormatoAlfanumerico")
    @Mapping(source = "denominazioneItalianaStraniera", target = "denominazioneItalianaStraniera")
    @Mapping(source = "denominazioneAltraLingua", target = "denominazioneAltraLingua")
    @Mapping(source = "codiceRipartizioneGeografica", target = "codiceRipartizioneGeografica")
    @Mapping(source = "ripartizioneGeografica", target = "ripartizioneGeografica")
    @Mapping(source = "tipologiaUnitaTerritorialeSovracomunale", target = "tipologiaUnitaTerritorialeSovracomunale")
    @Mapping(source = "flagComuneCapoluogoDiProvinciaCittaMetropolitanaLiberoConsorzio", target = "flagComuneCapoluogoDiProvinciaCittaMetropolitanaLiberoConsorzio")
    @Mapping(source = "siglaAutomobilistica", target = "siglaAutomobilistica")
    @Mapping(source = "codiceComuneFormatoNumerico", target = "codiceComuneFormatoNumerico")
    @Mapping(source = "codiceComuneNumericoCon110ProvinceDal2010Al2016", target = "codiceComuneNumericoCon110ProvinceDal2010Al2016")
    @Mapping(source = "codiceComuneNumericoCon107ProvinceDal2006Al2009", target = "codiceComuneNumericoCon107ProvinceDal2006Al2009")
    @Mapping(source = "codiceComuneNumericoCon103ProvinceDal1995Al2005", target = "codiceComuneNumericoCon103ProvinceDal1995Al2005")
    @Mapping(source = "codiceNUTS12010", target = "codiceNUTS12010")
    @Mapping(source = "codiceNUTS22010", target = "codiceNUTS22010")
    @Mapping(source = "codiceNUTS32010", target = "codiceNUTS32010")
    @Mapping(source = "codiceNUTS12021", target = "codiceNUTS12021")
    @Mapping(source = "codiceNUTS22021", target = "codiceNUTS22021")
    @Mapping(source = "codiceNUTS32021", target = "codiceNUTS32021")
    void mapDtoToEntity(RegistryPlaceCsvDTO place, @MappingTarget RegistryPlaceEntity entity);
}
