package org.example.servlet;

import com.google.gson.Gson;
import org.example.dto.StudentDto;
import org.example.service.StudentService;
import org.example.service.impl.StudentServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {

    private StudentService studentService;
    private final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        studentService = new StudentServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        StudentDto student = studentService.getStudentById(id);
        String json = gson.toJson(student);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        long groupId = Long.parseLong(request.getParameter("groupId"));

        StudentDto studentDto = new StudentDto();
        studentDto.setName(name);
        studentDto.setAge(age);
        studentDto.setGroupId(groupId);

        studentService.saveStudent(studentDto);

        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        Integer age = Integer.parseInt(request.getParameter("age"));
        Long groupId = Long.parseLong(request.getParameter("groupId"));

        StudentDto studentDto = new StudentDto();
        studentDto.setId(id);
        studentDto.setName(name);
        studentDto.setAge(age);
        studentDto.setGroupId(groupId);

        studentService.updateStudent(studentDto);

        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        studentService.deleteStudent(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
