package com.christiansalazar.ashir.Service;

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

    public Cliente agregarCliente(Cliente cliente){
        if(cliente.getCedula() == null && cliente.getCedula() instanceof Long) throw new ApiRequestException("Invalid id");
        if(cliente.getNombre() == null) throw new ApiRequestException("null name not allowed");
        if(cliente.getApellido() == null) throw new ApiRequestException("null lastname not allowed");
        clienteRepository.save(cliente);
        return cliente;
    }
}
