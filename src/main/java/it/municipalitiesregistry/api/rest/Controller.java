package it.municipalitiesregistry.api.rest;


import it.municipalitiesregistry.model.RegistryPlaceDTO;
import it.municipalitiesregistry.service.RegistryPlaceQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/rest/v1/registro-comuni")
@RequiredArgsConstructor
public class Controller {

    private final RegistryPlaceQueryService registryPlaceQueryService;

    @GetMapping("/by-codice-catastale")
    public ResponseEntity<RegistryPlaceDTO> getPlaceByCodiceCatastaleDelComune(@RequestParam("codice-catastale") String codiceCatastale) {
        return ResponseEntity.ok(registryPlaceQueryService.findByCadastralCode(codiceCatastale));
    }

    @GetMapping("/regioni")
    public ResponseEntity<Collection<String>> getRegions() {
        return ResponseEntity.ok(registryPlaceQueryService.getAllRegions());
    }

    @GetMapping("/province")
    public ResponseEntity<Collection<String>> getProvinces(@RequestParam(value = "regione") String region) {
        return ResponseEntity.ok(registryPlaceQueryService.getAllProvincesByRegion(region));
    }

    @GetMapping
    public ResponseEntity<Collection<RegistryPlaceDTO>> getMunicipalities(@RequestParam(value = "regione", required = false) String region, @RequestParam(value = "provincia", required = false) String province) {
        if (region != null && province != null) {
            return ResponseEntity.ok(registryPlaceQueryService.getAllCitiesByProvinceAndRegion(province, region));
        } else if (region != null) {
            return ResponseEntity.ok(registryPlaceQueryService.getAllCitiesByRegion(region));
        } else if (province != null) {
            return ResponseEntity.ok(registryPlaceQueryService.getAllCitiesByProvince(province));
        } else {
            throw new IllegalArgumentException("At least one parameter is required");
        }
    }

}
