package com.nagarro.controllers;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.nagarro.requests.LoginRequest;
import com.nagarro.utils.*;
import com.nagarro.utils.enums.Config;
import com.nagarro.utils.enums.Endpoint;
import com.nagarro.utils.enums.RequestType;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.nagarro.utils.Common.getConfig;

public class LoginController {
	private Configuration commonConfig = getConfig(Config.COMMON);
	private Configuration credentialsConfig = getConfig(Config.CREDS);

	public String login(String username, String password) {
		String authCode = null;
		String authUrl = commonConfig.getString("AUTH_URL")+"?client_id="+
				credentialsConfig.getString("CLIENT_ID")+"&redirect_uri="+
				credentialsConfig.getString("REDIRECT_URI")+"&state="+credentialsConfig.getString("STATE");
		Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
		WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER);
		String codeSeprator = "&code=";
		try {
			codeSeprator =
					"state="+ URLEncoder.encode(credentialsConfig.getString("STATE"),"UTF-8")+ codeSeprator;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			final HtmlPage page = webClient.getPage(authUrl);
			HtmlForm loginForm = page.getForms().get(0);
			HtmlEmailInput formUsername = loginForm.getInputByName("email");
			formUsername.setText(username);
			HtmlPasswordInput formPassword = loginForm.getInputByName("password");
			formPassword.setText(password);
			HtmlSubmitInput submit = loginForm.getFirstByXPath(".//input[@type='submit']");
			if (submit != null)
				submit.click();
		} catch (FailingHttpStatusCodeException expectedFailCode) {
			authCode = StringUtils.substringAfterLast(expectedFailCode.getMessage(),codeSeprator);
		} catch (Exception e) {
			Log.error("LoginController","Login",e.getMessage());
		}
		return authCode;
	}

	public String getAccessToken() {
		String authCode = login(
				credentialsConfig.getString("USERNAME"),
				credentialsConfig.getString("PASSWORD"));
		Api api = new Api();
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setClient_id(credentialsConfig.getString("CLIENT_ID"));
		loginRequest.setClient_secret(credentialsConfig.getString("CLIENT_SECRET"));
		loginRequest.setCode(authCode);
		api.apiUrl = Common.generateURL(Endpoint.AUTH_ACCESS_TOKEN,true);
		api.headers.put("content-type","application/json");
		api.headers.put("custom","");
		api.jsonBody = JsonHandler.toJson(loginRequest);
		api.sendNetworkRequest(RequestType.POST);
		return api.jsonResponse.then().extract().path("access_token");
	}
}
