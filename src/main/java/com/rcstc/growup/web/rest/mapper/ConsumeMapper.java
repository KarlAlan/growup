package com.rcstc.growup.web.rest.mapper;

import com.rcstc.growup.domain.*;
import com.rcstc.growup.web.rest.dto.ConsumeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Consume and its DTO ConsumeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConsumeMapper {

    @Mapping(source = "applyer.id", target = "applyerId")
    @Mapping(source = "auditor.id", target = "auditorId")
    ConsumeDTO consumeToConsumeDTO(Consume consume);

    @Mapping(source = "applyerId", target = "applyer")
    @Mapping(source = "auditorId", target = "auditor")
    Consume consumeDTOToConsume(ConsumeDTO consumeDTO);

    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
