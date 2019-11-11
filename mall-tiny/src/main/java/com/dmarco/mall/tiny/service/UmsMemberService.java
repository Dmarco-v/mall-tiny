package com.dmarco.mall.tiny.service;

import com.dmarco.mall.tiny.common.api.CommonResult;
import org.springframework.stereotype.Service;

/**
 * 会员管理Service
 * @author dmarco
 */
@Service
public interface UmsMemberService {

    /**
     * 生成验证码
     */
    CommonResult generateAuthCode(String telephone);

    /**
     * 判断验证码和手机号码是否匹配
     */
    CommonResult verifyAuthCode(String telephone, String authCode);

}
