package it.municipalitiesregistry.persistence.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class RegistryPlaceId implements Serializable {


    @Column(name = "denominazione_regione")
    private String denominazioneRegione;

    @Column(name = "codice_catastale_del_comune")
    private String codiceCatastaleDelComune;

    @Column(name = "denominazione_in_italiano")
    private String denominazioneInItaliano;

    @Column(name = "denominazione_unita_territoriale_sovracomunale")
    private String denominazioneUnitaTerritorialeSovracomunale;
}
