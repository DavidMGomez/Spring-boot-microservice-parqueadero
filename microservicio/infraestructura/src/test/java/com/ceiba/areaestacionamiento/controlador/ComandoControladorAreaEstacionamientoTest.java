package com.ceiba.areaestacionamiento.controlador;

import com.ceiba.ApplicationMock;
import com.ceiba.areaestacionamiento.comando.ComandoSolicitudCreacionArea;
import com.ceiba.areaestacionamiento.entidad.AreaEstacionamiento;
import com.ceiba.areaestacionamiento.puerto.RepositorioAreaEstacionamiento;
import com.ceiba.vehiculo.entidad.TipoVehiculo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ComandoControladorAreaEstacionamiento.class)
@ContextConfiguration(classes = ApplicationMock.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ComandoControladorAreaEstacionamientoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RepositorioAreaEstacionamiento repositorioAreaEstacionamiento;

    @Test
    void testCrearAreaEstacionamiento() throws Exception {
        // Crea una solicitud de creación de área
        ComandoSolicitudCreacionArea solicitud = new ComandoSolicitudCreacionArea("Area 1", TipoVehiculo.PARTICULAR, 10L);
        // Realiza la petición POST al endpoint del controlador
        MvcResult mvcResult = mockMvc.perform(post("/area-estacionamiento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(solicitud)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.valor").isNumber()).andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();
        JsonNode responseJson = objectMapper.readTree(responseContent);
        Long id = responseJson.get("valor").asLong();
        AreaEstacionamiento area = repositorioAreaEstacionamiento.obtener(id);
        Assertions.assertEquals(area.getNombre(), solicitud.getNombre());
        Assertions.assertEquals(area.getCapacidadMaxima(), solicitud.getCapacidadMaxima());
        Assertions.assertEquals(area.getTipoVehiculo(), solicitud.getTipoVehiculo());
        Assertions.assertEquals(area.getCapacidadActual(), solicitud.getCapacidadMaxima());
    }

}