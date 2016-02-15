package com.rcstc.growup.domain;

import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.rcstc.growup.domain.enumeration.Status;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
@Document(indexName = "task")
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotNull
    @Size(max = 2000)
    @Column(name = "description", length = 2000, nullable = false)
    private String description;
    
    @Column(name = "declare_value")
    private Integer declareValue;
    
    @Column(name = "audit_value")
    private Integer auditValue;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;
    
    @Column(name = "declare_date")
    private LocalDate declareDate;
    
    @Column(name = "audit_date")
    private LocalDate auditDate;
    
    @ManyToOne
    @JoinColumn(name = "contributor_id")
    private User contributor;

    @ManyToOne
    @JoinColumn(name = "auditor_id")
    private User auditor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDeclareValue() {
        return declareValue;
    }
    
    public void setDeclareValue(Integer declareValue) {
        this.declareValue = declareValue;
    }

    public Integer getAuditValue() {
        return auditValue;
    }
    
    public void setAuditValue(Integer auditValue) {
        this.auditValue = auditValue;
    }

    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDeclareDate() {
        return declareDate;
    }
    
    public void setDeclareDate(LocalDate declareDate) {
        this.declareDate = declareDate;
    }

    public LocalDate getAuditDate() {
        return auditDate;
    }
    
    public void setAuditDate(LocalDate auditDate) {
        this.auditDate = auditDate;
    }

    public User getContributor() {
        return contributor;
    }

    public void setContributor(User user) {
        this.contributor = user;
    }

    public User getAuditor() {
        return auditor;
    }

    public void setAuditor(User user) {
        this.auditor = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        if(task.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", declareValue='" + declareValue + "'" +
            ", auditValue='" + auditValue + "'" +
            ", status='" + status + "'" +
            ", declareDate='" + declareDate + "'" +
            ", auditDate='" + auditDate + "'" +
            '}';
    }
}
