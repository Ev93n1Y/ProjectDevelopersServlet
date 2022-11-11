package controller.developer;

import config.DatabaseManagerConnector;
import config.PropertiesConfig;
import entities.dto.CustomerDto;
import entities.dto.DeveloperDto;
import entities.dto.ProjectDto;
import repository.DeveloperRepository;
import service.DeveloperService;
import service.converter.DeveloperConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@WebServlet(urlPatterns = "/developers")
public class DeveloperController extends HttpServlet {
    private DeveloperService service;
    private static final String DELETE_URL = "/WEB-INF/jsp/developer/deleteDeveloperForm.jsp";
    private static final String CREATE_URL = "/WEB-INF/jsp/developer/createDeveloperForm.jsp";
    private static final String UPDATE_URL = "/WEB-INF/jsp/developer/updateDeveloperForm.jsp";
    private static final String FIND_URL = "/WEB-INF/jsp/developer/findDeveloper.jsp";

    @Override
    public void init() {
        String dbPassword = System.getenv("dbPassword");
        String dbUsername = System.getenv("dbUsername");
        PropertiesConfig propertiesConfig = new PropertiesConfig();
        Properties properties = propertiesConfig.loadProperties("application.properties");
        DatabaseManagerConnector connector = new DatabaseManagerConnector(properties, dbUsername, dbPassword);
        DeveloperRepository repository = new DeveloperRepository(connector);
        DeveloperConverter converter = new DeveloperConverter();
        service = new DeveloperService(repository, converter);
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
            case "find project devs":
                findAllDevelopersByProject(req);
                break;
            case "find java devs":
                findAllJavaDevelopers(req);
                break;
            case "find middle devs":
                findAllMiddleDevelopers(req);
                break;
            case "salary":
                totalDevelopersSalaryAtProject(req);
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
            req.setAttribute("message", "There is not developer by specified id");
        } else {
            try {
                service.delete(id);
                req.setAttribute("message", String.format("Developer with id %d successfully deleted!", id));
            } catch (SQLException e) {
                req.setAttribute("message", e.getMessage());
            }
        }
        req.getRequestDispatcher(DELETE_URL).forward(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DeveloperDto dto = new DeveloperDto();
        dto.setName(req.getParameter("name"));
        dto.setAge(Integer.parseInt(req.getParameter("age")));
        dto.setGender(req.getParameter("gender"));
        dto.setSalary(Integer.parseInt(req.getParameter("salary")));
        if(service.read(dto.getId()).isNull()){
            req.setAttribute("message", "There is not developers by specified id");
        } else {
            dto = service.update(dto.getId(), dto);
            req.setAttribute("message",
                    String.format("Developer with id %d successfully updated", dto.getId()));
        }
        req.getRequestDispatcher(UPDATE_URL).forward(req, resp);
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DeveloperDto dto = new DeveloperDto();
        dto.setName(req.getParameter("name"));
        dto.setAge(Integer.parseInt(req.getParameter("age")));
        dto.setGender(req.getParameter("gender"));
        dto.setSalary(Integer.parseInt(req.getParameter("salary")));
        dto = service.create(dto);
        if (!dto.isNull()) {
            req.setAttribute("message",
                    String.format("Developer %s created with id %d", dto.getName(), dto.getId()));
        } else {
            req.setAttribute("message", "Developer not created");
        }
        req.getRequestDispatcher(CREATE_URL).forward(req, resp);
    }

    private void findByName(HttpServletRequest req) {
        List<DeveloperDto> dtoList = new ArrayList<>();
        DeveloperDto dto;
        String name = req.getParameter("name");
        dto = service.read(name);
        if (dto.isNull()) {
            req.setAttribute("message", "There is no developers by specified name");
        } else {
            dtoList.add(dto);
            req.setAttribute("developers", dtoList);
        }
    }

    private void findById(HttpServletRequest req){
        List<DeveloperDto> dtoList = new ArrayList<>();
        DeveloperDto dto;
        int id = Integer.parseInt(req.getParameter("id"));
        dto = service.read(id);
        if (dto.isNull()) {
            req.setAttribute("message", "There is no developers by specified id");
        } else {
            dtoList.add(dto);
            req.setAttribute("developers", dtoList);
        }
    }

    private void findAllDevelopersByProject(HttpServletRequest req){
        List<DeveloperDto> dtoList = new ArrayList<>();
        DeveloperDto dto;
        int id = Integer.parseInt(req.getParameter("id"));
        dto = service.read(id);
        if (dto.isNull()) {
            req.setAttribute("message", "There is no developers by specified id");
        } else {
            dtoList.add(dto);
            req.setAttribute("developers", dtoList);
        }
    }

    private void findAllJavaDevelopers(HttpServletRequest req){
        List<DeveloperDto> dtoList = new ArrayList<>();
        DeveloperDto dto = null;// = service.allJavaDevelopers();
        if (dto.isNull()) {
            req.setAttribute("message", "There is no Java developers");
        } else {
            dtoList.add(dto);
            req.setAttribute("developers", dtoList);
        }
    }

    private void findAllMiddleDevelopers(HttpServletRequest req){
        List<DeveloperDto> dtoList = new ArrayList<>();
        DeveloperDto dto = null;// = service.allMiddleDevelopers();
        if (dto.isNull()) {
            req.setAttribute("message", "There is no middle developers");
        } else {
            dtoList.add(dto);
            req.setAttribute("developers", dtoList);
        }
    }

    private void findAllProjects(HttpServletRequest req){
        List<ProjectDto> dtoList = new ArrayList<>();
        ProjectDto dto = null;// = service.a();
        if (dto.isNull()) {
            req.setAttribute("message", "There is no projects");
        } else {
            dtoList.add(dto);
            req.setAttribute("projects", dtoList);
        }
    }

    private void totalDevelopersSalaryAtProject(HttpServletRequest req){
        int totalSalary = service.totalDevelopersSalaryByProject(Integer.parseInt(req.getParameter("id")));
        req.setAttribute("message", totalSalary);
    }
}
