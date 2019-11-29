package com.nic.ctFire.service.data.controller;

import com.nic.ctFire.common.domain.PageInfo;
import com.nic.ctFire.common.domain.TbData;
import com.nic.ctFire.common.domain.TbDevice;
import com.nic.ctFire.common.dto.BaseResult;
import com.nic.ctFire.common.utils.MapperUtils;
import com.nic.ctFire.service.data.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class DataController {

    @Autowired
    private DataService dataService;

    @PostMapping(value = "save")
    public BaseResult save(@RequestParam(required = true) String dataJson) {
        int result = 0;
        TbData tbData = null;
        try {
            tbData = MapperUtils.json2pojo(dataJson, TbData.class);
            if (tbData != null) {
                tbData.setId(UUID.randomUUID().toString());
                result = dataService.insert(tbData);
                if (result > 0) {
                    return BaseResult.ok("保存数据成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResult.ok("保存数据失败");
    }

    @GetMapping(value = "page")
    public PageInfo<TbData> page(@RequestParam(required = true) int start,
                                   @RequestParam(required = true) int length,
                                   @RequestParam(required = true) String dataJson) {
        TbData tbData = null;
        try {
            tbData = MapperUtils.json2pojo(dataJson, TbData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataService.page(start, length, tbData);
    }
}
