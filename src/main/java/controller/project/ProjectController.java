package controller.project;

import config.DatabaseManagerConnector;
import config.PropertiesConfig;
import entities.dto.DeveloperDto;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@WebServlet(urlPatterns = "/projects")
public class ProjectController extends HttpServlet {
    private ProjectService service;
    private static final String DELETE_URL = "/WEB-INF/jsp/project/deleteProjectForm.jsp";
    private static final String CREATE_URL = "/WEB-INF/jsp/project/createProjectForm.jsp";
    private static final String UPDATE_URL = "/WEB-INF/jsp/project/updateProjectForm.jsp";
    private static final String FIND_URL = "/WEB-INF/jsp/project/findProject.jsp";

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
        switch (req.getParameter("method")){
            case "find id":
                findById(req);
                break;
            case "find name":
                findByName(req);
                break;
        }
        req.getRequestDispatcher(FIND_URL).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getParameter("method")) {
            case "create":
                create(req, resp);
                break;
            case "update":
                update(req, resp);
                break;
            case "delete":
                doDelete(req, resp);
                break;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
        if(service.read(id).isNull()){
            req.setAttribute("message", "There is no projects by specified id");
        } else {
            try {
                service.delete(id);
                req.setAttribute("message", String.format("Project with id %d successfully deleted!", id));
            } catch (SQLException e) {
                req.setAttribute("message", e.getMessage());
            }
        }
        req.getRequestDispatcher(DELETE_URL).forward(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProjectDto dto = new ProjectDto();
        dto.setName(req.getParameter("name"));
        dto.setCompany_id(Integer.parseInt(req.getParameter("company_id")));
        dto.setCustomer_id(Integer.parseInt(req.getParameter("customer_id")));
        dto.setCost(Integer.parseInt(req.getParameter("cost")));
        dto.setCreation_date(Date.valueOf(req.getParameter("creation_date")));
        if(service.read(dto.getId()).isNull()){
            req.setAttribute("message", "There is no projects by specified id");
        } else {
            dto = service.update(dto.getId(), dto);
            req.setAttribute("message",
                    String.format("Project with id %d successfully updated", dto.getId()));
        }
        req.getRequestDispatcher(UPDATE_URL).forward(req, resp);
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProjectDto dto = new ProjectDto();
        dto.setName(req.getParameter("name"));
        dto.setCompany_id(Integer.parseInt(req.getParameter("company_id")));
        dto.setCustomer_id(Integer.parseInt(req.getParameter("customer_id")));
        dto.setCost(Integer.parseInt(req.getParameter("cost")));
        dto.setCreation_date(Date.valueOf(req.getParameter("creation_date")));
        dto = service.create(dto);
        if (!dto.isNull()) {
            req.setAttribute("message",
                    String.format("Project %s created with id %d", dto.getName(), dto.getId()));
        } else {
            req.setAttribute("message", "Project not created");
        }
        req.getRequestDispatcher(CREATE_URL).forward(req, resp);
    }

    private void findByName(HttpServletRequest req) {
        List<ProjectDto> dtoList = new ArrayList<>();
        ProjectDto dto;
        String name = req.getParameter("name");
        dto = service.read(name);
        if (dto.isNull()) {
            req.setAttribute("message", "There is no projects by specified name");
        } else {
            dtoList.add(dto);
            req.setAttribute("projects", dtoList);
        }
    }
    private void findById(HttpServletRequest req){
        List<ProjectDto> dtoList = new ArrayList<>();
        ProjectDto dto;
        int id = Integer.parseInt(req.getParameter("id"));
        dto = service.read(id);
        if (dto.isNull()) {
            req.setAttribute("message", "There is no projects by specified id");
        } else {
            dtoList.add(dto);
            req.setAttribute("projects", dtoList);
        }
    }
}
