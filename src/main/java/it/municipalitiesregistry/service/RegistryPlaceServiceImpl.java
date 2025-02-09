package it.municipalitiesregistry.service;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import it.municipalitiesregistry.AllProvincesByRegionRequest;
import it.municipalitiesregistry.AllProvincesByRegionResponse;
import it.municipalitiesregistry.AllRegionsResponse;
import it.municipalitiesregistry.MunicipalitiesRequest;
import it.municipalitiesregistry.MunicipalitiesResponse;
import it.municipalitiesregistry.Municipality;
import it.municipalitiesregistry.MunicipalityByCadastralCodeRequest;
import it.municipalitiesregistry.MunicipalityByCadastralCodeResponse;
import it.municipalitiesregistry.RegistryPlaceServiceGrpc;
import it.municipalitiesregistry.model.RegistryPlaceDTO;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import java.util.List;

import static it.municipalitiesregistry.util.Utility.isNullOrEmpty;

@GrpcService
@RequiredArgsConstructor
public class RegistryPlaceServiceImpl extends RegistryPlaceServiceGrpc.RegistryPlaceServiceImplBase {

    private final RegistryPlaceQueryService registryPlaceQueryService;

    @Override
    public void getMunicipalityByCadastralCode(MunicipalityByCadastralCodeRequest request, StreamObserver<MunicipalityByCadastralCodeResponse> responseObserver) {
        RegistryPlaceDTO dto = registryPlaceQueryService.findByCadastralCode(request.getCodiceCatastaleDelComune());
        MunicipalityByCadastralCodeResponse response = MunicipalityByCadastralCodeResponse.newBuilder().setMunicipality(mapToMunicipality(dto)).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllRegions(Empty request, StreamObserver<AllRegionsResponse> responseObserver) {
        List<String> regions = registryPlaceQueryService.getAllRegions();
        AllRegionsResponse response = AllRegionsResponse.newBuilder().addAllRegions(regions).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllProvincesByRegion(AllProvincesByRegionRequest request, StreamObserver<AllProvincesByRegionResponse> responseObserver) {
        List<String> provinces = registryPlaceQueryService.getAllProvincesByRegion(request.getRegion());
        AllProvincesByRegionResponse response = AllProvincesByRegionResponse.newBuilder().addAllProvinces(provinces).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    @Override
    public void getMunicipalities(MunicipalitiesRequest request, StreamObserver<MunicipalitiesResponse> responseObserver) {
        String region = request.getRegion();
        String province = request.getProvince();
        List<RegistryPlaceDTO> list;
        if (!isNullOrEmpty(region) && !isNullOrEmpty(province)) {
            list = registryPlaceQueryService.getAllCitiesByProvinceAndRegion(province, region);
        } else if (!isNullOrEmpty(region)) {
            list = registryPlaceQueryService.getAllCitiesByRegion(region);
        } else if (!isNullOrEmpty(province)) {
            list = registryPlaceQueryService.getAllCitiesByProvince(province);
        } else {
            throw new IllegalArgumentException("At least one parameter is required");
        }
        List<Municipality> municipalities = list.stream().map(this::mapToMunicipality).toList();
        MunicipalitiesResponse response = MunicipalitiesResponse.newBuilder().addAllMunicipalities(municipalities).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private Municipality mapToMunicipality(RegistryPlaceDTO dto) {
        if (dto == null) {
            return null;
        }

        return Municipality.newBuilder()
                .setDenominazioneRegione(dto.getDenominazioneRegione())
                .setCodiceCatastaleDelComune(dto.getCodiceCatastaleDelComune())
                .setDenominazioneInItaliano(dto.getDenominazioneInItaliano())
                .setDenominazioneUnitaTerritorialeSovracomunale(dto.getDenominazioneUnitaTerritorialeSovracomunale())
                .setCodiceRegione(dto.getCodiceRegione())
                .setCodiceUniteTerritorialeSovracomunale(dto.getCodiceUniteTerritorialeSovracomunale())
                .setCodiceProvinciaStorico(dto.getCodiceProvinciaStorico())
                .setProgressivoDelComune(dto.getProgressivoDelComune())
                .setCodiceComuneFormatoAlfanumerico(dto.getCodiceComuneFormatoAlfanumerico())
                .setDenominazioneItalianaStraniera(dto.getDenominazioneItalianaStraniera())
                .setDenominazioneAltraLingua(dto.getDenominazioneAltraLingua())
                .setCodiceRipartizioneGeografica(dto.getCodiceRipartizioneGeografica())
                .setRipartizioneGeografica(dto.getRipartizioneGeografica())
                .setTipologiaUnitaTerritorialeSovracomunale(dto.getTipologiaUnitaTerritorialeSovracomunale())
                .setFlagComuneCapoluogoDiProvinciaCittaMetropolitanaLiberoConsorzio(dto.getFlagComuneCapoluogoDiProvinciaCittaMetropolitanaLiberoConsorzio())
                .setSiglaAutomobilistica(dto.getSiglaAutomobilistica())
                .setCodiceComuneFormatoNumerico(dto.getCodiceComuneFormatoNumerico())
                .setCodiceComuneNumericoCon110ProvinceDal2010Al2016(dto.getCodiceComuneNumericoCon110ProvinceDal2010Al2016())
                .setCodiceComuneNumericoCon107ProvinceDal2006Al2009(dto.getCodiceComuneNumericoCon107ProvinceDal2006Al2009())
                .setCodiceComuneNumericoCon103ProvinceDal1995Al2005(dto.getCodiceComuneNumericoCon103ProvinceDal1995Al2005())
                .setCodiceNuts12010(dto.getCodiceNUTS12010())
                .setCodiceNuts22010(dto.getCodiceNUTS22010())
                .setCodiceNuts32010(dto.getCodiceNUTS32010())
                .setCodiceNuts12021(dto.getCodiceNUTS12021())
                .setCodiceNuts22021(dto.getCodiceNUTS22021())
                .setCodiceNuts32021(dto.getCodiceNUTS32021())
                .build();
    }
}
