package vip.liuw.resume.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.sql.Update;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(indexes = {@Index(name = "idx_domain", columnList = "domain", unique = true)})
public class Resume {

    @Id
    @Column(length = 20)
    @GeneratedValue
    @NotNull(groups = {Update.class})
    private Long id;

    @CreationTimestamp
    private Date createDate;

    @UpdateTimestamp
    private Date updateDate;

    /**
     * 标题
     */
    private String title;

    /**
     * 小标题
     */
    private String subTitle;

    /**
     * 简历内容
     */
    @Column(columnDefinition = "text")
    private String content;

    /**
     * 简历路径，当作唯一标识
     */
    private String domain;

    /**
     * 阅读密码
     */
    private String viewPassword;

    /**
     * 编辑密码，不能为空
     */
    private String adminPassword;

    /**
     * 控制页面是否显示简历 0否 1是
     */
    @Transient
    private Integer show;
}
