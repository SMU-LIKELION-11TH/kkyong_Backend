package smu.likelion.kkyong.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smu.likelion.kkyong.config.auth.AuthUtil;
import smu.likelion.kkyong.domain.entity.Bookmark;
import smu.likelion.kkyong.domain.entity.Services;
import smu.likelion.kkyong.domain.entity.Users;
import smu.likelion.kkyong.dto.service.ServiceReturnDto;
import smu.likelion.kkyong.repository.BookmarkRepository;
import smu.likelion.kkyong.repository.ServiceRepository;
import smu.likelion.kkyong.repository.UserRepository;
import smu.likelion.kkyong.util.ExceptionUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookmarkServiceImpl implements BookmarkService {

    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;


    private Users findUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> ExceptionUtil.id(email, Users.class.getName())
        );
    }

    private Services findService(Long serviceId) {
        return serviceRepository.findById(serviceId).orElseThrow(
                () -> ExceptionUtil.id(serviceId, Services.class.getName())
        );
    }

    private Bookmark findBookmark(Services service, Users user) {
        return bookmarkRepository.findByServiceAndUser(service, user).orElseThrow(
                () -> ExceptionUtil.available("No Bookmark")
        );
    }

    @Transactional
    @Override
    public List<ServiceReturnDto> getBookmarkList() {
        Users user = findUser(AuthUtil.getAuthUser());
        List<Bookmark> bookmarks = bookmarkRepository.findByUser(user);

        log.info(user.getEmail() + " : get bookmark list");

        return bookmarks.stream().map(bookmark -> ServiceReturnDto.builder()
                .entity(bookmark.getService())
                .bookmarkStatus(true)
                .build()).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void createBookmark(Long serviceId) {
        Services service = findService(serviceId);
        Users user = findUser(AuthUtil.getAuthUser());

        if (bookmarkRepository.existsByServiceAndUser(service, user)) {
            throw ExceptionUtil.available("You have been bookmark this service");
        }

        log.info(user.getEmail() + " : create bookmark " + serviceId);

        bookmarkRepository.save(Bookmark.builder()
                .service(service)
                .user(user)
                .build());
    }

    @Transactional
    @Override
    public void deleteBookmark(Long serviceId) {
        Services service = findService(serviceId);
        Users user = findUser(AuthUtil.getAuthUser());
        Bookmark bookmark = findBookmark(service, user);

        if (bookmark == null) {
            throw ExceptionUtil.available("This Service have not bookmarked");
        }

        log.info(user.getEmail() + " : delete bookmark " + serviceId);

        bookmarkRepository.delete(bookmark);
    }
}
