package controller.skill;

import config.DatabaseManagerConnector;
import config.PropertiesConfig;
import entities.dto.DeveloperDto;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@WebServlet(urlPatterns = "/skills")
public class SkillController extends HttpServlet {
    private SkillService service;
    private static final String DELETE_URL = "/WEB-INF/jsp/skill/deleteSkillForm.jsp";
    private static final String CREATE_URL = "/WEB-INF/jsp/skill/createSkillForm.jsp";
    private static final String UPDATE_URL = "/WEB-INF/jsp/skill/updateSkillForm.jsp";
    private static final String FIND_URL = "/WEB-INF/jsp/skill/findSkill.jsp";
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
            req.setAttribute("message", "There is no skill by specified id");
        } else {
            try {
                service.delete(id);
                req.setAttribute("message", String.format("Skill with id %d successfully deleted!", id));
            } catch (SQLException e) {
                req.setAttribute("message", e.getMessage());
            }
        }
        req.getRequestDispatcher(DELETE_URL).forward(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SkillDto dto = new SkillDto();
        dto.setDepartment(req.getParameter("department"));
        dto.setLevel(req.getParameter("level"));
        if(service.read(dto.getId()).isNull()){
            req.setAttribute("message", "There is no skills by specified id");
        } else {
            dto = service.update(dto.getId(), dto);
            req.setAttribute("message",
                    String.format("Skill with id %d successfully updated", dto.getId()));
        }
        req.getRequestDispatcher(UPDATE_URL).forward(req, resp);
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SkillDto dto = new SkillDto();
        dto.setDepartment(req.getParameter("department"));
        dto.setLevel(req.getParameter("level"));
        dto = service.create(dto);
        if (!dto.isNull()) {
            req.setAttribute("message",
                    String.format("Skill %s %s created with id %d", dto.getDepartment(), dto.getLevel(), dto.getId()));
        } else {
            req.setAttribute("message", "Skill not created");
        }
        req.getRequestDispatcher(CREATE_URL).forward(req, resp);
    }

    private void findByName(HttpServletRequest req) {
        List<SkillDto> dtoList = new ArrayList<>();
        SkillDto dto;
        String name = req.getParameter("name");
        dto = service.read(name);
        if (dto.isNull()) {
            req.setAttribute("message", "There is no skills by specified name");
        } else {
            dtoList.add(dto);
            req.setAttribute("skills", dtoList);
        }
    }
    private void findById(HttpServletRequest req){
        List<SkillDto> dtoList = new ArrayList<>();
        SkillDto dto;
        int id = Integer.parseInt(req.getParameter("id"));
        dto = service.read(id);
        if (dto.isNull()) {
            req.setAttribute("message", "There is no skills by specified id");
        } else {
            dtoList.add(dto);
            req.setAttribute("skills", dtoList);
        }
    }
}
