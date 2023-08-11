package smu.likelion.kkyong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smu.likelion.kkyong.domain.entity.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
