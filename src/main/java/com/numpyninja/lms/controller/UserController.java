package com.numpyninja.lms.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import com.numpyninja.lms.dto.*;
import com.numpyninja.lms.entity.UserRoleMap;
import com.numpyninja.lms.entity.UserRoleProgramBatchMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.numpyninja.lms.exception.DuplicateResourceFoundException;
import com.numpyninja.lms.exception.InvalidDataException;
import com.numpyninja.lms.exception.ResourceNotFoundException;
import com.numpyninja.lms.mappers.UserMapper;
import com.numpyninja.lms.services.UserServices;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
//@RequestMapping("/users")
@Api(tags="User Controller", description="User CRUD Operations")
public class UserController {

	private UserMapper userMapper;
	private UserServices userServices;

	@Autowired
	public UserController(
			UserMapper userMapper,
			UserServices userServices

	) {
		this.userMapper = userMapper;
		this.userServices = userServices;

	}

	//get all users from LMS_Users table
	@GetMapping("/users")
	@ApiOperation("Get all Users")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		//List<User> userList = userServices.getAllUsers();
		List<UserDto> userList = userServices.getAllUsers();
		return ResponseEntity.ok(userList);
	}


	//get user by ID - user, role, program, batch, skill and other details
	@GetMapping("/users/{id}")
	@ApiOperation("Get User Information by ID")
	public ResponseEntity getUserInfoById(@PathVariable String id) {
		UserAllDto userInfo = userServices.getUserInfoById(id);
		return ResponseEntity.ok(userInfo);
	}

	//create user with Role
	@PostMapping("/users/roleStatus")
	@ApiOperation("Create User Login with Role")
	public ResponseEntity<UserDto> createUserloginWithRole(@Valid @RequestBody UserLoginRoleDTO newUserRoleDto) throws InvalidDataException, DuplicateResourceFoundException {
		UserDto responseDto = userServices.createUserLoginWithRole(newUserRoleDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
	}

	//update user info in User Table
	@PutMapping("/users/{userId}")
	 @ApiOperation("Update User")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto updateuserDto, @PathVariable(value = "userId") String userId) throws ResourceNotFoundException, InvalidDataException {
		UserDto responseDto = userServices.updateUser(updateuserDto, userId);
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}

	//Ask front end to include a separate link to update role status for user
	//update User role - (Active/inactive) for a given user id and role id
	@PutMapping("/users/roleStatus/{userId}")
	@ApiOperation("Update User Role Status")
	public ResponseEntity<String> updateUserRoleStatus(@Valid @PathVariable(value = "userId") String userId, @Valid @RequestBody UserRoleMapSlimDTO updateUserRoleStatus) throws InvalidDataException {
		//String UserRole, String UserStatus
		String responseDto = userServices.updateUserRoleStatus(updateUserRoleStatus, userId);
		return ResponseEntity.status(HttpStatus.OK).body("UserStatus Updated for User: " + userId);
	}

	//cascade deletes users and User roles
	@DeleteMapping("/users/{userId}")
	@ApiOperation("Delete User")
	public ResponseEntity<String> deleteUser(@PathVariable(value = "userId") String userId) throws ResourceNotFoundException {
		String deletedUserId = userServices.deleteUser(userId);
		return ResponseEntity.status(HttpStatus.OK).body("Deleted User ID:  " + deletedUserId);
		//return deletedUserId;
	}

	@GetMapping("/users/getAllStaff")
	@ApiOperation("Get All Staff")
	public ResponseEntity<List<Object>> getAllStaff() {
		List<Object> list = userServices.getAllStaff();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// Ask front end to include a separate link to assign program/batch to existing user
	// Update existing user to assign program and its corresponding batch
	@PutMapping("/users/roleProgramBatchStatus/{userId}")
	@ApiOperation("Update User Role Program Batch status")
	public ResponseEntity<String> assignUpdateUserRoleProgramBatchStatus(@PathVariable String userId,
																		 @RequestBody UserRoleProgramBatchDto userRoleProgramBatchDto) {
		String response = userServices.assignUpdateUserRoleProgramBatchStatus(userRoleProgramBatchDto, userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}


	//USER - GET USER BY PROGRAM-Batch
	@GetMapping("/users/programBatch/{batchId}")
	@ApiOperation("Get User by Program Batches")
	public ResponseEntity<List<UserDto>> getUserByProgramBatches(@PathVariable Integer batchId) throws ResourceNotFoundException
	{
			return ResponseEntity.ok(this.userServices.getUserByProgramBatch(batchId));
	}
	
	
	@GetMapping("/users/programs/{programId}")
	@ApiOperation("Get User for Program")
	public ResponseEntity<List<UserDto>> getUsersForProgram(@PathVariable Long programId) throws ResourceNotFoundException {
		{
			List<UserDto> list = userServices.getUsersByProgram(programId);
			return ResponseEntity.status(HttpStatus.OK).body(list);
		}

	}

	@PutMapping("/users/userLogin/{userId}")
	@ApiOperation("Update User Login Status")
	public ResponseEntity<String> updateUserLoginStatus(@Valid @PathVariable(value = "userId") String userId, @Valid @RequestBody UserLoginDto updateUserLogin) throws InvalidDataException {
		//String UserRole, String UserStatus
		String responseDto = userServices.updateUserLogin(updateUserLogin,userId);
		return ResponseEntity.status(HttpStatus.OK).body("UserLoginEmail/Status Updated for User: " + userId);
	}

    @GetMapping("/users/roles")
    protected List<UserRoleMap> getAllUsersWithRoles() {
        return userServices.getAllUsersWithRoles();
    }
}
		
		
		
		
	

    
	//Check if this is needed form front end or not??
	//Get all users with all their info - Role, status
	/*@GetMapping("/users/roles")
	protected List<?> getAllUsersWithRoles() {
		return userServices.getAllUsersWithRoles() ;
	}
	//Get all the users for a given role (Admin,Staff,User)- only giving user table info
    @GetMapping("/users/roles/{rolename}")
    protected List<?> getAllUsersByRole(@PathVariable(value="rolename")String roleName) {
    	return userMapper.userDtos( userServices.getAllUsersByRole(roleName) );
    }
	 @GetMapping("/users/roles/{rolename}")
     protected List<?> getAllRoles(@PathVariable(value="rolename")String roleName) {
     	return userMapper.userDtos( userServices.getAllUsersByRole(roleName) );
     }
	//Creates user only but no role added
    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto newuserDto) throws InvalidDataException, DuplicateResourceFoundException {
    	UserDto responseDto = userServices.createUser(newuserDto);
    	return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
	@PutMapping("/users/roleStatus/{userId}")
    public ResponseEntity<UserDto> updateUserWithRole(@Valid @RequestBody UserAndRoleDTO updateUserRoleDto, @PathVariable(value="userId") String userId) throws DuplicateResourceFoundException, ResourceNotFoundException, InvalidDataException {
    	UserDto responseDto = userServices.updateUserWithRole(updateUserRoleDto, userId);

    	return ResponseEntity.status(HttpStatus.OK).body(responseDto); 
    }
	
	*/
	
	