package www.com.ksm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import www.com.ksm_backend.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);

    //    @Query(value = """
//             SELECT r FROM Role r\s
//             JOIN UserRole ur ON r.role_id = ur.role_id\s
//             JOIN User u ON ur.user_id = u.user_id\s
//             WHERE u.username = :username\s
//             """)
//    @Query(value =
//            "select name from role " +
//            "join user_role on role.role_id = user_role.role_id " +
//            "join user on user_role.user_id = user.user_id " +
//            "where user.username = :username", nativeQuery = true)
//    Set<Role> findRolesByUsername(@Param("username") String username);

//    @Query(value = """
//             SELECT r.name FROM Role r\s
//             JOIN UserRole ur ON r.role_id = ur.role_id\s
//             JOIN User u ON ur.user_id = u.user_id\s
//             WHERE u.username = :username\s
//             """)
//    Role findAllRolesByUsername(String username);

    @Query("SELECT r.role_id FROM Role r WHERE r.name = :roleName")
    Integer findRoleIdByName(@Param("roleName") String roleName);
}