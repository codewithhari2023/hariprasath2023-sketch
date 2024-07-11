package OnlineEBookStore.demo.Repository;

import OnlineEBookStore.demo.Model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.LinkOption;

@Repository
public interface RoleRepository extends JpaRepository<Roles,Long> {
    Roles findByRole(String role);
}
