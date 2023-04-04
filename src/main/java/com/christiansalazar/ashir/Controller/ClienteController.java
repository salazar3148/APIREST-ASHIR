package com.christiansalazar.ashir.Controller;

import com.christiansalazar.ashir.DTO.ClienteDTO;
import com.christiansalazar.ashir.Modelo.Cliente;
import com.christiansalazar.ashir.Service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@Api(value = "Controlador Clientes", tags = {"Endpoints Clientes"})
public class ClienteController {
    private ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @ApiResponses(value={
            @ApiResponse(code = 201, message = "Usuario guardado correctamente"),
            @ApiResponse(code = 400, message = "Parametros invalidos"),
            @ApiResponse(code = 500, message = "Ocurrió un error en el servidor")
    })
    @ApiOperation(value = "Este endpoint permite ingresar un nuevo cliente al sistema",
            notes = "Validaciones:\n1. Cedula no nula\n2. nombre no nulo\n3. Apellido no nulo\n- A continuacion se muestra como debe ser el cuerpo de la petición:",
            response = Cliente.class)
    @PostMapping("clientes")
    public ResponseEntity<ClienteDTO> ingresarCliente(@RequestBody ClienteDTO cliente){
        return new ResponseEntity<>(clienteService.agregarCliente(cliente), HttpStatus.CREATED);
    }
}
