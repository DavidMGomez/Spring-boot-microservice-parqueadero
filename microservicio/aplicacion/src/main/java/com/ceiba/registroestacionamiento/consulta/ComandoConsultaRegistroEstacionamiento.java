package com.ceiba.registroestacionamiento.consulta;

import com.ceiba.excepcion.ExcepcionValorObligatorio;
import com.ceiba.registroestacionamiento.modelo.entidad.EstadoRegistro;

import java.util.Objects;


public class ComandoConsultaRegistroEstacionamiento {

    private  EstadoRegistro estadoRegistro;

    public ComandoConsultaRegistroEstacionamiento(EstadoRegistro estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public EstadoRegistro getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(EstadoRegistro estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }
}
