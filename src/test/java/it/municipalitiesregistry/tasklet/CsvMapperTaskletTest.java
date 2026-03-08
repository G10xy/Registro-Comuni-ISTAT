package it.municipalitiesregistry.tasklet;

import com.opencsv.bean.CsvToBeanBuilder;
import it.municipalitiesregistry.model.RegistryPlaceCsvDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvMapperTaskletTest {

    @Test
    @DisplayName("Parse CSV and verify main fields for all municipalities")
    void shouldParseCsvFileCorrectly() {
        var inputStream = getClass().getClassLoader().getResourceAsStream("test-comuni.csv");
        assertNotNull(inputStream, "Test CSV file not found");

        try (var reader = new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1)) {
            List<RegistryPlaceCsvDTO> places = new CsvToBeanBuilder<RegistryPlaceCsvDTO>(reader)
                    .withSeparator(';')
                    .withSkipLines(1)
                    .withType(RegistryPlaceCsvDTO.class)
                    .build()
                    .parse();

            assertEquals(3, places.size());

            RegistryPlaceCsvDTO torino = places.getFirst();
            assertEquals("Torino", torino.getDenominazioneInItaliano());
            assertEquals("Piemonte", torino.getDenominazioneRegione());
            assertEquals("Torino", torino.getDenominazioneUnitaTerritorialeSovracomunale());
            assertEquals("L219", torino.getCodiceCatastaleDelComune());
            assertEquals("TO", torino.getSiglaAutomobilistica());
            assertEquals("01", torino.getCodiceRegione());

            RegistryPlaceCsvDTO milano = places.get(1);
            assertEquals("Milano", milano.getDenominazioneInItaliano());
            assertEquals("Lombardia", milano.getDenominazioneRegione());
            assertEquals("F205", milano.getCodiceCatastaleDelComune());

            RegistryPlaceCsvDTO roma = places.get(2);
            assertEquals("Roma", roma.getDenominazioneInItaliano());
            assertEquals("Lazio", roma.getDenominazioneRegione());
            assertEquals("H501", roma.getCodiceCatastaleDelComune());
        } catch (Exception e) {
            fail("CSV parsing failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("First CSV line (header) is skipped and not parsed as data")
    void shouldSkipHeaderLine() {
        var inputStream = getClass().getClassLoader().getResourceAsStream("test-comuni.csv");
        assertNotNull(inputStream);

        try (var reader = new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1)) {
            List<RegistryPlaceCsvDTO> places = new CsvToBeanBuilder<RegistryPlaceCsvDTO>(reader)
                    .withSeparator(';')
                    .withSkipLines(1)
                    .withType(RegistryPlaceCsvDTO.class)
                    .build()
                    .parse();

            // None of the parsed entries should contain "Codice Regione" (header value)
            places.forEach(p ->
                    assertNotEquals("Codice Regione", p.getCodiceRegione())
            );
        } catch (Exception e) {
            fail("CSV parsing failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("All NUTS code fields (2010 and 2021) are correctly mapped")
    void shouldMapAllNutsFields() {
        var inputStream = getClass().getClassLoader().getResourceAsStream("test-comuni.csv");
        assertNotNull(inputStream);

        try (var reader = new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1)) {
            List<RegistryPlaceCsvDTO> places = new CsvToBeanBuilder<RegistryPlaceCsvDTO>(reader)
                    .withSeparator(';')
                    .withSkipLines(1)
                    .withType(RegistryPlaceCsvDTO.class)
                    .build()
                    .parse();

            RegistryPlaceCsvDTO torino = places.getFirst();
            assertEquals("ITC", torino.getCodiceNUTS12010());
            assertEquals("ITC1", torino.getCodiceNUTS22010());
            assertEquals("ITC11", torino.getCodiceNUTS32010());
            assertEquals("ITC", torino.getCodiceNUTS12021());
            assertEquals("ITC1", torino.getCodiceNUTS22021());
            assertEquals("ITC11", torino.getCodiceNUTS32021());
        } catch (Exception e) {
            fail("CSV parsing failed: " + e.getMessage());
        }
    }
}
