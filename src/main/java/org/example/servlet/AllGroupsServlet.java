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
import java.util.List;

@WebServlet("/groupList")
public class AllGroupsServlet extends HttpServlet {
    private GroupService groupService;
    private static Gson gson = new Gson();
    @Override
    public void init() throws ServletException {
        groupService = new GroupsServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            List<GroupDto> groups = groupService.getAllGroups();
            String json = gson.toJson(groups);
            resp.setContentType("application/json");
            resp.getWriter().write(json);
    }
}
