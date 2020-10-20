package com.proyecto.proyecto.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.proyecto.proyecto.model.Student;
import com.proyecto.proyecto.repository.StudentRepository;

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

public class StudentController {

    @Autowired
    StudentRepository studentrepository;

    @PostMapping("/students")
    public ResponseEntity <Student> createStudent(@RequestBody Student student){
        try {
            Student _student = studentrepository.save(new Student(student.getNombre(),student.getApellido(), student.getAsistio()));
            return new ResponseEntity<>(_student, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudent(@RequestParam(required = false) String nombre){
        try {
            List<Student> students = new ArrayList <Student>();
            if (nombre == null){
                studentrepository.findAll().forEach(students::add);
            } else {
                studentrepository.findByNombreContaining(nombre).forEach(students::add);
            }

            if(students.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") String id){
        Optional<Student> studentData = studentrepository.findById(id);
        if(studentData.isPresent()){
            return new ResponseEntity<>(studentData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") String id, @RequestBody Student student){
        Optional<Student> studentData = studentrepository.findById(id);
        if(studentData.isPresent()){
            Student _student = studentData.get();
            _student.setNombre(student.getNombre());
            _student.setApellido(student.getApellido());
            _student.setAsistio(student.getAsistio());
            return new ResponseEntity<>(studentrepository.save(_student), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("id") String id){
        try {
            studentrepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @DeleteMapping("/students")
    public ResponseEntity<HttpStatus> deleteAllStudents(){
        try {
            studentrepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
