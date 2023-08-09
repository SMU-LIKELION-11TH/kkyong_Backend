package smu.likelion.kkyong.service;


public interface BookmarkService {
    public void createBookmark(Long serviceId, Long userId);
    public void deleteBookmark(Long serviceId, Long userId);
}
