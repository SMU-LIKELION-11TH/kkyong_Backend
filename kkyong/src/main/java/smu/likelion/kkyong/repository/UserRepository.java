package smu.likelion.kkyong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smu.likelion.kkyong.domain.entity.Users;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    public Optional<Users> findByEmail(String email);
}
