package controller.skill;

import config.DatabaseManagerConnector;
import config.PropertiesConfig;
import entities.dto.SkillDto;
import repository.SkillRepository;
import service.SkillService;
import service.converter.SkillConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@WebServlet(urlPatterns = "/skills")
public class SkillController extends HttpServlet {
    private SkillService service;
    private static final String SKILL = "/WEB-INF/jsp/skill/";
    private static final String DELETE_URL = SKILL + "deleteSkillForm.jsp";
    private static final String CREATE_URL = SKILL + "createSkillForm.jsp";
    private static final String UPDATE_URL = SKILL + "updateSkillForm.jsp";
    private static final String FIND_URL = SKILL + "findSkill.jsp";
    @Override
    public void init() {
        String dbPassword = System.getenv("dbPassword");
        String dbUsername = System.getenv("dbUsername");
        PropertiesConfig propertiesConfig = new PropertiesConfig();
        Properties properties = propertiesConfig.loadProperties("application.properties");
        DatabaseManagerConnector connector = new DatabaseManagerConnector(properties, dbUsername, dbPassword);
        SkillRepository repository = new SkillRepository(connector);
        SkillConverter converter = new SkillConverter();
        service = new SkillService(repository, converter);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getParameter("method")){
            case "find id":
                findById(req);
                req.getRequestDispatcher(FIND_URL).forward(req, resp);
                break;
            case "find department":
                findByDepartment(req);
                req.getRequestDispatcher(FIND_URL).forward(req, resp);
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
            req.setAttribute("message", String.format("Skill with id %d successfully deleted!", id));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void update(HttpServletRequest req) {
        SkillDto dto = new SkillDto();
        dto.setId(Integer.parseInt(req.getParameter("id")));
        dto.setDepartment(req.getParameter("department"));
        dto.setLevel(req.getParameter("level"));
        try {
            service.read(dto.getId());
            dto = service.update(dto.getId(), dto);
            req.setAttribute("message",
                    String.format("Skill with id %d successfully updated", dto.getId()));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void create(HttpServletRequest req) {
        SkillDto dto = new SkillDto();
        dto.setDepartment(req.getParameter("department"));
        dto.setLevel(req.getParameter("level"));
        try {
            dto = service.create(dto);
            req.setAttribute("message",
                    String.format("Skill %s %s created with id %d", dto.getDepartment(), dto.getLevel(), dto.getId()));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findByDepartment(HttpServletRequest req) {
        List<SkillDto> dtoList;
        String department = req.getParameter("department");
        try {
            dtoList = service.read(department);
            req.setAttribute("skills", dtoList);
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }
    private void findById(HttpServletRequest req){
        List<SkillDto> dtoList;
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            dtoList = service.read(id);
            req.setAttribute("skills", dtoList);
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }
}
