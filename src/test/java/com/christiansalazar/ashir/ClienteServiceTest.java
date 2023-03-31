package com.christiansalazar.ashir;
import com.christiansalazar.ashir.Excepciones.ApiRequestException;
import com.christiansalazar.ashir.Modelo.Cliente;
import com.christiansalazar.ashir.Repository.ClienteRepository;
import com.christiansalazar.ashir.Service.ClienteService;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {
    ClienteService clienteService;
    ClienteRepository clienteRepository;
    @Before
    public void setUp(){

        this.clienteRepository = mock(ClienteRepository.class);
        clienteService = new ClienteService(clienteRepository);

    }

    @Test(expected = ApiRequestException.class)
    public void añadirClienteConCedulaNula(){
        //Arrange

        Long cedula = null;
        String nombre = "Jorge";
        String apellido = "Bedoya";
        String direccion = "Carrera 22 N62# - 33";
        Integer edad = 56;
        String email = "jbedoya@mail.com";

        //Act

        Cliente cliente = new Cliente(cedula, nombre, apellido, direccion, edad, email);
        clienteService.agregarCliente(cliente);
    }

    @Test(expected = ApiRequestException.class)
    public void añadirClienteConNombreNulo(){
        //Arrange

        Long cedula = 1003513135L;
        String nombre = null;
        String apellido = "Bedoya";
        String direccion = "Carrera 22 N62# - 33";
        Integer edad = 56;
        String email = "jbedoya@mail.com";

        //Act

        Cliente cliente = new Cliente(cedula, nombre, apellido, direccion, edad, email);
        clienteService.agregarCliente(cliente);
    }

    @Test(expected = ApiRequestException.class)
    public void añadirClienteConApellidoNulo(){
        //Arrange
        Long cedula = 1003513135L;
        String nombre = null;
        String apellido = "Bedoya";
        String direccion = "Carrera 22 N62# - 33";
        Integer edad = 56;
        String email = "jbedoya@mail.com";

        //Act

        Cliente cliente = new Cliente(cedula, nombre, apellido, direccion, edad, email);
        clienteService.agregarCliente(cliente);

    }
    @Test
    public void añadirCliente(){
        //Arrange

        Long cedula = 1003513135L;
        String nombre = "Jorge";
        String apellido = "bedoya";
        String direccion = "Carrera 22 N62# - 33";
        Integer edad = 56;
        String email = "jbedoya@mail.com";

        //Act

        Cliente cliente = new Cliente(cedula, nombre, apellido, direccion, edad, email);
        clienteService.agregarCliente(cliente);

        //Assert
        verify(clienteRepository, times(1)).save(any());
    }
}
