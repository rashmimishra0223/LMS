package com.numpyninja.lms.controller;

import com.numpyninja.lms.config.ApiResponse;
import com.numpyninja.lms.dto.AssignmentSubmitDTO;
import com.numpyninja.lms.entity.AssignmentSubmit;
import com.numpyninja.lms.services.AssignmentSubmitService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/assignmentsubmission")
@Api(tags="Assignment Submission Controller", description="Assignment Submission CRUD Operations")
public class AssignmentSubmitController {

    private AssignmentSubmitService assignmentSubmitService;

    public AssignmentSubmitController(AssignmentSubmitService assignmentSubmitService) {
        this.assignmentSubmitService = assignmentSubmitService;
    }

    @GetMapping("/student/{userID}")
    @ApiOperation("Get Submissions by User ID")
    public ResponseEntity<List<AssignmentSubmitDTO>> getSubmissionsByUserID(@PathVariable String userID) {
        System.out.println(userID);
        List<AssignmentSubmitDTO> submissionsListDTO = assignmentSubmitService.getSubmissionsByUserID(userID);
        return ResponseEntity.ok(submissionsListDTO);
    }

    @PostMapping(path="", consumes="application/json", produces="application/json")
    @ApiOperation("Create New Submission")
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<AssignmentSubmitDTO> submitAssignment( @RequestBody AssignmentSubmitDTO assignmentSubmitDTO)
    {
        AssignmentSubmitDTO createdAssignSubmitDTO = assignmentSubmitService.submitAssignment(assignmentSubmitDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAssignSubmitDTO);
    }
    
   @GetMapping("/studentbatch/{batchid}")
   @ApiOperation("Get Submissions By Batch")
   public ResponseEntity<List<AssignmentSubmitDTO>> getSubmissionsByBatch(@PathVariable Integer batchid) {
      
   	List<AssignmentSubmitDTO> submissionsListDTO = assignmentSubmitService.getSubmissionsByBatch(batchid);
       return ResponseEntity.ok(submissionsListDTO);
   }
   
    @GetMapping("")
    @ApiOperation("Get All Submissions")
    public ResponseEntity<List<AssignmentSubmitDTO>> getAllSubmissions() {
       
    	List<AssignmentSubmitDTO> submissionsListDTO = assignmentSubmitService.getAllSubmissions();
        return ResponseEntity.ok(submissionsListDTO);
    }


    @PutMapping(path="/{id}", consumes="application/json", produces="application/json")
    @ApiOperation("Update details of Submission")
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<AssignmentSubmitDTO> resubmitAssignment( @RequestBody AssignmentSubmitDTO assignmentSubmitDTO,
                                                                  @PathVariable Long id)
    {
        AssignmentSubmitDTO updatedAssignSubmitDTO = assignmentSubmitService.resubmitAssignment(assignmentSubmitDTO,id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedAssignSubmitDTO);
    }

    @DeleteMapping(path="/{id}")
    @ApiOperation("Delete Submission")
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<ApiResponse> deleteSubmission(@PathVariable Long id)
    {
        assignmentSubmitService.deleteSubmissions(id);
        return new ResponseEntity(new ApiResponse("Submission deleted successfully", true),HttpStatus.OK);
    }
    
    @GetMapping("/getGrades/{assignmentId}")
    @ApiOperation("Get Grades by Assignment Id")
    public ResponseEntity<List<AssignmentSubmitDTO>> getGradesByAssignmentId(@PathVariable Long assignmentId)
    {
        List<AssignmentSubmitDTO> getListGradesAssignmentDTO = assignmentSubmitService.getGradesByAssinmentId((assignmentId));
        return ResponseEntity.ok(getListGradesAssignmentDTO);
    }
    
    @GetMapping("/grades/student/{studentId}")
    @ApiOperation("Get Grades by Student Id")
    public ResponseEntity<List<AssignmentSubmitDTO>> getGradesByStudentId(@PathVariable String studentId){
    	 List<AssignmentSubmitDTO> getListGradesAssignmentByStudentIdDTO = assignmentSubmitService.getGradesByStudentId(studentId);
         return ResponseEntity.ok(getListGradesAssignmentByStudentIdDTO);
    }

    @PutMapping(path="/gradesubmission/{submissionId}",consumes="application/json", produces="application/json" )
    @ApiOperation("Grade Assignment Submission")
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<AssignmentSubmitDTO> gradeAssignmentSubmission(@RequestBody AssignmentSubmitDTO assignmentSubmitDTO,
                                                                     @PathVariable Long submissionId){
        AssignmentSubmitDTO gradedSubmissionDTO = assignmentSubmitService.gradeAssignmentSubmission(assignmentSubmitDTO,submissionId);
        return ResponseEntity.status(HttpStatus.OK).body(gradedSubmissionDTO);
    }
    @GetMapping("/grades/{batchId}")
    @ApiOperation("Get Grades by Batch Id")
    public ResponseEntity<List<AssignmentSubmitDTO>> getGradesByBatchId(@PathVariable Integer batchId) {
        List<AssignmentSubmitDTO> assignmentSubmitDTOs = assignmentSubmitService.getGradesByBatchId(batchId);
        return ResponseEntity.ok(assignmentSubmitDTOs);
    }

    @GetMapping("/{assignmentId}")
    @ApiOperation("Get Submission by Assignment Id")
    public ResponseEntity<List<AssignmentSubmitDTO>> getSubmissionsByAssignmentId(@PathVariable Long assignmentId){
    	 List<AssignmentSubmitDTO> AssignmentsubmissionDTO = assignmentSubmitService.getSubmissionsByAssignment(assignmentId);
         return ResponseEntity.ok(AssignmentsubmissionDTO);
    }
    
    @GetMapping("/program/{programid}")
    @ApiOperation("Get Gades by Program Id")
    public ResponseEntity<List<AssignmentSubmitDTO>> getGradesByProgramId(@PathVariable Long programid) {
       
    	List<AssignmentSubmitDTO> gradesListByProgram = assignmentSubmitService.getGradesByProgramId(programid);
        return ResponseEntity.ok(gradesListByProgram);
    }
    
}
