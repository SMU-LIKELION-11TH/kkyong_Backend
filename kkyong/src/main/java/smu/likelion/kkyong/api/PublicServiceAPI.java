package smu.likelion.kkyong.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import smu.likelion.kkyong.domain.entity.Services;
import smu.likelion.kkyong.dto.service.ServiceRequestDto;
import smu.likelion.kkyong.dto.service.ServiceReturnDto;
import smu.likelion.kkyong.repository.ServiceRepository;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicServiceAPI {
    private final RestTemplate template;
    private final ServiceRepository serviceRepository;

    @Value("${PUBLIC-APIKEY}")
    private String apiKey;

    @Transactional
    public String createAllService() {
        URI uri = UriComponentsBuilder
                .fromUriString("http://openapi.seoul.go.kr:8088")
                .path("/" + apiKey + "/json/tvYeyakCOllect/1/2/")
                .encode()
                .build()
                .toUri();

        ResponseDto result = template.getForEntity(uri, ResponseDto.class).getBody();
        String resultString = template.getForObject(uri, String.class);
        System.out.println("result = " + result);

        // Row -> ServiceRequestDto -> Services
        List<ServiceRequestDto> dtos = Arrays.stream(result.tvYeyakCOllect.rows).map(row ->
                ServiceRequestDto.builder()
                        .serviceType(row.MAXCLASSNM)
                        .reservationType(row.DIV)
                        .serviceName(row.SVCNM)
                        .region(row.AREANM)
                        .place(row.PLACENM)
                        .serviceTarget(row.USETGTINFO)
                        .xCoord(row.X)
                        .yCoord(row.Y)
                        .serviceStart(row.RCPTENDDT)
                        .serviceEnd(row.SVCOPNENDDT)
                        .imageUrl(row.IMGURL)
                        .contact(row.TELNO)
                        .recruitCount(25)
                        .reservationStatus(true)
                        .bookmarkStatus(false)
                        .build()
        ).toList();

        List<Services> services = dtos.stream()
                .map(ServiceRequestDto::toEntity).toList();

        serviceRepository.saveAll(services);

        return resultString;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class ResponseDto {
        @JsonProperty("tvYeyakCOllect")
        private TvYeyakCOllect tvYeyakCOllect;

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Data
        public static class TvYeyakCOllect {
            @JsonProperty("row")
            private Row[] rows;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Data
        public static class Row {

            @JsonProperty("MAXCLASSNM")
            private String MAXCLASSNM;

            @JsonProperty("DIV")
            private String DIV;

            @JsonProperty("SVCNM")
            private String SVCNM;

            @JsonProperty("AREANM")
            private String AREANM;

            @JsonProperty("PLACENM")
            private String PLACENM;

            @JsonProperty("USETGTINFO")
            private String USETGTINFO;

            @JsonProperty("X")
            private String X;

            @JsonProperty("Y")
            private String Y;

            @JsonProperty("RCPTENDDT")
            private String RCPTENDDT;

            @JsonProperty("SVCOPNENDDT")
            private String SVCOPNENDDT;

            @JsonProperty("IMGURL")
            private String IMGURL;

            @JsonProperty("TELNO")
            private String TELNO;
        }
    }
}

