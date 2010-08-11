package flexcom.casehistory.mvc.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import flexcom.casehistory.ticket.dao.UserDAO;
import flexcom.casehistory.ticket.entity.User;

/**
 * @author philippe
 */
@Controller
public class LoginController extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value = "login")
	public void login(){}
	
	@RequestMapping(value = "login-failure")
	public void loginFailure(){}
	
	@RequestMapping(value = "denied")
	public void accessDenied(){}

	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		try{
			User user = userDAO.findByLogin(authentication.getName());
			userDAO.updateLastLogin(user, new Date());
		}
		catch (EmptyResultDataAccessException e) {}
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
