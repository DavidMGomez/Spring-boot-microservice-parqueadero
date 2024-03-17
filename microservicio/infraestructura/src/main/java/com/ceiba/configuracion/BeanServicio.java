package com.ceiba.configuracion;


import com.ceiba.areaestacionamiento.puerto.RepositorioAreaEstacionamiento;
import com.ceiba.areaestacionamiento.servicio.ServicioCrearAreaEstacionamiento;
import com.ceiba.registroestacionamiento.puerto.repositorio.RepositorioRegistroEstacionamiento;
import com.ceiba.registroestacionamiento.servicio.ServicioConsultaRegistroEstacionamiento;
import com.ceiba.registroestacionamiento.servicio.ServicioRegistrarSalida;
import com.ceiba.registroestacionamiento.servicio.ServicioRegistroEntrada;
import com.ceiba.registroestacionamiento.servicio.festivos.ServicioCalculadoraParqueadero;
import com.ceiba.registroestacionamiento.servicio.festivos.ServicioCalculadoraParqueaderoImp;
import com.ceiba.tarifa.puerto.RepositorioTarifa;
import com.ceiba.vehiculo.puerto.RepositorioVehiculo;
import com.ceiba.vehiculo.servicio.ServicioCreacionVehiculo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanServicio {

    @Bean
    ServicioCalculadoraParqueadero servicioCalculadoraParqueadero() {
        return new ServicioCalculadoraParqueaderoImp();
    }

    @Bean
    ServicioCrearAreaEstacionamiento servicioCrearAreaEstacionamiento(RepositorioAreaEstacionamiento repositorioAreaEstacionamiento) {
        return new ServicioCrearAreaEstacionamiento(repositorioAreaEstacionamiento);
    }

    @Bean
    public ServicioRegistroEntrada servicioRegistroEntrada(RepositorioRegistroEstacionamiento repositorioRegistroEstacionamiento,
                                                           RepositorioAreaEstacionamiento repositorioAreaEstacionamiento) {
        return new ServicioRegistroEntrada(repositorioRegistroEstacionamiento, repositorioAreaEstacionamiento);
    }

    @Bean
    public ServicioRegistrarSalida servicioRegistroSalida(RepositorioRegistroEstacionamiento repositorioRegistroEstacionamiento,
                                                          ServicioCalculadoraParqueadero servicioCalculadoraParqueadero,
                                                          RepositorioTarifa repositorioTarifa,
                                                          RepositorioAreaEstacionamiento repositorioAreaEstacionamiento) {
        return new ServicioRegistrarSalida(repositorioRegistroEstacionamiento, servicioCalculadoraParqueadero, repositorioTarifa, repositorioAreaEstacionamiento);
    }

    @Bean
    public ServicioCreacionVehiculo servicioCreacionVehiculo(RepositorioVehiculo repositorioVehiculo) {
        return new ServicioCreacionVehiculo(repositorioVehiculo);
    }


    @Bean
    public ServicioConsultaRegistroEstacionamiento servicioConsultaRegistroEstacionamiento(RepositorioRegistroEstacionamiento repositorioRegistroEstacionamiento,
                                                                                           RepositorioTarifa repositorioTarifa, ServicioCalculadoraParqueadero servicioCalculadoraParqueadero) {

        return new ServicioConsultaRegistroEstacionamiento(repositorioRegistroEstacionamiento, servicioCalculadoraParqueadero, repositorioTarifa);
    }

}
