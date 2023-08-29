package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Student;

import com.example.demo.repository.StudentRepository;


@RestController
public class StudentController {

	@Autowired
	StudentRepository studentRepository;

	
	@GetMapping("/student")
	public ResponseEntity<Object> getStudent() {

		try {
			List<Student> students = studentRepository.findAll();

			return new ResponseEntity<>(students, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/student")
	public ResponseEntity<Object> addS(@RequestBody Student body) {

		try {
			//Optional<Student> role = studentRepository.findById(4);

			Student student = studentRepository.save(body);

			return new ResponseEntity<>(student, HttpStatus.CREATED);

		} catch (Exception e) {

			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/student/{studentId}")
	public ResponseEntity<Object> getStudentDetail(@PathVariable Integer studentId) {

		try {

			Optional<Student> student = studentRepository.findById(studentId);

			if (student.isPresent()) {

				return new ResponseEntity<>(student, HttpStatus.OK);
			} else {

				return new ResponseEntity<>("student not found", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {

			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/student/{studentId}")
	public ResponseEntity<Object> updateStudent(@PathVariable Integer studentId, @RequestBody Student body) {

		try {

			Optional<Student> student = studentRepository.findById(studentId);

			if (student.isPresent()) {
				student.get().setStudentId(body.getStudentId());
				student.get().setName(body.getName());
				student.get().setEmail(body.getEmail());
		

				studentRepository.save(student.get());

				return new ResponseEntity<>(student, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("student not found", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {

			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/student/{studentId}")
	public ResponseEntity<Object> deleteStudent(@PathVariable Integer studentId) {

		try {

			Optional<Student> student = studentRepository.findById(studentId);

			if (student.isPresent()) {
				studentRepository.delete(student.get());

				return new ResponseEntity<>("Delete successful", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("student not found", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {

			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
