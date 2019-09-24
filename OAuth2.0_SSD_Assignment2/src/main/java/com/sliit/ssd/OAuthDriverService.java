package com.sliit.ssd;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

@Service
public class OAuthDriverService {

	private Logger logger = LoggerFactory.getLogger(OAuthDriverService.class);

	HttpTransport htth = new NetHttpTransport();
	JsonFactory json = JacksonFactory.getDefaultInstance();

	List<String> scopeList = Collections.singletonList(DriveScopes.DRIVE);

	private Drive drive;

	@Autowired
	OAuthService oauthService;

	@Autowired
	ExternalPropConfig extPropConfig;

	@PostConstruct
	public void driverConfig() throws Exception {

		Credential credential = oauthService.credentials();
		drive = new Drive.Builder(htth, json, (HttpRequestInitializer) credential).setApplicationName(Constant.APP_NAME)
				.build();

	}

	public void uploadFile(MultipartFile multipartFile) throws Exception {

		String path = extPropConfig.getAppPath();
		String fileName = multipartFile.getOriginalFilename();
		String contentType = multipartFile.getContentType();

		java.io.File transferedFile = new java.io.File(path, fileName);
		multipartFile.transferTo(transferedFile);

		saveToDriver(fileName, contentType, transferedFile);

		logger.info("File Updloading -> completed");

	}

	private void saveToDriver(String fileName, String contentType, java.io.File transferedFile) throws IOException {
		
		File fileMetadata = new File();
		fileMetadata.setName(fileName);

		FileContent fileContent = new FileContent(contentType, transferedFile);
		drive.files().create(fileMetadata, fileContent).setFields("id").execute();
	}

}
