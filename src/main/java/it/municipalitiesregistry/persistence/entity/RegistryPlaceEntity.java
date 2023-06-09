package it.municipalitiesregistry.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Version;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Table(name = "registro_comuni")
public class RegistryPlaceEntity {

    @EmbeddedId
    private RegistryPlaceId id;

    @Column(name = "codice_regione")
    private String codiceRegione;

    @Column(name = "codice_unita_territoriale_sovracomunale")
    private String codiceUniteTerritorialeSovracomunale;

    @Column(name = "codice_provincia_storico")
    private String codiceProvinciaStorico;

    @Column(name = "progressivo_del_comune")
    private String progressivoDelComune;

    @Column(name = "codice_comune_formato_alfanumerico")
    private String codiceComuneFormatoAlfanumerico;

    @Column(name = "denominazione_italiana_e_straniera")
    private String denominazioneItalianaStraniera;

    @Column(name = "denominazione_altra_lingua")
    private String denominazioneAltraLingua;

    @Column(name = "codice_ripartizione_geografica")
    private String codiceRipartizioneGeografica;

    @Column(name = "ripartizione_geografica")
    private String ripartizioneGeografica;

    @Column(name = "tipologia_di_unita_territoriale_sovracomunale")
    private String tipologiaUnitaTerritorialeSovracomunale;

    @Column(name = "flag_comune_capoluogo_di_provincia_citta_metropolitana_libero_consorzio")
    private String flagComuneCapoluogoDiProvinciaCittaMetropolitanaLiberoConsorzio;

    @Column(name = "sigla_automobilistica")
    private String siglaAutomobilistica;

    @Column(name = "codice_comune_formato_numerico")
    private String codiceComuneFormatoNumerico;

    @Column(name = "codice_comune_numerico_con_110_province_dal_2010_al_2016")
    private String codiceComuneNumericoCon110ProvinceDal2010Al2016;

    @Column(name = "codice_comune_numerico_con_107_province_dal_2006_al_2009")
    private String codiceComuneNumericoCon107ProvinceDal2006Al2009;

    @Column(name = "codice_comune_numerico_con_103_province_dal_1995_al_2005")
    private String codiceComuneNumericoCon103ProvinceDal1995Al2005;

    @Column(name = "codice_NUTS1_2010")
    private String codiceNUTS12010;

    @Column(name = "codice_NUTS2_2010")
    private String codiceNUTS22010;

    @Column(name = "codice_NUTS3_2010")
    private String codiceNUTS32010;

    @Column(name = "codice_NUTS1_2021")
    private String codiceNUTS12021;

    @Column(name = "codice_NUTS2_2021")
    private String codiceNUTS22021;

    @Column(name = "codice_NUTS3_2021")
    private String codiceNUTS32021;

    @Column(name = "attualmente_valido", nullable = false)
    private boolean currentValid;

    @UpdateTimestamp
    @Column(name = "ultimo_aggiornamento", nullable = false)
    private LocalDateTime lastUpdate;

    @CreationTimestamp
    @Column(name = "inizio_validita", nullable = false)
    private LocalDateTime validFrom;

    @Column(name = "fine_validita")
    private LocalDateTime validTo;

    @Version
    @Column(name = "versione")
    private long version;

    @PrePersist
    public void prePersist() {
        this.version = 1;
    }
}
