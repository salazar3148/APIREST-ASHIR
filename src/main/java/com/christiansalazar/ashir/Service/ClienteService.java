package com.christiansalazar.ashir.Service;
import com.christiansalazar.ashir.DTO.ClienteDTO;
import com.christiansalazar.ashir.Excepciones.ApiRequestException;
import com.christiansalazar.ashir.Modelo.Cliente;
import com.christiansalazar.ashir.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteDTO agregarCliente(ClienteDTO clienteDTO){
        if(clienteDTO.getCedula() == null) throw new ApiRequestException("Invalid id");
        if(clienteDTO.getNombre() == null) throw new ApiRequestException("null name not allowed");
        if(clienteDTO.getApellido() == null) throw new ApiRequestException("null lastname not allowed");
        Cliente cliente = new Cliente(
                clienteDTO.getCedula(),
                clienteDTO.getNombre(),
                clienteDTO.getApellido(),
                clienteDTO.getDireccion(),
                clienteDTO.getEdad(),
                clienteDTO.getEmail()
        );
        clienteRepository.save(cliente);
        return clienteDTO;
    }

}