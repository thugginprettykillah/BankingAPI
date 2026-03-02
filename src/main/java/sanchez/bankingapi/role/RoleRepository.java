package sanchez.bankingapi.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query("SELECT r FROM RoleEntity r where r.name = :name")
    Optional<RoleEntity> findByName(@Param("name") RoleEnum name);
}
