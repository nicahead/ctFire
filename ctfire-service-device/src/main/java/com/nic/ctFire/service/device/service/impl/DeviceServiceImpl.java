package com.nic.ctFire.service.device.service.impl;

import com.nic.ctFire.common.domain.TbDevice;
import com.nic.ctFire.common.service.mapper.TbDeviceMapper;
import com.nic.ctFire.common.service.service.impl.BaseServiceImpl;
import com.nic.ctFire.common.utils.MapperUtils;
import com.nic.ctFire.service.device.service.DeviceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Service
@Transactional(readOnly = true)
public class DeviceServiceImpl extends BaseServiceImpl<TbDevice,TbDeviceMapper> implements DeviceService {


}

