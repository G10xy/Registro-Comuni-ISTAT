package it.municipalitiesregistry.persistence.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RegistryPlaceId implements Serializable {


    private String denominazioneRegione;

    private String codiceCatastaleDelComune;

    private String denominazioneInItaliano;

    private String denominazioneUnitaTerritorialeSovracomunale;
}
