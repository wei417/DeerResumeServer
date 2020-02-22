package vip.liuw.resume.api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.liuw.resume.entity.Resume;
import vip.liuw.resume.framework.response.Res;
import vip.liuw.resume.repository.ResumeRepository;

@RestController
@RequestMapping("/resume")
public class ResumeAPI {
    @Autowired
    private ResumeRepository resumeRepository;

    /**
     * 使用domain查询，无则返回默认的
     * 如果有且设置了阅读密码，则验证密码
     *
     * @param domain
     * @param vpass  查询密码
     * @return
     */
    @GetMapping
    public Resume show(@RequestParam String domain, @RequestParam(required = false) String vpass) {
        Resume data = resumeRepository.getResumeByDomain(domain);
        if (data != null) {
            if ((StringUtils.isBlank(data.getViewPassword()) || data.getViewPassword().equals(vpass))) {
                data.setShow(1);
            } else {
                data = new Resume();
                data.setShow(0);
            }
        } else {
            data = resumeRepository.getResumeByDomain("default");
            data.setShow(1);
        }
        return data;
    }

    /**
     * 新增/更新
     * 需要验证管理密码
     *
     * @param resume
     * @return
     */
    @PostMapping
    public Res save(@RequestBody Resume resume) {
        Resume data = resumeRepository.getResumeByDomain(resume.getDomain());
        if (data != null) {
            if (data.getAdminPassword().equals(resume.getAdminPassword())) {
                data.setTitle(resume.getTitle());
                data.setSubTitle(resume.getSubTitle());
                data.setContent(resume.getContent());
                data.setAdminPassword(resume.getAdminPassword());
                data.setViewPassword(resume.getViewPassword());
            } else {
                return Res.error("管理密码错误");
            }
        } else {
            data = resume;
        }
        if (StringUtils.isBlank(data.getAdminPassword()) || data.getAdminPassword().length() < 4) {
            return Res.error("管理密码不能低于4位数");
        }
        resumeRepository.save(data);
        return Res.ok("保存成功");
    }
}
