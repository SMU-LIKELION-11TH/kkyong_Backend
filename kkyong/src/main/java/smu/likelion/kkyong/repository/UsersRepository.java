package smu.likelion.kkyong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smu.likelion.kkyong.domain.entity.users.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    public Optional<Users> findByEmail(String email);
    public Optional<Users> findById(String userId);
}
