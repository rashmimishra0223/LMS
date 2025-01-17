package com.numpyninja.lms.repository;

import com.numpyninja.lms.entity.Batch;
import com.numpyninja.lms.entity.Program;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ProgBatchRepositoryTest {

	@Autowired
	private ProgBatchRepository progBatchRepository;

	@Autowired
	ProgramRepository programRepository;

	Program mockProgram;

	Batch mockBatch;

	@BeforeEach
	public void setUp() {
		setMockProgramBatchAndSave();
	}

	private void setMockProgramBatchAndSave() {
		mockProgram = new Program(2L, "DA", "DA Training",
				"Active", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
		programRepository.save(mockProgram);

		mockBatch = new Batch(1, "DA 01", "DA Batch 01", "Active", mockProgram,
				6, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
		progBatchRepository.save(mockBatch);
	}

	@DisplayName("JUnit test for get Batches by BatchName")
	@Test
	public void givenBatchName_WhenFindBatches_ReturnBatchObjects() {
		//given
		String batchName = "DA 01";

		// when
		List<Batch> batchList = progBatchRepository.findByBatchName(batchName);

		// then
		assertThat(batchList).isNotEmpty();
	}

	@DisplayName("JUnit test for get all Batches by Batch Name ignoring case")
	@Test
	public void givenBatchList_WhenGetAllBatchesByBatchName_ThenReturnBatchesList() {
		String batchName = "da 01";

		List<Batch> list = progBatchRepository.findByBatchNameContainingIgnoreCaseOrderByBatchIdAsc(batchName);

		assertThat(list).isNotEmpty();
	}

	@DisplayName("JUnit test for get Batches by ProgramId ")
	@Test
	//@Order(6)
	public void givenProgramId_WhenFindBatch_ReturnBatchObjects() {
		// given
		Long programId = 2L;

		// when
		List<Batch> batchList = progBatchRepository.findByProgramProgramId(programId);

		// then
		assertThat(batchList).isNotEmpty();
	}

	@DisplayName("test to get batch by Id, ProgramId and Status")
	@Test
	public void testFindBatchByBatchIdAndProgram_ProgramIdAndBatchStatusEqualsIgnoreCase() {

		Optional<Batch> optionalBatch = progBatchRepository
				.findBatchByBatchIdAndProgram_ProgramIdAndBatchStatusEqualsIgnoreCase(1, 2L, "active");

		assertThat(optionalBatch).isNotEqualTo(0);
	}
	
	
	@DisplayName("test to get batchName and Program by programId")
	@Test
	public void testFindByBatchNameAndProgram_ProgramId() {
		
		String batchName = "SDET";
		Long ProgramID = 2L;
		Batch optionalBatch = progBatchRepository
				.findByBatchNameAndProgram_ProgramId(batchName, ProgramID);
		assertThat(optionalBatch).isNotEqualTo(0);
		
	}
	
}
