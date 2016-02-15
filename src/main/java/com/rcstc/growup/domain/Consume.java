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
 * A Consume.
 */
@Entity
@Table(name = "consume")
@Document(indexName = "consume")
public class Consume implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "apply_date")
    private LocalDate applyDate;
    
    @Column(name = "apply_value")
    private Integer applyValue;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    
    @Column(name = "audit_date")
    private LocalDate auditDate;
    
    @Column(name = "audit_value")
    private Integer auditValue;
    
    @ManyToOne
    @JoinColumn(name = "applyer_id")
    private User applyer;

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

    public LocalDate getApplyDate() {
        return applyDate;
    }
    
    public void setApplyDate(LocalDate applyDate) {
        this.applyDate = applyDate;
    }

    public Integer getApplyValue() {
        return applyValue;
    }
    
    public void setApplyValue(Integer applyValue) {
        this.applyValue = applyValue;
    }

    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getAuditDate() {
        return auditDate;
    }
    
    public void setAuditDate(LocalDate auditDate) {
        this.auditDate = auditDate;
    }

    public Integer getAuditValue() {
        return auditValue;
    }
    
    public void setAuditValue(Integer auditValue) {
        this.auditValue = auditValue;
    }

    public User getApplyer() {
        return applyer;
    }

    public void setApplyer(User user) {
        this.applyer = user;
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
        Consume consume = (Consume) o;
        if(consume.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, consume.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Consume{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", applyDate='" + applyDate + "'" +
            ", applyValue='" + applyValue + "'" +
            ", status='" + status + "'" +
            ", auditDate='" + auditDate + "'" +
            ", auditValue='" + auditValue + "'" +
            '}';
    }
}
