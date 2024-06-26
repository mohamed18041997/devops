import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.services.IEventServices;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebMvcTest(EventRestController.class) // Configuration pour tester uniquement le contrôleur EventRestController
@AutoConfigureMockMvc
public class EventRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IEventServices eventServices;

    @InjectMocks
    private EventRestController eventRestController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(eventRestController).build();
    }

    @Test
    public void testAddParticipant() throws Exception {
        Participant participant = new Participant(); // Créer un participant de test
        participant.setId(1); // Définir l'ID du participant

        when(eventServices.addParticipant(any())).thenReturn(participant); // Mock de la méthode addParticipant

        mockMvc.perform(post("/event/addPart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(participant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1)); // Vérifier que l'ID retourné est celui attendu
    }

    @Test
    public void testAddEventPart() throws Exception {
        Event event = new Event(); // Créer un événement de test
        event.setId(1); // Définir l'ID de l'événement

        when(eventServices.addAffectEvenParticipant(any(), anyInt())).thenReturn(event); // Mock de la méthode addAffectEvenParticipant

        mockMvc.perform(post("/event/addEvent/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(event)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1)); // Vérifier que l'ID retourné est celui attendu
    }

    // Ajoutez d'autres méthodes de test pour les autres endpoints si nécessaire
}
