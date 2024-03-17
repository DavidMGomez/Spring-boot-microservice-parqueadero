package com.ceiba.registroestacionamiento.controlador;

import com.ceiba.ApplicationMock;
import com.ceiba.registroestacionamiento.comando.ComandoSolicitudRegistrarIngreso;
import com.ceiba.registroestacionamiento.comando.ComandoSolicitudRegistrarSalida;
import com.ceiba.registroestacionamiento.modelo.dto.ResumenRegistroEstacionamientoDTO;
import com.ceiba.registroestacionamiento.modelo.entidad.EstadoRegistro;
import com.ceiba.registroestacionamiento.modelo.entidad.RegistroEstacionamiento;
import com.ceiba.registroestacionamiento.puerto.repositorio.RepositorioRegistroEstacionamiento;
import com.ceiba.vehiculo.entidad.TipoVehiculo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ComandoControladorRegistroEstacionamiento.class)
@ContextConfiguration(classes = ApplicationMock.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ComandoControladorRegistroEstacionamientoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    RepositorioRegistroEstacionamiento repositorioRegistroEstacionamiento;

    // Aquí deberías tener instancias simuladas (mocks) de los manejadores necesarios para las pruebas

    @BeforeEach
    public void setUp() {
        // Configuración previa antes de cada prueba, si es necesario
    }

    @Test
    public void ingresarRegistroDebeRetornarEstadoEnUso() throws Exception {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        ComandoSolicitudRegistrarIngreso solicitud = new ComandoSolicitudRegistrarIngreso("FQY112", TipoVehiculo.PARTICULAR);
        // Act & Assert
        MvcResult mvcResult = mockMvc.perform(post("/registro-estacionamiento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(solicitud)))
                .andExpect(status().isCreated()).andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        JsonNode responseJson = objectMapper.readTree(responseContent);
        Long id = responseJson.get("valor").asLong();
        RegistroEstacionamiento registroEstacionamiento = repositorioRegistroEstacionamiento.obtener(id);
        Assertions.assertEquals(registroEstacionamiento.getVehiculoRegistro().getPlaca(), solicitud.getPlacaVehiculo());
        Assertions.assertEquals(registroEstacionamiento.getVehiculoRegistro().getTipo(), solicitud.getTipoVehiculo());
        Assertions.assertEquals(EstadoRegistro.EN_USO, registroEstacionamiento.getEstadoRegistro());
        Assertions.assertNull(registroEstacionamiento.getFechaHoraSalida());
    }

    @Test
    public void registrarSalidaDebeRetornarEstadoFinalizado() throws Exception {
        // Arrange
        ComandoSolicitudRegistrarIngreso solicitud = new ComandoSolicitudRegistrarIngreso("ABC123", TipoVehiculo.PARTICULAR);
        // Act & Assert
        MvcResult mvcResult = mockMvc.perform(post("/registro-estacionamiento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(solicitud)))
                .andExpect(status().isCreated()).andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        JsonNode responseJson = objectMapper.readTree(responseContent);
        Long id = responseJson.get("valor").asLong();

        // Arrange
        ComandoSolicitudRegistrarSalida solicitudSalida = new ComandoSolicitudRegistrarSalida(id);
        // Act & Assert
        MvcResult mvcResultSalida = mockMvc.perform(post("/registro-estacionamiento/registrar-salida")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solicitudSalida)))
                .andExpect(status().isCreated()).andReturn();

        String responseContentSalida = mvcResultSalida.getResponse().getContentAsString();
        JsonNode responseJsonSalida = objectMapper.readTree(responseContentSalida);
        ResumenRegistroEstacionamientoDTO resumenRegistroEstacionamientoDTO = objectMapper.convertValue(responseJsonSalida.get("valor"), ResumenRegistroEstacionamientoDTO.class);

        Assertions.assertEquals(resumenRegistroEstacionamientoDTO.getPlaca(), solicitud.getPlacaVehiculo());
        Assertions.assertEquals(resumenRegistroEstacionamientoDTO.getId(), id);
        Assertions.assertNotNull(resumenRegistroEstacionamientoDTO.getMontoAPagar());
        Assertions.assertEquals(EstadoRegistro.FINALIZADO, resumenRegistroEstacionamientoDTO.getEstadoRegistro());
    }

    @Test
    public void registrarSalidaDebeRegistroNoExisteDebeLanzarException() throws Exception {


        // Arrange
        ComandoSolicitudRegistrarSalida solicitudSalida = new ComandoSolicitudRegistrarSalida(66666L);
        // Act & Assert
        MvcResult mvcResultSalida = mockMvc.perform(post("/registro-estacionamiento/registrar-salida")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(solicitudSalida)))
                .andExpect(status().is5xxServerError()).andReturn();

    }

}