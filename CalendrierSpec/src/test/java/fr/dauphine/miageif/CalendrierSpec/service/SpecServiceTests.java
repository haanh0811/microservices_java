package fr.dauphine.miageif.CalendrierSpec.service;

import fr.dauphine.miageif.CalendrierSpec.entity.Spec;
import fr.dauphine.miageif.CalendrierSpec.repository.SpecRepository;
import fr.dauphine.miageif.CalendrierSpec.service.impl.SpecServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SpecServiceTests {

    @Mock
    private SpecRepository specRepository;

    @InjectMocks
    private SpecServiceImpl specService;

    @Test
    public void testGetSpecById() {
        Spec spec = new Spec("spec1", "Spécialisation Football", Arrays.asList());

        when(specRepository.findById(anyString())).thenReturn(Optional.of(spec));

        Spec result = specService.getSpecById("spec1");

        assertNotNull(result);
        assertEquals("spec1", result.getId());
        assertEquals("Spécialisation Football", result.getName());
    }
}
