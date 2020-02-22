package vip.liuw.resume.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vip.liuw.resume.entity.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Resume getResumeByDomain(String domain);

}