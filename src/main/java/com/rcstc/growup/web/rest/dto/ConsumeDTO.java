package com.rcstc.growup.web.rest.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.rcstc.growup.domain.enumeration.Status;

/**
 * A DTO for the Consume entity.
 */
public class ConsumeDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;


    private String description;


    private LocalDate applyDate;


    private Integer applyValue;


    private Status status;


    private LocalDate auditDate;


    private Integer auditValue;


    private Long applyerId;
    private Long auditorId;
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

    public Long getApplyerId() {
        return applyerId;
    }

    public void setApplyerId(Long userId) {
        this.applyerId = userId;
    }
    public Long getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Long userId) {
        this.auditorId = userId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConsumeDTO consumeDTO = (ConsumeDTO) o;

        if ( ! Objects.equals(id, consumeDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ConsumeDTO{" +
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
