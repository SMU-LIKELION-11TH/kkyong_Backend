package smu.likelion.kkyong.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smu.likelion.kkyong.domain.entity.Bookmark;
import smu.likelion.kkyong.domain.entity.Services;
import smu.likelion.kkyong.domain.entity.Users;
import smu.likelion.kkyong.repository.BookmarkRepository;
import smu.likelion.kkyong.repository.ServiceRepository;
import smu.likelion.kkyong.repository.UserRepository;
import smu.likelion.kkyong.util.ExceptionUtil;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;


    private Users findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> ExceptionUtil.id(userId, Users.class.getName())
        );
    }

    private Services findService(Long serviceId) {
        return serviceRepository.findById(serviceId).orElseThrow(
                () -> ExceptionUtil.id(serviceId, Services.class.getName())
        );
    }

    private Bookmark findBookmark(Services service, Users user) {
        return bookmarkRepository.getBookmarkByServiceAndUser(service, user).orElseThrow(
                () -> ExceptionUtil.available("No Bookmark")
        );
    }

    @Transactional
    @Override
    public void createBookmark(Long userId, Long serviceId) {
        Services service = findService(serviceId);
        Users user = findUser(userId);
        Bookmark bookmark = findBookmark(service, user);
        if (bookmark != null) {
            throw ExceptionUtil.available("You have been bookmark this service");
        }

       bookmarkRepository.save(Bookmark.builder()
                .service(service)
                .user(user)
                .build());
    }

    @Transactional
    @Override
    public void deleteBookmark(Long userId, Long serviceId) {
        Services service = findService(serviceId);
        Users user = findUser(userId);
        Bookmark bookmark = findBookmark(service, user);
        if (bookmark == null) {
            throw ExceptionUtil.available("This Service have not bookmarked");
        }
        bookmarkRepository.delete(bookmark);
    }
}
