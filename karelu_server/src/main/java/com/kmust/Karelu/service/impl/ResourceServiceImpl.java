package com.kmust.Karelu.service.impl;

import com.kmust.Karelu.entity.Resource;
import com.kmust.Karelu.mapper.ResourceMapper;
import com.kmust.Karelu.service.IResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Karelu
 * @since 2021-04-23
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

}
