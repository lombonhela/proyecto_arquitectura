package com.proyecto.proyecto.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.proyecto.proyecto.model.Teacher;
import com.proyecto.proyecto.repository.TeacherRespository;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TeacherController {
    

    @Autowired
    TeacherRespository teacherrepository;

    @PostMapping("/teachers")
    public ResponseEntity <Teacher> createStudent(@RequestBody Teacher teacher){
        try {
            Teacher _teacher = teacherrepository.save(new Teacher(teacher.getNombre(),teacher.getApellido(), teacher.getActivo()));
            return new ResponseEntity<>(_teacher, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/teachers")
    public ResponseEntity<List<Teacher>> getAllTeacher(@RequestParam(required = false) String nombre){
        try {
            List<Teacher> teachers = new ArrayList <Teacher>();
            if (nombre == null){
                teacherrepository.findAll().forEach(teachers::add);
            } else {
                teacherrepository.findByNombreContaining(nombre).forEach(teachers::add);
            }

            if(teachers.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(teachers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<?> getTeacherById(@PathVariable("id") String id){
        Optional<Teacher> teacherData = teacherrepository.findById(id);
        if(teacherData.isPresent()){
            return new ResponseEntity<>(teacherData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/teachers/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable("id") String id, @RequestBody Teacher teacher){
        Optional<Teacher> teacherData = teacherrepository.findById(id);
        if(teacherData.isPresent()){
            Teacher _teacher = teacherData.get();
            _teacher.setNombre(teacher.getNombre());
            _teacher.setApellido(teacher.getApellido());
            _teacher.setActivo(teacher.getActivo());
            return new ResponseEntity<>(teacherrepository.save(_teacher), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("teachers/{id}")
    public ResponseEntity<HttpStatus> deleteTeacher(@PathVariable("id") String id){
        try {
            teacherrepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @DeleteMapping("/teachers")
    public ResponseEntity<HttpStatus> deleteAllTeachers(){
        try {
            teacherrepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
