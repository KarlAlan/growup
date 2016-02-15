package com.rcstc.growup.web.rest;

import com.rcstc.growup.Application;
import com.rcstc.growup.domain.Consume;
import com.rcstc.growup.repository.ConsumeRepository;
import com.rcstc.growup.repository.search.ConsumeSearchRepository;
import com.rcstc.growup.web.rest.dto.ConsumeDTO;
import com.rcstc.growup.web.rest.mapper.ConsumeMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rcstc.growup.domain.enumeration.Status;

/**
 * Test class for the ConsumeResource REST controller.
 *
 * @see ConsumeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ConsumeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final LocalDate DEFAULT_APPLY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPLY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_APPLY_VALUE = 1;
    private static final Integer UPDATED_APPLY_VALUE = 2;
    
    private static final Status DEFAULT_STATUS = Status.draft;
    private static final Status UPDATED_STATUS = Status.confirmed;

    private static final LocalDate DEFAULT_AUDIT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AUDIT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_AUDIT_VALUE = 1;
    private static final Integer UPDATED_AUDIT_VALUE = 2;

    @Inject
    private ConsumeRepository consumeRepository;

    @Inject
    private ConsumeMapper consumeMapper;

    @Inject
    private ConsumeSearchRepository consumeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restConsumeMockMvc;

    private Consume consume;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConsumeResource consumeResource = new ConsumeResource();
        ReflectionTestUtils.setField(consumeResource, "consumeSearchRepository", consumeSearchRepository);
        ReflectionTestUtils.setField(consumeResource, "consumeRepository", consumeRepository);
        ReflectionTestUtils.setField(consumeResource, "consumeMapper", consumeMapper);
        this.restConsumeMockMvc = MockMvcBuilders.standaloneSetup(consumeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        consume = new Consume();
        consume.setName(DEFAULT_NAME);
        consume.setDescription(DEFAULT_DESCRIPTION);
        consume.setApplyDate(DEFAULT_APPLY_DATE);
        consume.setApplyValue(DEFAULT_APPLY_VALUE);
        consume.setStatus(DEFAULT_STATUS);
        consume.setAuditDate(DEFAULT_AUDIT_DATE);
        consume.setAuditValue(DEFAULT_AUDIT_VALUE);
    }

    @Test
    @Transactional
    public void createConsume() throws Exception {
        int databaseSizeBeforeCreate = consumeRepository.findAll().size();

        // Create the Consume
        ConsumeDTO consumeDTO = consumeMapper.consumeToConsumeDTO(consume);

        restConsumeMockMvc.perform(post("/api/consumes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(consumeDTO)))
                .andExpect(status().isCreated());

        // Validate the Consume in the database
        List<Consume> consumes = consumeRepository.findAll();
        assertThat(consumes).hasSize(databaseSizeBeforeCreate + 1);
        Consume testConsume = consumes.get(consumes.size() - 1);
        assertThat(testConsume.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testConsume.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testConsume.getApplyDate()).isEqualTo(DEFAULT_APPLY_DATE);
        assertThat(testConsume.getApplyValue()).isEqualTo(DEFAULT_APPLY_VALUE);
        assertThat(testConsume.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testConsume.getAuditDate()).isEqualTo(DEFAULT_AUDIT_DATE);
        assertThat(testConsume.getAuditValue()).isEqualTo(DEFAULT_AUDIT_VALUE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = consumeRepository.findAll().size();
        // set the field null
        consume.setName(null);

        // Create the Consume, which fails.
        ConsumeDTO consumeDTO = consumeMapper.consumeToConsumeDTO(consume);

        restConsumeMockMvc.perform(post("/api/consumes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(consumeDTO)))
                .andExpect(status().isBadRequest());

        List<Consume> consumes = consumeRepository.findAll();
        assertThat(consumes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConsumes() throws Exception {
        // Initialize the database
        consumeRepository.saveAndFlush(consume);

        // Get all the consumes
        restConsumeMockMvc.perform(get("/api/consumes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(consume.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].applyDate").value(hasItem(DEFAULT_APPLY_DATE.toString())))
                .andExpect(jsonPath("$.[*].applyValue").value(hasItem(DEFAULT_APPLY_VALUE)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].auditDate").value(hasItem(DEFAULT_AUDIT_DATE.toString())))
                .andExpect(jsonPath("$.[*].auditValue").value(hasItem(DEFAULT_AUDIT_VALUE)));
    }

    @Test
    @Transactional
    public void getConsume() throws Exception {
        // Initialize the database
        consumeRepository.saveAndFlush(consume);

        // Get the consume
        restConsumeMockMvc.perform(get("/api/consumes/{id}", consume.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(consume.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.applyDate").value(DEFAULT_APPLY_DATE.toString()))
            .andExpect(jsonPath("$.applyValue").value(DEFAULT_APPLY_VALUE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.auditDate").value(DEFAULT_AUDIT_DATE.toString()))
            .andExpect(jsonPath("$.auditValue").value(DEFAULT_AUDIT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingConsume() throws Exception {
        // Get the consume
        restConsumeMockMvc.perform(get("/api/consumes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsume() throws Exception {
        // Initialize the database
        consumeRepository.saveAndFlush(consume);

		int databaseSizeBeforeUpdate = consumeRepository.findAll().size();

        // Update the consume
        consume.setName(UPDATED_NAME);
        consume.setDescription(UPDATED_DESCRIPTION);
        consume.setApplyDate(UPDATED_APPLY_DATE);
        consume.setApplyValue(UPDATED_APPLY_VALUE);
        consume.setStatus(UPDATED_STATUS);
        consume.setAuditDate(UPDATED_AUDIT_DATE);
        consume.setAuditValue(UPDATED_AUDIT_VALUE);
        ConsumeDTO consumeDTO = consumeMapper.consumeToConsumeDTO(consume);

        restConsumeMockMvc.perform(put("/api/consumes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(consumeDTO)))
                .andExpect(status().isOk());

        // Validate the Consume in the database
        List<Consume> consumes = consumeRepository.findAll();
        assertThat(consumes).hasSize(databaseSizeBeforeUpdate);
        Consume testConsume = consumes.get(consumes.size() - 1);
        assertThat(testConsume.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConsume.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConsume.getApplyDate()).isEqualTo(UPDATED_APPLY_DATE);
        assertThat(testConsume.getApplyValue()).isEqualTo(UPDATED_APPLY_VALUE);
        assertThat(testConsume.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testConsume.getAuditDate()).isEqualTo(UPDATED_AUDIT_DATE);
        assertThat(testConsume.getAuditValue()).isEqualTo(UPDATED_AUDIT_VALUE);
    }

    @Test
    @Transactional
    public void deleteConsume() throws Exception {
        // Initialize the database
        consumeRepository.saveAndFlush(consume);

		int databaseSizeBeforeDelete = consumeRepository.findAll().size();

        // Get the consume
        restConsumeMockMvc.perform(delete("/api/consumes/{id}", consume.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Consume> consumes = consumeRepository.findAll();
        assertThat(consumes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
