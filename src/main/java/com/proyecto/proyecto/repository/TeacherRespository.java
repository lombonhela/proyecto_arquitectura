package com.proyecto.proyecto.repository;

import java.util.List;

import com.proyecto.proyecto.model.Teacher;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeacherRespository extends MongoRepository <Teacher, String>
{
    List<Teacher> findByActivo(Boolean activo);
    List<Teacher> findByNombreContaining(String nombre);  
}
