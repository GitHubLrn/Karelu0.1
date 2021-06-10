package com.kmust.Karelu.service.impl;

import com.kmust.Karelu.entity.Chat;
import com.kmust.Karelu.mapper.ChatMapper;
import com.kmust.Karelu.service.IChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Karelu
 * @since 2021-05-24
 */
@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements IChatService {

}
