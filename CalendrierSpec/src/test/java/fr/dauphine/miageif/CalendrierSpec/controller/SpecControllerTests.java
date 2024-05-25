package fr.dauphine.miageif.CalendrierSpec.controller;

import fr.dauphine.miageif.CalendrierSpec.entity.Spec;
import fr.dauphine.miageif.CalendrierSpec.service.impl.SpecServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(SpecController.class)
public class SpecControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpecServiceImpl specService;

    @Test
    public void testGetAllSpecs() throws Exception {
        Spec spec1 = new Spec("spec1", "Spécialisation Football", Arrays.asList());
        Spec spec2 = new Spec("spec2", "Spécialisation Rugby", Arrays.asList());
        when(specService.getAllSpecs()).thenReturn(Arrays.asList(spec1, spec2));

        mockMvc.perform(get("/api/spec/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("spec1")))
                .andExpect(jsonPath("$[0].name", is("Spécialisation Football")))
                .andExpect(jsonPath("$[1].id", is("spec2")))
                .andExpect(jsonPath("$[1].name", is("Spécialisation Rugby")));
    }
}
