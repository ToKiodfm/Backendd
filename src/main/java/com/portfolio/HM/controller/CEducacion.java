/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.portfolio.HM.controller;

import com.portfolio.HM.Dto.DtoEducacion;
import com.portfolio.HM.Security.Controller.Mensaje;
import com.portfolio.HM.entity.Educacion;
import com.portfolio.HM.service.ServEducacion;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/educacion")
@CrossOrigin(origins = "https://proyectofinal-26481.web.app")
public class CEducacion {
    @Autowired
    ServEducacion servEducacion;
    
    @GetMapping("/lista")
    public ResponseEntity<List<Educacion>> list(){
        List <Educacion> list = servEducacion.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<Educacion> getById(@PathVariable("id") int id){
        if (servEducacion.existsById(id)){
            return new ResponseEntity(new Mensaje ("No existe el ID"), HttpStatus.BAD_REQUEST);
        }
        Educacion educacion = servEducacion.getOne(id).get();
        return new ResponseEntity(educacion, HttpStatus.OK);
    }
    
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        if(!servEducacion.existsById(id)){
            return new ResponseEntity(new Mensaje("No existe el ID"), HttpStatus.NOT_FOUND);
    }
    servEducacion.delete(id);
    return new ResponseEntity(new Mensaje ("Educacion eliminada"), HttpStatus.OK);
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody DtoEducacion dtoeducacion){
        if (StringUtils.isBlank(dtoeducacion.getNombreE())){
            return new ResponseEntity(new Mensaje("Nombre Obligatorio"),HttpStatus.BAD_REQUEST);
        } 
        if (servEducacion.existsByNombreE(dtoeducacion.getNombreE())){
            return new ResponseEntity(new Mensaje("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        }
        Educacion educacion = new Educacion(
        dtoeducacion.getNombreE(), dtoeducacion.getDescripcionE()
        );
        servEducacion.save(educacion);
       return new ResponseEntity(new Mensaje("Educacion creada"), HttpStatus.OK);
    
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update (@PathVariable("id") int id, @RequestBody DtoEducacion dtoeducacion){
        if(!servEducacion.existsById(id)){
            return new ResponseEntity(new Mensaje("No existe el ID"), HttpStatus.NOT_FOUND);
                    }
        if (servEducacion.existsByNombreE(dtoeducacion.getNombreE()) && servEducacion.getByNombreE(dtoeducacion.getNombreE()).get().getId() !=id){
        return new ResponseEntity(new Mensaje ("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
    }
    
    if (StringUtils.isBlank(dtoeducacion.getNombreE())){
        return new ResponseEntity(new Mensaje("Debes escribir algo"), HttpStatus.BAD_REQUEST);
    }
    Educacion educacion = servEducacion.getOne(id).get();
    educacion.setNombreE(dtoeducacion.getNombreE());
    educacion.setDescripcionE(dtoeducacion.getDescripcionE());
    
    servEducacion.save(educacion);
    return new ResponseEntity(new Mensaje ("Actualizado"), HttpStatus.OK);
   }
    }

