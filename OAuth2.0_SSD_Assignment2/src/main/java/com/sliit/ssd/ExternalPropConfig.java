package com.sliit.ssd;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ExternalPropConfig {
	
	@Value("${google.credentials}")
	private Resource credentialsPath;

	@Value("${redirection.uri}")
	private String redirectionURI;

	@Value("${api.secret.key}")
	private Resource secretKeyFilePath;

	@Value("${app.path.temp}")
	private String appPath;

	public String getAppPath() {
		return appPath;
	}

	public Resource getCredentialsPath() {
		return credentialsPath;
	}

	public void setCredentialsPath(Resource credentialsPath) {
		this.credentialsPath = credentialsPath;
	}

	public Resource getSecretKeyFilePath() {
		return secretKeyFilePath;
	}

	public void setSecretKeyFilePath(Resource secretKeyFilePath) {
		this.secretKeyFilePath = secretKeyFilePath;
	}

	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}

	public String getRedirectionURI() {
		return redirectionURI;
	}

	public void setRedirectionURI(String redirectionURI) {
		this.redirectionURI = redirectionURI;
	}
}
