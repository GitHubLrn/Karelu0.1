package com.kmust.Karelu.service.impl;

import com.kmust.Karelu.entity.Account;
import com.kmust.Karelu.mapper.AccountMapper;
import com.kmust.Karelu.service.IAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Karelu
 * @since 2021-05-30
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

}
