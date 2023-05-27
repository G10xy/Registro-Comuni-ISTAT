package it.municipalitiesregistry.api;

import graphql.kickstart.tools.GraphQLQueryResolver;
import it.municipalitiesregistry.model.RegistryPlaceDTO;
import it.municipalitiesregistry.service.RegistryPlaceQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Query implements GraphQLQueryResolver {

    private final RegistryPlaceQueryService registryPlaceQueryService;

    public RegistryPlaceDTO municipalityByCadastralCode(String codiceCatastaleDelComune) {
        return registryPlaceQueryService.findByCadastralCode(codiceCatastaleDelComune);
    }

    public List<String> allRegions() {
        return registryPlaceQueryService.getAllRegions();
    }

    public List<String> allProvincesByRegion(String region) {
        return registryPlaceQueryService.getAllProvincesByRegion(region);
    }

    public List<RegistryPlaceDTO> allMunicipalitiesByProvinceAndRegion(String province, String region) {
        return registryPlaceQueryService.getAllCitiesByProvinceAndRegion(province, region);
    }
}
