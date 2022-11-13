package controller.project;

import config.DatabaseManagerConnector;
import config.PropertiesConfig;
import entities.dto.ProjectDto;
import repository.ProjectRepository;
import service.ProjectService;
import service.converter.ProjectConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@WebServlet(urlPatterns = "/projects")
public class ProjectController extends HttpServlet {
    private ProjectService service;
    private static final String PROJECT = "/WEB-INF/jsp/project/";
    private static final String DELETE_URL = PROJECT + "deleteProjectForm.jsp";
    private static final String CREATE_URL = PROJECT + "createProjectForm.jsp";
    private static final String UPDATE_URL = PROJECT + "updateProjectForm.jsp";
    private static final String FIND_URL = PROJECT + "findProject.jsp";
    private static final String FIND_ALL_URL = PROJECT + "findAllProjects.jsp";

    @Override
    public void init() {
        String dbPassword = System.getenv("dbPassword");
        String dbUsername = System.getenv("dbUsername");
        PropertiesConfig propertiesConfig = new PropertiesConfig();
        Properties properties = propertiesConfig.loadProperties("application.properties");
        DatabaseManagerConnector connector = new DatabaseManagerConnector(properties, dbUsername, dbPassword);
        ProjectRepository repository = new ProjectRepository(connector);
        ProjectConverter converter = new ProjectConverter();
        service = new ProjectService(repository, converter);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getParameter("method")) {
            case "find id":
                findById(req);
                req.getRequestDispatcher(FIND_URL).forward(req, resp);
                break;
            case "find name":
                findByName(req);
                req.getRequestDispatcher(FIND_URL).forward(req, resp);
                break;
            case "Find all projects":
                findAllProjects(req);
                req.getRequestDispatcher(FIND_ALL_URL).forward(req, resp);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getParameter("method")) {
            case "create":
                create(req);
                req.getRequestDispatcher(CREATE_URL).forward(req, resp);
                break;
            case "update":
                update(req);
                req.getRequestDispatcher(UPDATE_URL).forward(req, resp);
                break;
            case "delete":
                doDelete(req, resp);
                req.getRequestDispatcher(DELETE_URL).forward(req, resp);
                break;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = Integer.valueOf(req.getParameter("id"));
        try {
            service.read(id);
            service.delete(id);
            req.setAttribute("message", String.format("Project with id %d successfully deleted!", id));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void update(HttpServletRequest req) {
        ProjectDto dto = new ProjectDto();
        dto.setId(Integer.parseInt(req.getParameter("id")));
        dto.setName(req.getParameter("name"));
        dto.setCompany_id(Integer.parseInt(req.getParameter("company id")));
        dto.setCustomer_id(Integer.parseInt(req.getParameter("customer id")));
        dto.setCost(Integer.parseInt(req.getParameter("cost")));
        dto.setCreation_date(Date.valueOf(req.getParameter("creation date")));
        try {
            service.read(dto.getId());
            dto = service.update(dto.getId(), dto);
            req.setAttribute("message",
                    String.format("Project with id %d successfully updated", dto.getId()));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void create(HttpServletRequest req) {
        ProjectDto dto = new ProjectDto();
        dto.setName(req.getParameter("name"));
        dto.setCompany_id(Integer.parseInt(req.getParameter("company id")));
        dto.setCustomer_id(Integer.parseInt(req.getParameter("customer id")));
        dto.setCost(Integer.parseInt(req.getParameter("cost")));
        dto.setCreation_date(Date.valueOf(req.getParameter("creation date")));
        try {
            dto = service.create(dto);
            req.setAttribute("message",
                    String.format("Project %s created with id %d", dto.getName(), dto.getId()));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findByName(HttpServletRequest req) {
        List<ProjectDto> dtoList;
        String name = req.getParameter("name");
        try {
            dtoList = service.read(name);
            req.setAttribute("projects", dtoList);
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findById(HttpServletRequest req) {
        List<ProjectDto> dtoList;
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            dtoList = service.read(id);
            req.setAttribute("projects", dtoList);
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findAllProjects(HttpServletRequest req) {
        List<ProjectDto> dtoList = service.allProjects();
        if (dtoList.isEmpty()) {
            req.setAttribute("message", "There is no projects");
        } else {
            req.setAttribute("projects", dtoList);
        }
    }
}
