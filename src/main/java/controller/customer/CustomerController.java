package controller.customer;

import config.DatabaseManagerConnector;
import config.PropertiesConfig;
import entities.dto.CustomerDto;
import repository.CustomerRepository;
import service.CustomerService;
import service.converter.CustomerConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@WebServlet(urlPatterns = "/customers")
public class CustomerController extends HttpServlet {
    private CustomerService service;
    private static final String CUSTOMER = "/WEB-INF/jsp/customer/";
    private static final String DELETE_URL = CUSTOMER + "deleteCustomerForm.jsp";
    private static final String CREATE_URL = CUSTOMER + "createCustomerForm.jsp";
    private static final String UPDATE_URL = CUSTOMER + "updateCustomerForm.jsp";
    private static final String FIND_URL = CUSTOMER + "findCustomer.jsp";

    @Override
    public void init() {
        String dbPassword = System.getenv("dbPassword");
        String dbUsername = System.getenv("dbUsername");
        PropertiesConfig propertiesConfig = new PropertiesConfig();
        Properties properties = propertiesConfig.loadProperties("application.properties");
        DatabaseManagerConnector connector = new DatabaseManagerConnector(properties, dbUsername, dbPassword);
        CustomerRepository customerRepository = new CustomerRepository(connector);
        CustomerConverter customerConverter = new CustomerConverter();
        service = new CustomerService(customerRepository, customerConverter);
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
        try {
            service.read(id);
            service.delete(id);
            req.setAttribute("message", String.format("Customer with id %d successfully deleted!", id));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void update(HttpServletRequest req) {
        CustomerDto dto = new CustomerDto();
        dto.setId(Integer.parseInt(req.getParameter("id")));
        dto.setName(req.getParameter("name"));
        dto.setEmail(req.getParameter("email"));
        try {
            service.read(dto.getId());
            dto = service.update(dto.getId(), dto);
            req.setAttribute("message",
                    String.format("Customer with id %d successfully updated", dto.getId()));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void create(HttpServletRequest req) {
        CustomerDto dto = new CustomerDto();
        dto.setName(req.getParameter("name"));
        dto.setEmail(req.getParameter("email"));
        try {
            dto = service.create(dto);
            req.setAttribute("message",
                    String.format("Customer %s created with id %d", dto.getName(), dto.getId()));
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findByName(HttpServletRequest req) {
        List<CustomerDto> dtoList;
        String name = req.getParameter("name");
        try {
            dtoList = service.read(name);
            req.setAttribute("customers", dtoList);
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }

    private void findById(HttpServletRequest req) {
        List<CustomerDto> dtoList;
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            dtoList = service.read(id);
            req.setAttribute("customers", dtoList);
        } catch (RuntimeException e) {
            req.setAttribute("message", e.getMessage());
        }
    }
}
