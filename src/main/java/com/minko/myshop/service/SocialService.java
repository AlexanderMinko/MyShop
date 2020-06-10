package com.minko.myshop.service;

import com.minko.myshop.model.SocialAccount;

public interface SocialService {
	
	String getAutorizeUrl();
	
	SocialAccount getSocialAccount(String authToken);
}
