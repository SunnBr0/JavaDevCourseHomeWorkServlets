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
import java.util.List;

@WebServlet("/studentList")
public class AllStudentsServlet extends HttpServlet {
    private StudentService studentService;
    private static Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        studentService = new StudentServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<StudentDto> students = studentService.getAllStudents();
        String json = gson.toJson(students);

        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }
}
