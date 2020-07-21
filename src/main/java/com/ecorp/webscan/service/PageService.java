package com.ecorp.webscan.service;


import com.ecorp.webscan.domain.Page;

public interface PageService {
	
	Page findByUrl(String url);

	void save(Page page);
}
