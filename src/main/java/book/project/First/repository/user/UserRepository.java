package book.project.First.repository.user;

import book.project.First.entity.projection.UserProjection;
import book.project.First.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserProjection findUserAuthDataByUsername(String username);
    @Modifying
    @Query(value = "DELETE FROM UserEntity WHERE id = :id")
    int removeById(Long id);
}