package org.example.servlet;

import com.google.gson.Gson;
import org.example.dto.GroupDto;
import org.example.service.GroupService;
import org.example.service.impl.GroupsServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/group")
public class GroupServlet extends HttpServlet {
    private GroupService groupService;
    private static final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        groupService = new GroupsServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            long id = Long.parseLong(req.getParameter("id"));

            GroupDto group = groupService.getGroup(id);
            String json = gson.toJson(group);

            resp.setContentType("application/json");
            resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String faculty = req.getParameter("faculty");
        int numberOfStudents = Integer.parseInt(req.getParameter("numberOfStudents"));

        GroupDto group = new GroupDto();
        group.setFaculty(faculty);
        group.setNumberOfStudents(numberOfStudents);

        groupService.saveGroup(group);

        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        String faculty = req.getParameter("faculty");
        int numberOfStudents = Integer.parseInt(req.getParameter("numberOfStudents"));

        GroupDto group = new GroupDto();
        group.setId(id);
        group.setFaculty(faculty);
        group.setNumberOfStudents(numberOfStudents);

        groupService.updateGroup(group);

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));

        groupService.deleteGroup(id);

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
