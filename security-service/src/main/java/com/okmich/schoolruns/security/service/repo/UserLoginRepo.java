/**
 *
 */
package com.okmich.schoolruns.security.service.repo;

import com.okmich.schoolruns.common.entity.UserLogin;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Michael
 *
 */
@Repository
public interface UserLoginRepo extends JpaRepository<UserLogin, Integer> {

    @Query(name = "findByUsername", value = "SELECT u FROM UserLogin u WHERE u.username = ?1")
    UserLogin findByUsername(String userName);

    @Query(name = "findNonParentUserByEmail", value = "SELECT u FROM UserLogin u WHERE u.email = ?1 AND u.systemRole.systemRoleId <> 4")
    UserLogin findNonParentUserByEmail(String email);

    @Query(name = "findParentUserByEmail", value = "SELECT u FROM UserLogin u WHERE u.email = ?1 AND u.systemRole.systemRoleId = 4")
    UserLogin findParentUserByEmail(String email);

    @Query(name = "findParentUserByPhoneNumber", value = "SELECT u FROM UserLogin u WHERE u.username = ?1")
    UserLogin findParentUserByPhoneNumber(String phoneNumber);

    @Query(name = "findByPhoneNumber", value = "SELECT u FROM UserLogin u "
    + "WHERE u.phoneNumber = ?1 AND u.systemRole.systemRoleId <> 4")
    UserLogin findByPhoneNumber(String phoneNumber);

    @Query(name = "findEmployeeUserLogin", value = "SELECT * FROM user_login u join employee e "
    + "on u.user_login_id = e.user_login_id WHERE e.employee_id = ?1", nativeQuery = true)
    UserLogin findEmployeeUserLogin(Integer employeeId);

    @Query(name = "findSchoolAdmin", value = "SELECT u FROM UserLogin u WHERE u.school.schoolId = ?1 AND u.schoolAdmin = 1")
    List<UserLogin> findSchoolAdmin(Integer schoolId);

    @Query(name = "findParentLogin", value = "SELECT u FROM UserLogin u WHERE u.userLoginId = "
    + "(SELECT p.userLoginId FROM Parent p WHERE p.phoneNumber = ?1)")
    UserLogin findParentLogin(String parentId);

    @Query(name = "findDuplicateByEmailAndRole",
    value = "SELECT u FROM UserLogin u WHERE u.email = ?1 AND u.systemRole.systemRoleId = ?2")
    List<UserLogin> findDuplicateByEmailAndRole(String email, Integer roleId);
}
