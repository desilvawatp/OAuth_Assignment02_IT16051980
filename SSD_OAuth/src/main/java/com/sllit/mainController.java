package com.sllit;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class mainController {
	
	private final String mainPageName = "main.html";
	private final String indexPageName = "index.html";
	private final String redirectToMainPage = "redirect:/main.html";
	private final String redirectToIndexPage = "redirect:/";
	private final String RedirectFinalWithNotificationSuccess = "redirect:/main?notification=uploaded";

	@Autowired
	OAuthService OAuthService;

	@Autowired
	OAuthDriverService oAuthImpl;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String mainPage() throws IOException {

		if (OAuthService.userstatus().equals("SUCCESS")) {
			return redirectToMainPage;

		} else {
			return indexPageName;
		}

	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String dashboard() throws IOException {

		if (OAuthService.userstatus().equals("SUCCESS")) {
			return redirectToMainPage;

		} else {
			return indexPageName;
		}
	}

	@RequestMapping(value = "/Userlogin", method = RequestMethod.GET)
	public void googleLogin(HttpServletResponse response) throws IOException {
		response.sendRedirect(OAuthService.authenticateUser());
	}

	@RequestMapping(value = "/Userlogout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) throws IOException {
		OAuthService.discardUserSession(request);
		return redirectToIndexPage;
	}

	@RequestMapping(value = "/UserFileupload", method = RequestMethod.POST)
	public String upload(HttpServletRequest request, @ModelAttribute File file) throws Exception {

		MultipartFile multipartFile = file.getMultipartFile();
		oAuthImpl.uploadFile(multipartFile);
		return RedirectFinalWithNotificationSuccess;
	}

	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
	public String authorizationCode(@RequestParam(name = "code") String code) throws IOException {

		if (code != null) {
			OAuthService.tokenExchange(code);
			return mainPageName;
		} else {
			return indexPageName;
		}

	}

}
