package com.community.jian.community.service;

import com.community.jian.community.dto.NotificationDTO;
import com.community.jian.community.dto.PaginationDTO;
import com.community.jian.community.model.User;

public interface NotificationService {
    PaginationDTO<NotificationDTO> listByRecipient(Integer page, Integer size, Long id);

    Integer countUnread(Long id);

    Long modifyStatus(Long id, User user);
}
