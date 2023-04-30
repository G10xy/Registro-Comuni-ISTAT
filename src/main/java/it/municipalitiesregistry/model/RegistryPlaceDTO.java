package it.municipalitiesregistry.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class RegistryPlaceDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3664244847615509887L;

    @CsvBindByPosition(position = 0)
    private String codiceRegione;

    @CsvBindByPosition(position = 1)
    private String codiceUniteTerritorialeSovracomunale;

    @CsvBindByPosition(position = 2)
    private String codiceProvinciaStorico;

    @CsvBindByPosition(position = 3)
    private String progressivoDelComune;

    @CsvBindByPosition(position = 4)
    private String codiceComuneFormatoAlfanumerico;

    @CsvBindByPosition(position = 5)
    private String denominazioneItalianaStraniera;

    @CsvBindByPosition(position = 6)
    private String denominazioneInItaliano;

    @CsvBindByPosition(position = 7)
    private String denominazioneAltraLingua;

    @CsvBindByPosition(position = 8)
    private String codiceRipartizioneGeografica;

    @CsvBindByPosition(position = 9)
    private String ripartizioneGeografica;

    @CsvBindByPosition(position = 10)
    private String denominazioneRegione;

    @CsvBindByPosition(position = 11)
    private String denominazioneUnitaTerritorialeSovracomunale;

    @CsvBindByPosition(position = 12)
    private String tipologiaUnitaTerritorialeSovracomunale;

    @CsvBindByPosition(position = 13)
    private String flagComuneCapoluogoDiProvinciaCittaMetropolitanaLiberoConsorzio;

    @CsvBindByPosition(position = 14)
    private String siglaAutomobilistica;

    @CsvBindByPosition(position = 15)
    private String codiceComuneFormatoNumerico;

    @CsvBindByPosition(position = 16)
    private String codiceComuneNumericoCon110ProvinceDal2010Al2016;

    @CsvBindByPosition(position = 17)
    private String codiceComuneNumericoCon107ProvinceDal2006Al2009;

    @CsvBindByPosition(position = 18)
    private String codiceComuneNumericoCon103ProvinceDal1995Al2005;

    @CsvBindByPosition(position = 19)
    private String codiceCatastaleDelComune;

    @CsvBindByPosition(position = 20)
    private String codiceNUTS12010;

    @CsvBindByPosition(position = 21)
    private String codiceNUTS22010;

    @CsvBindByPosition(position = 22)
    private String codiceNUTS32010;

    @CsvBindByPosition(position = 23)
    private String codiceNUTS12021;

    @CsvBindByPosition(position = 24)
    private String codiceNUTS22021;

    @CsvBindByPosition(position = 25)
    private String codiceNUTS32021;
}
