package controller.company;

import config.DatabaseManagerConnector;
import config.PropertiesConfig;
import entities.dto.CompanyDto;
import repository.CompanyRepository;
import service.CompanyService;
import service.converter.CompanyConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@WebServlet(urlPatterns = "/companies")
public class CompanyController extends HttpServlet {
    private CompanyService service;
    private static final String COMPANY = "/WEB-INF/jsp/company/";
    private static final String DELETE_URL = COMPANY + "deleteCompanyForm.jsp";
    private static final String CREATE_URL = COMPANY + "createCompanyForm.jsp";
    private static final String UPDATE_URL = COMPANY + "updateCompanyForm.jsp";
    private static final String FIND_URL = COMPANY + "findCompany.jsp";

    @Override
    public void init() {
        String dbPassword = System.getenv("dbPassword");
        String dbUsername = System.getenv("dbUsername");
        PropertiesConfig propertiesConfig = new PropertiesConfig();
        Properties properties = propertiesConfig.loadProperties("application.properties");
        DatabaseManagerConnector connector = new DatabaseManagerConnector(properties, dbUsername, dbPassword);
        CompanyRepository companyRepository = new CompanyRepository(connector);
        CompanyConverter companyConverter = new CompanyConverter();
        service = new CompanyService(companyRepository, companyConverter);
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
        try{
            service.read(id);
            service.delete(id);
            req.setAttribute("message", String.format("Company with id %d successfully deleted!", id));
        } catch (RuntimeException e){
            req.setAttribute("message", e.getMessage());
        }
    }

    private void update(HttpServletRequest req) {
        CompanyDto dto = new CompanyDto();
        dto.setId(Integer.parseInt(req.getParameter("id")));
        dto.setName(req.getParameter("name"));
        dto.setLocation(req.getParameter("location"));
        try{
            service.read(dto.getId());
            dto = service.update(dto.getId(), dto);
            req.setAttribute("message",
                    String.format("Company with id %d successfully updated", dto.getId()));
        } catch (RuntimeException e){
            req.setAttribute("message", e.getMessage());
        }
    }

    private void create(HttpServletRequest req) {
        CompanyDto dto = new CompanyDto();
        dto.setName(req.getParameter("name"));
        dto.setLocation(req.getParameter("location"));
        try{
            dto = service.create(dto);
            req.setAttribute("message",
                    String.format("Company %s created with id %d", dto.getName(), dto.getId()));
        } catch (RuntimeException e){
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findByName(HttpServletRequest req) {
        List<CompanyDto> dtoList;
        String name = req.getParameter("name");
        try{
            dtoList = service.read(name);
            req.setAttribute("companies", dtoList);
        } catch (RuntimeException e){
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findById(HttpServletRequest req) {
        List<CompanyDto> dtoList;
        int id = Integer.parseInt(req.getParameter("id"));
        try{
            dtoList = service.read(id);
            req.setAttribute("companies", dtoList);
        } catch (RuntimeException e){
            req.setAttribute("message", e.getMessage());
        }
    }
}
