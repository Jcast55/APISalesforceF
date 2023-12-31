package com.web.app.Controller;

import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.web.app.Model.Informe;
import com.web.app.Service.InformeService;

@RestController
@RequestMapping("/informes")
public class InformeController {
    private final InformeService informeService;

    @Autowired
    public InformeController(InformeService informeService) {
        this.informeService = informeService;
    }

    @GetMapping
    public ResponseEntity<List<Informe>> getAllInformes() {
        List<Informe> informes = informeService.getAllInformes();
        return new ResponseEntity<>(informes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Informe> getInformeById(@PathVariable Long id) {
        Informe informe = informeService.getInformeById(id);
        if (informe != null) {
            return new ResponseEntity<>(informe, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Informe> createInforme(@RequestBody Informe informe) {
        Informe createdInforme = informeService.createInforme(informe);
        return new ResponseEntity<>(createdInforme, HttpStatus.CREATED);
    }
      @PostMapping(path = "/API", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public ResponseEntity<Informe> createInforme(
            @RequestParam("idInforme1") String idInforme1,
            @RequestParam("idContact") String idContact,
            @RequestParam("horasTrabajadas") double horasTrabajadas,
            @RequestParam("horaInicio") String horaInicio,
            @RequestParam("horaFinalizado") String horaFinalizado,
            @RequestParam("idInforme") double idInforme) {

        // Formato de fecha esperado
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        // Convierte las cadenas de fecha a objetos Date
        Date fechaInicio = null;
        Date fechaFinalizado = null;
        try {
            fechaInicio = dateFormat.parse(horaInicio);
            fechaFinalizado = dateFormat.parse(horaFinalizado);
        } catch (ParseException e) {
            e.printStackTrace(); // Maneja la excepción según tus necesidades
        }

        // Aquí puedes utilizar los parámetros recibidos para crear tu objeto Informe
        Informe informe = new Informe();
        informe.setIdInforme((long) idInforme);
        informe.setIdInforme1(idInforme1);
        informe.setIdContact(idContact);
        informe.setHorasTrabajadas(horasTrabajadas);
        informe.setHoraInicio(fechaInicio);
        informe.setHoraFinalizado(fechaFinalizado);

        // Luego, llama a tu servicio para crear el informe
        Informe createdInforme = informeService.createInforme(informe);

        // Devuelve la respuesta adecuada
        return new ResponseEntity<>(createdInforme, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Informe> updateInforme(@PathVariable Long id, @RequestBody Informe informe) {
        Informe updatedInforme = informeService.updateInforme(id, informe);
        if (updatedInforme != null) {
            return new ResponseEntity<>(updatedInforme, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInforme(@PathVariable Long id) {
        informeService.deleteInforme(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/info/{idInforme1}")
    public ResponseEntity<Informe> getInformeByIdInforme1(@PathVariable String idInforme1,
            @RequestParam(required = false) String idInforme) {
        String id = idInforme != null ? idInforme : idInforme1;
        Informe informe = informeService.getInformesByIdInforme1(id);
        if (informe != null) {
            return new ResponseEntity<>(informe, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
