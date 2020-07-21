package com.ecorp.webscan.repo;

import com.ecorp.webscan.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Repository
@Transactional
public interface PageRepository extends JpaRepository<Page, UUID> {

	Page findByUrl(String url);

}
