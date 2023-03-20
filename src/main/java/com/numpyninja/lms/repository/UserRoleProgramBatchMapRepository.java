package com.numpyninja.lms.repository;

import com.numpyninja.lms.entity.UserRoleProgramBatchMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleProgramBatchMapRepository extends JpaRepository<UserRoleProgramBatchMap, Long> {

    Optional<UserRoleProgramBatchMap> findByUser_UserIdAndRoleRoleIdAndAndProgram_ProgramIdAndBatch_BatchId
            (String userId, String roleId, Long programId, Integer batchId);

    Optional<UserRoleProgramBatchMap> findByUser_UserIdAndRoleRoleIdAndUserRoleProgramBatchStatusEqualsIgnoreCase
            (String userId, String roleId, String status);

}