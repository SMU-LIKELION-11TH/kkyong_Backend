package smu.likelion.kkyong.service;


import smu.likelion.kkyong.dto.service.ServiceReturnDto;

import java.util.List;

public interface BookmarkService {
    public List<ServiceReturnDto> getBookmarkList();
    public void createBookmark(Long serviceId);
    public void deleteBookmark(Long serviceId);
}
