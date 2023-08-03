package smu.likelion.kkyong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smu.likelion.kkyong.domain.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
