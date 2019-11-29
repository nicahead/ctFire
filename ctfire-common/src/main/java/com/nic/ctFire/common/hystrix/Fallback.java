package com.nic.ctFire.common.hystrix;


import com.nic.ctFire.common.constants.HttpStatusConstants;
import com.nic.ctFire.common.dto.BaseResult;
import com.nic.ctFire.common.utils.MapperUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用的熔断方法
 */
public class Fallback {
    /**
     * 502
     * @return
     */
    public static String badGateway() {
        BaseResult.Error error = new BaseResult.Error(
                String.valueOf(HttpStatusConstants.BAD_GATEWAY.getStatus()),
                HttpStatusConstants.BAD_GATEWAY.getContent());
        List<BaseResult.Error> errors = new ArrayList<BaseResult.Error>();
        errors.add(error);
        BaseResult baseResult = BaseResult.notOk(errors);
        try {
            return MapperUtils.obj2json(baseResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
