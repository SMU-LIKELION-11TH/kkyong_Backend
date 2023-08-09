package smu.likelion.kkyong.service;


import smu.likelion.kkyong.dto.service.ServiceReturnDto;

import java.util.List;

public interface BookmarkService {
    public List<ServiceReturnDto> getBookmarkList(Long userId);
    public void createBookmark(Long serviceId, Long userId);
    public void deleteBookmark(Long serviceId, Long userId);
}
