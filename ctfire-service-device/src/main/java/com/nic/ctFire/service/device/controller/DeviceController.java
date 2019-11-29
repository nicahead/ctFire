package com.nic.ctFire.service.device.controller;

import com.nic.ctFire.common.domain.PageInfo;
import com.nic.ctFire.common.domain.TbData;
import com.nic.ctFire.common.domain.TbDevice;
import com.nic.ctFire.common.dto.BaseResult;
import com.nic.ctFire.common.utils.MapperUtils;
import com.nic.ctFire.service.device.service.DeviceService;
import com.nic.ctFire.service.device.service.consumer.DataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

@RestController
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DataService dataService;

    @PostMapping(value = "save")
    public BaseResult save(@RequestParam(required = true) String deviceJson) {
        int result = 0;
        TbDevice tbDevice = null;
        try {
            tbDevice = MapperUtils.json2pojo(deviceJson, TbDevice.class);
            tbDevice.setSwitc(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (tbDevice != null) {
            BaseResult baseResult = check(tbDevice);
            if (baseResult.getSuccess().equals("ok")) {
                // 新增设备
                if (StringUtils.isBlank(tbDevice.getId())) {
                    tbDevice.setId(UUID.randomUUID().toString());
                    result = deviceService.insert(tbDevice);
                    new Thread(new ConnectThread(tbDevice)).start();  //开启一个连接线程
                }
                // 修改设备
                else {
                    tbDevice.setState(1);
                    result = deviceService.update(tbDevice);
                    new Thread(new ConnectThread(tbDevice)).start();  //开启一个连接线程
                }
                // 最少有一行数据受影响
                if (result > 0) {
                    return BaseResult.ok("保存设备成功");
                }
            }
            return baseResult;
        }
        return BaseResult.ok("保存设备失败");
    }

    @GetMapping(value = "close")
    public BaseResult close(@RequestParam(required = true) String id) {
        TbDevice tbDevice = deviceService.selectOne(id);
        tbDevice.setSwitc(0);
        tbDevice.setState(2);
        deviceService.update(tbDevice);
        new Thread(new ConnectThread(tbDevice)).start();  //开启一个连接线程
        return BaseResult.ok("操作成功");
    }

    @PostMapping(value = "delete")
    public BaseResult delete(@RequestParam(required = true) String ids) {
        int result = 0;
        if (!StringUtils.isBlank(ids)) {
            String[] idArray = ids.split(",");
            if (idArray.length > 1)
                result = deviceService.deleteMulti(idArray);
            else
                result = deviceService.delete(idArray[0]);
        }
        if (result > 0)
            return BaseResult.ok("删除成功");
        return BaseResult.ok("删除失败");
    }

    @GetMapping(value = "get")
    public BaseResult get(@RequestParam(required = true) String id) {
        TbDevice tbDevice = deviceService.selectOne(id);
        if (tbDevice != null)
            return BaseResult.ok(tbDevice);
        return BaseResult.ok("没有查询到该设备");
    }

    @GetMapping(value = "page")
    public PageInfo<TbDevice> page(@RequestParam(required = true) int start,
                                   @RequestParam(required = true) int length,
                                   @RequestParam(required = true) String deviceJson) {
        TbDevice tbDevice = null;
        try {
            tbDevice = MapperUtils.json2pojo(deviceJson, TbDevice.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceService.page(start, length, tbDevice);
    }

    private BaseResult check(TbDevice tbDevice) {
        BaseResult baseResult = new BaseResult();
        if (StringUtils.isBlank(tbDevice.getName())) {
            baseResult.setSuccess("设备名不能为空");
            return baseResult;
        }
        if (StringUtils.isBlank(tbDevice.getServer())) {
            baseResult.setSuccess("设备ip不能为空");
            return baseResult;
        }
        if (tbDevice.getPort() == null) {
            baseResult.setSuccess("设备端口不能为空");
            return baseResult;
        }
        if (StringUtils.isBlank(tbDevice.getLocated())) {
            baseResult.setSuccess("设备地址不能为空");
            return baseResult;
        }
        if (tbDevice.getMaxVoltage() == null) {
            baseResult.setSuccess("电压最大值不能为空");
            return baseResult;
        }
        if (tbDevice.getMinVoltage() == null) {
            baseResult.setSuccess("电压最小值不能为空");
            return baseResult;
        }
        if (tbDevice.getMaxCurrent() == null) {
            baseResult.setSuccess("电流最大值不能为空");
            return baseResult;
        }
        if (tbDevice.getMinCurrent() == null) {
            baseResult.setSuccess("电流最小值不能为空");
            return baseResult;
        }
        if (tbDevice.getMaxTemper() == null) {
            baseResult.setSuccess("温度最大值不能为空");
            return baseResult;
        }
        if (tbDevice.getMinTemper() == null) {
            baseResult.setSuccess("温度最小值不能为空");
            return baseResult;
        }
        baseResult.setSuccess("ok");
        return baseResult;
    }


    class ConnectThread implements Runnable {

        private TbDevice device;

        public ConnectThread(TbDevice device) {
            this.device = device;
        }

        @Override
        public void run() {
            String data;
            TbData tbData;
            Socket s = null;
            PrintWriter out = null;
            BufferedReader bin = null;
            try {
                s = new Socket(device.getServer(), device.getPort());        //客户端建立socket服务，指定目的主机和端口
                out = new PrintWriter(s.getOutputStream(), true);  //目的-socket输出流，包装成打印流
                out.println(MapperUtils.obj2json(device));            //写入socket输出流
                bin = new BufferedReader(new InputStreamReader(s.getInputStream()));    //源-读取socket输入流
                while (!(data = bin.readLine()).equals("exit")) {
                    tbData = MapperUtils.json2pojo(data, TbData.class);
                    if (tbData.getVoltage() > device.getMaxVoltage() || tbData.getVoltage() < device.getMinVoltage() ||
                            tbData.getCurrent() > device.getMaxCurrent() || tbData.getCurrent() < device.getMinCurrent() ||
                            tbData.getTemper() > device.getMaxTemper() || tbData.getTemper() < device.getMinTemper()) {
                        tbData.setState(2);
                    }
                    else{
                        tbData.setState(1);
                    }
                    tbData.setId(UUID.randomUUID().toString());
                    tbData.setDid(device.getId());
                    dataService.save(MapperUtils.obj2json(tbData));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                device.setState(2);
                deviceService.update(device);
                try {
                    bin.close();
                    out.close();
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

