package edu.miu.cse.sms.service.impl;

import edu.miu.cse.sms.dto.request.StudentRequestDTO;
import edu.miu.cse.sms.dto.response.StudentResponseDTO;
import edu.miu.cse.sms.model.Student;
import edu.miu.cse.sms.repository.StudentRepository;
import edu.miu.cse.sms.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    @Override
    public List<StudentResponseDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponseDTO getStudentByRegisterNumber(String registerNumber) {
        Student student = studentRepository.findByRegisterNumber(registerNumber)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with register number " + registerNumber));
        return convertToResponseDTO(student);
    }

    @Override
    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO) {
        Student student = new Student();
        student.setName(studentRequestDTO.name());
        student.setRegisterNumber(studentRequestDTO.registerNumber());
        student.setEmail(studentRequestDTO.email());
        student.setPhone(studentRequestDTO.phone());
        Student savedStudent = studentRepository.save(student);
        return convertToResponseDTO(savedStudent);
    }

    private StudentResponseDTO convertToResponseDTO(Student student) {
        StudentResponseDTO studentResponseDTO = new StudentResponseDTO(student.getName(), student.getRegisterNumber(), student.getEmail(), student.getPhone());
        return studentResponseDTO;
    }
}
