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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServletTest {

    @InjectMocks
    private StudentServlet studentServlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private StudentService studentService;

    @Test
    void doGetTest() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        when(studentService.getStudentById(1)).thenReturn(new StudentDto());
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
        studentServlet.doGet(request, response);
        verify(response).setContentType("application/json");
        verify(studentService, times(1)).getStudentById(Long.valueOf(request.getParameter("id")));
    }

    @Test
    void doPostTest() throws ServletException, IOException {
        String name = "Vasya";
        int age = 25;
        long groupId = 1L;
        when(request.getParameter("name")).thenReturn(name);
        when(request.getParameter("age")).thenReturn(String.valueOf(age));
        when(request.getParameter("groupId")).thenReturn(String.valueOf(groupId));
        StudentDto studentDto = new StudentDto();
        studentDto.setName(name);
        studentDto.setAge(age);
        studentDto.setGroupId(groupId);
        studentServlet.doPost(request, response);
        verify(studentService, times(1)).saveStudent(studentDto);
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    void doPutTest() throws ServletException, IOException {
        String name = "Vasya";
        int age = 25;
        long groupId = 1L;
        long id = 1L;
        when(request.getParameter("id")).thenReturn(String.valueOf(id));
        when(request.getParameter("name")).thenReturn(name);
        when(request.getParameter("age")).thenReturn(String.valueOf(age));
        when(request.getParameter("groupId")).thenReturn(String.valueOf(groupId));
        StudentDto studentDto = new StudentDto();
        studentDto.setName(name);
        studentDto.setAge(age);
        studentDto.setGroupId(groupId);
        studentDto.setId(id);
        studentServlet.doPut(request, response);
        verify(studentService, times(1)).updateStudent(studentDto);
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void doDelete() throws ServletException, IOException {
        Long id = 1L;
        when(request.getParameter("id")).thenReturn(String.valueOf(id));
        studentServlet.doDelete(request, response);
        verify(studentService, times(1)).deleteStudent(id);
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}
