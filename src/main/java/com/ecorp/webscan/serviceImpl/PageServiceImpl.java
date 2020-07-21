package com.ecorp.webscan.serviceImpl;


import com.ecorp.webscan.domain.Page;
import com.ecorp.webscan.repo.PageRepository;
import com.ecorp.webscan.service.PageService;


public class PageServiceImpl implements PageService {
	
	private PageRepository roleRepository;

	public PageServiceImpl(PageRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public Page findByUrl(String url) {
		
		return roleRepository.findByUrl(url);
	}

	@Override
	public void save(Page page) {
		roleRepository.save(page);
	}


}
