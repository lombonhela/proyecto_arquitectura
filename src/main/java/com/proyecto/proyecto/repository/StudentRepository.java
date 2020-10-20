package com.proyecto.proyecto.repository;

import java.util.List;

import com.proyecto.proyecto.model.Student;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository <Student, String>
{
    List<Student> findByAsistio(Boolean attend);
    List<Student> findByNombreContaining(String nombre);    
}
