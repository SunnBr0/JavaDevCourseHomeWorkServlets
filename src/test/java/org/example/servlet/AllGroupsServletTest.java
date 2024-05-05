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
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AllGroupsServletTest {

    @InjectMocks
    private AllGroupsServlet groupsServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private GroupService groupService;


    @Test
    void doGetTest() throws IOException, ServletException {
        List<GroupDto> groups = new ArrayList<>();
        groups.add(new GroupDto());
        when(groupService.getAllGroups()).thenReturn(groups);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
        groupsServlet.doGet(request, response);
        verify(response).setContentType("application/json");
        verify(groupService, times(1)).getAllGroups();
    }
}