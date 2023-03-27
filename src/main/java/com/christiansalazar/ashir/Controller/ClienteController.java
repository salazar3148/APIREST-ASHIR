package com.christiansalazar.ashir.Controller;

import com.christiansalazar.ashir.Modelo.Cliente;
import com.christiansalazar.ashir.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class ClienteController {

    private ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("clientes")
    public ResponseEntity<Cliente> ingresarCliente(@RequestBody Cliente cliente){
        return new ResponseEntity<>(clienteService.agregarCliente(cliente), HttpStatus.ACCEPTED);
    }
}
