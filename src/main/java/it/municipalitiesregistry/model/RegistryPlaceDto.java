package it.municipalitiesregistry.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegistryPlaceDto(String codiceRegione,
                               String codiceUniteTerritorialeSovracomunale,
                               String codiceProvinciaStorico,
                               String progressivoDelComune,
                               String codiceComuneFormatoAlfanumerico,
                               String denominazioneItalianaStraniera,
                               String denominazioneAltraLingua,
                               String codiceRipartizioneGeografica,
                               String ripartizioneGeografica,
                               String tipologiaUnitaTerritorialeSovracomunale,
                               String flagComuneCapoluogoDiProvinciaCittaMetropolitanaLiberoConsorzio,
                               String siglaAutomobilistica,
                               String codiceComuneFormatoNumerico,
                               String codiceComuneNumericoCon110ProvinceDal2010Al2016,
                               String codiceComuneNumericoCon107ProvinceDal2006Al2009,
                               String codiceComuneNumericoCon103ProvinceDal1995Al2005,
                               String codiceNUTS12010,
                               String codiceNUTS22010,
                               String codiceNUTS32010,
                               String codiceNUTS12021,
                               String codiceNUTS22021,
                               String codiceNUTS32021,
                               UUID uuidCode,
                               boolean currentValid,
                               LocalDateTime lastUpdate,
                               LocalDateTime validFrom,
                               LocalDateTime validTo
) {
}
