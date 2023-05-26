package it.municipalitiesregistry.model;

import lombok.Data;

@Data
public class RegistryPlaceDTO {
    private String denominazioneRegione;
    private String codiceCatastaleDelComune;
    private String denominazioneInItaliano;
    private String denominazioneUnitaTerritorialeSovracomunale;
    private String codiceRegione;
    private String codiceUniteTerritorialeSovracomunale;
    private String codiceProvinciaStorico;
    private String progressivoDelComune;
    private String codiceComuneFormatoAlfanumerico;
    private String denominazioneItalianaStraniera;
    private String denominazioneAltraLingua;
    private String codiceRipartizioneGeografica;
    private String ripartizioneGeografica;
    private String tipologiaUnitaTerritorialeSovracomunale;
    private String flagComuneCapoluogoDiProvinciaCittaMetropolitanaLiberoConsorzio;
    private String siglaAutomobilistica;
    private String codiceComuneFormatoNumerico;
    private String codiceComuneNumericoCon110ProvinceDal2010Al2016;
    private String codiceComuneNumericoCon107ProvinceDal2006Al2009;
    private String codiceComuneNumericoCon103ProvinceDal1995Al2005;
    private String codiceNUTS12010;
    private String codiceNUTS22010;
    private String codiceNUTS32010;
    private String codiceNUTS12021;
    private String codiceNUTS22021;
    private String codiceNUTS32021;
}
