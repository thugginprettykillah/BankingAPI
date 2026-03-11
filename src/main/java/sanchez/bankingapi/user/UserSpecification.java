package sanchez.bankingapi.user;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import sanchez.bankingapi.role.RoleEntity;

public class UserSpecification {

    public static Specification<UserEntity> hasRoleId(Long roleId)
    {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (roleId == null) {
                return criteriaBuilder.conjunction();
            }

            Join<UserEntity, RoleEntity> roles = root.join("roles");
            return criteriaBuilder.equal(roles.get("id"), roleId);
        };
    }

    public static Specification<UserEntity> emailContains(String email)
    {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (email == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), '%' + email.toLowerCase() + '%');
        };
    }
}
