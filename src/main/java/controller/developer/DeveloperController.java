package controller.developer;

import config.DatabaseManagerConnector;
import config.PropertiesConfig;
import entities.dto.DeveloperDto;
import repository.DeveloperRepository;
import service.DeveloperService;
import service.converter.DeveloperConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@WebServlet(urlPatterns = "/developers")
public class DeveloperController extends HttpServlet {
    private DeveloperService service;
    private static final String DEVELOPER = "/WEB-INF/jsp/developer/";
    private static final String DELETE_URL = DEVELOPER + "deleteDeveloperForm.jsp";
    private static final String CREATE_URL = DEVELOPER + "createDeveloperForm.jsp";
    private static final String UPDATE_URL = DEVELOPER + "updateDeveloperForm.jsp";
    private static final String FIND_URL = DEVELOPER + "findDeveloper.jsp";
    private static final String PROJECT_TOTAL_SALARY_URL = DEVELOPER + "totalProjectDevelopersSalary.jsp";
    private static final String PROJECT_DEVELOPERS_URL = DEVELOPER + "findDevelopersOnProject.jsp";
    private static final String JAVA_DEVELOPERS_URL = DEVELOPER + "findJavaDevelopers.jsp";
    private static final String MIDDLE_DEVELOPERS_URL = DEVELOPER + "findMiddleDevelopers.jsp";

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
        switch (req.getParameter("method")) {
            case "find id":
                findById(req);
                req.getRequestDispatcher(FIND_URL).forward(req, resp);
                break;
            case "find name":
                findByName(req);
                req.getRequestDispatcher(FIND_URL).forward(req, resp);
                break;
            case "find devs by project id":
                findDevelopersOnProject(req);
                req.getRequestDispatcher(PROJECT_DEVELOPERS_URL).forward(req, resp);
                break;
            case "Find java devs":
                findAllJavaDevelopers(req);
                req.getRequestDispatcher(JAVA_DEVELOPERS_URL).forward(req, resp);
                break;
            case "Find middle devs":
                findAllMiddleDevelopers(req);
                req.getRequestDispatcher(MIDDLE_DEVELOPERS_URL).forward(req, resp);
                break;
            case "salary":
                totalDevelopersSalaryAtProject(req);
                req.getRequestDispatcher(PROJECT_TOTAL_SALARY_URL).forward(req, resp);
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
            req.setAttribute("message", String.format("Developer with id %d successfully deleted!", id));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void update(HttpServletRequest req) {
        DeveloperDto dto = new DeveloperDto();
        dto.setId(Integer.parseInt(req.getParameter("id")));
        dto.setName(req.getParameter("name"));
        dto.setAge(Integer.parseInt(req.getParameter("age")));
        dto.setGender(req.getParameter("gender"));
        dto.setSalary(Integer.parseInt(req.getParameter("salary")));
        try {
            service.read(dto.getId());
            dto = service.update(dto.getId(), dto);
            req.setAttribute("message",
                    String.format("Developer with id %d successfully updated", dto.getId()));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void create(HttpServletRequest req) {
        DeveloperDto dto = new DeveloperDto();
        dto.setName(req.getParameter("name"));
        dto.setAge(Integer.parseInt(req.getParameter("age")));
        dto.setGender(req.getParameter("gender"));
        dto.setSalary(Integer.parseInt(req.getParameter("salary")));
        try {
            dto = service.create(dto);
            req.setAttribute("message",
                    String.format("Developer %s created with id %d", dto.getName(), dto.getId()));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findByName(HttpServletRequest req) {
        List<DeveloperDto> dtoList;
        String name = req.getParameter("name");
        try {
            dtoList = service.read(name);
            req.setAttribute("developers", dtoList);
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findById(HttpServletRequest req) {
        List<DeveloperDto> dtoList;
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            dtoList = service.read(id);
            req.setAttribute("developers", dtoList);
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findDevelopersOnProject(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        List<DeveloperDto> dtoList = service.allDevelopersByProject(id);
        if (dtoList.isEmpty()) {
            req.setAttribute("message", "There is no developers by specified project id");
        } else {
            req.setAttribute("developers", dtoList);
        }
    }

    private void findAllJavaDevelopers(HttpServletRequest req) {
        List<DeveloperDto> dtoList = service.allJavaDevelopers();
        if (dtoList.isEmpty()) {
            req.setAttribute("message", "There is no Java developers");
        } else {
            req.setAttribute("developers", dtoList);
        }
    }

    private void findAllMiddleDevelopers(HttpServletRequest req) {
        List<DeveloperDto> dtoList = service.allMiddleDevelopers();
        if (dtoList.isEmpty()) {
            req.setAttribute("message", "There is no middle developers");
        } else {
            req.setAttribute("developers", dtoList);
        }
    }

    private void totalDevelopersSalaryAtProject(HttpServletRequest req) {
        int totalSalary = service.totalDevelopersSalaryByProject(Integer.parseInt(req.getParameter("id")));
        req.setAttribute("id", req.getParameter("id"));
        req.setAttribute("salary", totalSalary);
    }
}
