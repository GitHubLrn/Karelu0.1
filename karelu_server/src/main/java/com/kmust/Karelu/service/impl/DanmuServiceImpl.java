package com.kmust.Karelu.service.impl;

import com.kmust.Karelu.entity.DanMuShow;
import com.kmust.Karelu.entity.Danmu;
import com.kmust.Karelu.entity.RespBean;
import com.kmust.Karelu.entity.User;
import com.kmust.Karelu.mapper.DanmuMapper;
import com.kmust.Karelu.service.IDanmuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kmust.Karelu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Karelu
 * @since 2021-05-21
 */
@Service
public class DanmuServiceImpl extends ServiceImpl<DanmuMapper, Danmu> implements IDanmuService {

    @Autowired
    private IUserService userService;
    @Autowired
    private DanmuMapper danmuMapper;
    @Autowired
    private IDanmuService danmuService;

    @Override
    public RespBean getDanmu(Principal principal) {
        User user = userService.getAdminByUsername(principal.getName());
        Long id = user.getId();
        List<Danmu> danmuList = danmuService.list();
        List<DanMuShow> res = new ArrayList<>();
        for (Danmu d:danmuList
             ) {
            if (d.getUid().toString().equals(new Integer(id.toString()))){
                continue;
            }
            else{
                DanMuShow ds = new DanMuShow();
                ds.setAvatar(d.getIcon());
                ds.setMsg(d.getDanmu());
                ds.setTime((int)(Math.random()*3)+11);
                res.add(ds);
            }
        }
        return RespBean.sucess("",res);
    }
}
