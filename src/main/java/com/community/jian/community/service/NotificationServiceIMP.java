package com.community.jian.community.service;

import com.community.jian.community.dto.NotificationDTO;
import com.community.jian.community.dto.NotificationStatusEum;
import com.community.jian.community.dto.NotificationTypeEnum;
import com.community.jian.community.dto.PaginationDTO;
import com.community.jian.community.exception.NotificationErrorMessage;
import com.community.jian.community.exception.ServiceException;
import com.community.jian.community.mapper.NotificationMapper;
import com.community.jian.community.model.Notification;
import com.community.jian.community.model.NotificationExample;
import com.community.jian.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceIMP implements NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public PaginationDTO<NotificationDTO> listByRecipient(Integer page, Integer size, Long id) {
        PaginationDTO<NotificationDTO> paginationDTO =new PaginationDTO<>();
        NotificationExample example = new NotificationExample();
        example.or().andRecipientEqualTo(id);
        long count = notificationMapper.countByExample(example);
        int offset = paginationDTO.initPage((int) count, page, size);
        NotificationExample example2 = new NotificationExample();
        example2.setOrderByClause("gmt_create desc");
        example2.or().andRecipientEqualTo(id);
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example2, new RowBounds(offset, size));
        if (null==notifications||notifications.size()==0){
            paginationDTO.setData(new ArrayList<NotificationDTO>());
            return paginationDTO;
        }
        List<NotificationDTO> notificationDTOList = notifications.stream().map(notification -> {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.getTypeName(notificationDTO.getType()));
            return notificationDTO;
        }).collect(Collectors.toList());
        paginationDTO.setData(notificationDTOList);
        return paginationDTO;
    }

    @Override
    public Integer countUnread(Long id) {
        NotificationExample example = new NotificationExample();
        example.or().andStatusEqualTo(NotificationStatusEum.UNREAD.getStatus())
                .andRecipientEqualTo(id);
        Integer unread = (int) notificationMapper.countByExample(example);
        return unread;
    }

    @Override
    public Long modifyStatus(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (null==notification){
            throw new ServiceException(NotificationErrorMessage.NOTIFICATION_NOT_FOUND);
        }
        if (!notification.getRecipient().equals(user.getId())){
            throw  new ServiceException(NotificationErrorMessage.NOTIFICATION_NOT_RECIPIENT_MATCH);
        }
        Notification readNotification=new Notification();
        readNotification.setStatus(NotificationStatusEum.READ.getStatus());
        readNotification.setId(notification.getId());
        notificationMapper.updateByPrimaryKeySelective(readNotification);
        Long questionId=null;
        if (notification.getType()==NotificationTypeEnum.REPLY_QUESTION.getType()||notification.getType()==NotificationTypeEnum.REPLY_COMMENT.getType()){
            questionId=notification.getAutorid();
        }else {
            questionId=notification.getAutorid();
        }
        return questionId;
    }
}
