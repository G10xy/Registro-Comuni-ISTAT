package it.municipalitiesregistry.api;


import it.municipalitiesregistry.model.RegistryPlaceDTO;
import it.municipalitiesregistry.service.RegistryPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/v1/registro-comuni")
@RequiredArgsConstructor
public class RestController {

    private final RegistryPlaceService registryPlaceService;

    @GetMapping("/by-codice-catastale")
    public RegistryPlaceDTO getRegistryPlaceByCodiceCatastaleDelComune(@RequestParam("codice-catastale") String codiceCatastale) {
        return registryPlaceService.findByMunicipalCode(codiceCatastale);
    }
}