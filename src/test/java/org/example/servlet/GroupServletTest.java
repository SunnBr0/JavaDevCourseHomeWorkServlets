package org.example.servlet;

import org.example.dto.GroupDto;
import org.example.service.GroupService;
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
class GroupServletTest {
    @InjectMocks
    private GroupServlet groupServlet;

    @Mock
    private GroupService groupService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;


    @Test
    void doGetTest() throws ServletException, IOException {
        Long id = 1L;
        when(request.getParameter("id")).thenReturn(String.valueOf(id));
        when(groupService.getGroup(id)).thenReturn(new GroupDto());
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
        groupServlet.doGet(request, response);
        verify(response).setContentType("application/json");
        verify(groupService, times(1)).getGroup(id);
    }

    @Test
    void doPostTest() throws ServletException, IOException {
        String faculty = "faculty";
        int numberOfStudents = 10;
        when(request.getParameter("faculty")).thenReturn(faculty);
        when(request.getParameter("numberOfStudents")).thenReturn(String.valueOf(numberOfStudents));
        GroupDto group = new GroupDto();
        group.setFaculty(faculty);
        group.setNumberOfStudents(numberOfStudents);
        groupServlet.doPost(request, response);
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        verify(groupService, times(1)).saveGroup(group);
    }

    @Test
    void doPutTest() throws ServletException, IOException {
        String faculty = "faculty";
        int numberOfStudents = 10;
        long id = 1L;
        when(request.getParameter("faculty")).thenReturn(faculty);
        when(request.getParameter("numberOfStudents")).thenReturn(String.valueOf(numberOfStudents));
        when(request.getParameter("id")).thenReturn(String.valueOf(id));
        GroupDto group = new GroupDto();
        group.setFaculty(faculty);
        group.setNumberOfStudents(numberOfStudents);
        group.setId(id);
        groupServlet.doPut(request, response);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(groupService, times(1)).updateGroup(group);
    }

    @Test
    void doDeleteTest() throws ServletException, IOException {
        long id = 1L;
        when(request.getParameter("id")).thenReturn(String.valueOf(id));
        groupServlet.doDelete(request, response);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(groupService, times(1)).deleteGroup(id);
    }
}