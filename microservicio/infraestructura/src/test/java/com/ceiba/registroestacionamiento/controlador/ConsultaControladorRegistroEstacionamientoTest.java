package com.ceiba.registroestacionamiento.controlador;

import com.ceiba.ApplicationMock;
import com.ceiba.registroestacionamiento.consulta.ComandoConsultaRegistroEstacionamiento;
import com.ceiba.registroestacionamiento.modelo.dto.ResumenRegistroEstacionamientoDTO;
import com.ceiba.registroestacionamiento.modelo.entidad.EstadoRegistro;
import com.ceiba.registroestacionamiento.puerto.repositorio.RepositorioRegistroEstacionamiento;
import com.fasterxml.jackson.core.type.TypeReference;
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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ConsultaControladorRegistroEstacionamiento.class)
@ContextConfiguration(classes = ApplicationMock.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ConsultaControladorRegistroEstacionamientoTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RepositorioRegistroEstacionamiento registroEstacionamiento;

    // Aquí deberías tener instancias simuladas (mocks) del manejador necesario para las pruebas

    @BeforeEach
    public void setUp() {
        // Configuración previa antes de cada prueba, si es necesario
    }

    @Test
    void obtenerRegistrosEstacionamientoEstadoFinalizadoDebeRetornarEstadoOk() throws Exception {
        // Arrange
        ComandoConsultaRegistroEstacionamiento consulta = new ComandoConsultaRegistroEstacionamiento(EstadoRegistro.FINALIZADO);
        // Act & Assert
        MvcResult mvcResultSalida = mockMvc.perform(get("/registro-estacionamiento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("estadoRegistro", consulta.getEstadoRegistro().name()))
                .andExpect(status().isOk()).andReturn();

        String responseContentSalida = mvcResultSalida.getResponse().getContentAsString();
        JsonNode responseJsonSalida = objectMapper.readTree(responseContentSalida);
        List<ResumenRegistroEstacionamientoDTO> lista = objectMapper.convertValue(responseJsonSalida, new TypeReference<>() {
        });

        for (ResumenRegistroEstacionamientoDTO resumenRegistroEstacionamientoDTO :
                lista) {
            Assertions.assertNotNull(resumenRegistroEstacionamientoDTO.getPlaca());
            Assertions.assertNotNull(resumenRegistroEstacionamientoDTO.getId());
            Assertions.assertNotNull(resumenRegistroEstacionamientoDTO.getMontoAPagar());
            Assertions.assertNotNull(resumenRegistroEstacionamientoDTO.getFechaHoraSalida());
            Assertions.assertEquals(EstadoRegistro.FINALIZADO, resumenRegistroEstacionamientoDTO.getEstadoRegistro());
        }

    }

    @Test
    void obtenerRegistrosEstacionamientoEstadoNullDebeRetornarError() throws Exception {

        mockMvc.perform(get("/registro-estacionamiento")
                        .contentType(MediaType.APPLICATION_JSON)
                        )
                .andExpect(status().isBadRequest()).andReturn();
    }
    @Test
    void obtenerRegistrosEstacionamientoEstadoEnUsoDebeRetornarEstadoOk() throws Exception {
        // Arrange
        ComandoConsultaRegistroEstacionamiento consulta = new ComandoConsultaRegistroEstacionamiento(EstadoRegistro.EN_USO);
        // Act & Assert
        MvcResult mvcResultSalida = mockMvc.perform(get("/registro-estacionamiento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("estadoRegistro", consulta.getEstadoRegistro().name()))
                .andExpect(status().isOk()).andReturn();

        String responseContentSalida = mvcResultSalida.getResponse().getContentAsString();
        JsonNode responseJsonSalida = objectMapper.readTree(responseContentSalida);
        List<ResumenRegistroEstacionamientoDTO> lista = objectMapper.convertValue(responseJsonSalida, new TypeReference<>() {
        });

        for (ResumenRegistroEstacionamientoDTO resumenRegistroEstacionamientoDTO :
                lista) {
            Assertions.assertNotNull(resumenRegistroEstacionamientoDTO.getPlaca());
            Assertions.assertNotNull(resumenRegistroEstacionamientoDTO.getId());
            Assertions.assertNotNull(resumenRegistroEstacionamientoDTO.getMontoAPagar());
            Assertions.assertNull(resumenRegistroEstacionamientoDTO.getFechaHoraSalida());
            Assertions.assertEquals(EstadoRegistro.EN_USO, resumenRegistroEstacionamientoDTO.getEstadoRegistro());
        }

    }
}