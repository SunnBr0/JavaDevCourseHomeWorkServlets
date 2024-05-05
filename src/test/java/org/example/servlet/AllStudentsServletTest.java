package org.example.servlet;

import org.example.dto.StudentDto;
import org.example.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AllStudentsServletTest {
    @InjectMocks
    private AllStudentsServlet studentsServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private StudentService studentService;


    @Test
    void doGetTest() throws ServletException, IOException {
        List<StudentDto> students = new ArrayList<>();
        students.add(new StudentDto());
        when(studentService.getAllStudents()).thenReturn(students);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
        studentsServlet.doGet(request, response);
        verify(response).setContentType("application/json");
        verify(studentService, times(1)).getAllStudents();
    }
}