package com.rcstc.growup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rcstc.growup.domain.Consume;
import com.rcstc.growup.repository.ConsumeRepository;
import com.rcstc.growup.repository.search.ConsumeSearchRepository;
import com.rcstc.growup.web.rest.util.HeaderUtil;
import com.rcstc.growup.web.rest.util.PaginationUtil;
import com.rcstc.growup.web.rest.dto.ConsumeDTO;
import com.rcstc.growup.web.rest.mapper.ConsumeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Consume.
 */
@RestController
@RequestMapping("/api")
public class ConsumeResource {

    private final Logger log = LoggerFactory.getLogger(ConsumeResource.class);
        
    @Inject
    private ConsumeRepository consumeRepository;
    
    @Inject
    private ConsumeMapper consumeMapper;
    
    @Inject
    private ConsumeSearchRepository consumeSearchRepository;
    
    /**
     * POST  /consumes -> Create a new consume.
     */
    @RequestMapping(value = "/consumes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ConsumeDTO> createConsume(@Valid @RequestBody ConsumeDTO consumeDTO) throws URISyntaxException {
        log.debug("REST request to save Consume : {}", consumeDTO);
        if (consumeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("consume", "idexists", "A new consume cannot already have an ID")).body(null);
        }
        Consume consume = consumeMapper.consumeDTOToConsume(consumeDTO);
        consume = consumeRepository.save(consume);
        ConsumeDTO result = consumeMapper.consumeToConsumeDTO(consume);
        consumeSearchRepository.save(consume);
        return ResponseEntity.created(new URI("/api/consumes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("consume", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /consumes -> Updates an existing consume.
     */
    @RequestMapping(value = "/consumes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ConsumeDTO> updateConsume(@Valid @RequestBody ConsumeDTO consumeDTO) throws URISyntaxException {
        log.debug("REST request to update Consume : {}", consumeDTO);
        if (consumeDTO.getId() == null) {
            return createConsume(consumeDTO);
        }
        Consume consume = consumeMapper.consumeDTOToConsume(consumeDTO);
        consume = consumeRepository.save(consume);
        ConsumeDTO result = consumeMapper.consumeToConsumeDTO(consume);
        consumeSearchRepository.save(consume);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("consume", consumeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /consumes -> get all the consumes.
     */
    @RequestMapping(value = "/consumes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ConsumeDTO>> getAllConsumes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Consumes");
        Page<Consume> page = consumeRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/consumes");
        return new ResponseEntity<>(page.getContent().stream()
            .map(consumeMapper::consumeToConsumeDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /consumes/:id -> get the "id" consume.
     */
    @RequestMapping(value = "/consumes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ConsumeDTO> getConsume(@PathVariable Long id) {
        log.debug("REST request to get Consume : {}", id);
        Consume consume = consumeRepository.findOne(id);
        ConsumeDTO consumeDTO = consumeMapper.consumeToConsumeDTO(consume);
        return Optional.ofNullable(consumeDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /consumes/:id -> delete the "id" consume.
     */
    @RequestMapping(value = "/consumes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteConsume(@PathVariable Long id) {
        log.debug("REST request to delete Consume : {}", id);
        consumeRepository.delete(id);
        consumeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("consume", id.toString())).build();
    }

    /**
     * SEARCH  /_search/consumes/:query -> search for the consume corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/consumes/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ConsumeDTO> searchConsumes(@PathVariable String query) {
        log.debug("REST request to search Consumes for query {}", query);
        return StreamSupport
            .stream(consumeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(consumeMapper::consumeToConsumeDTO)
            .collect(Collectors.toList());
    }
}
