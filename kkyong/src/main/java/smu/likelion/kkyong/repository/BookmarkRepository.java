package smu.likelion.kkyong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smu.likelion.kkyong.domain.entity.Bookmark;
import smu.likelion.kkyong.domain.entity.Services;
import smu.likelion.kkyong.domain.entity.Users;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    public boolean existsByServiceAndUser(Services service, Users user);
    public Optional<Bookmark> findByServiceAndUser(Services service, Users user);
    public List<Bookmark> findByUser(Users user);
}
