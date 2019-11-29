package com.nic.ctFire.service.data.service.impl;

import com.nic.ctFire.common.domain.TbData;
import com.nic.ctFire.common.service.mapper.TbDataMapper;
import com.nic.ctFire.common.service.service.impl.BaseServiceImpl;
import com.nic.ctFire.service.data.service.DataService;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImpl extends BaseServiceImpl<TbData,TbDataMapper> implements DataService {
}
