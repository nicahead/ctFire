package com.nic.ctFire.web.device.controller;

import com.nic.ctFire.common.domain.PageInfo;
import com.nic.ctFire.common.domain.TbData;
import com.nic.ctFire.common.domain.TbDevice;
import com.nic.ctFire.common.dto.BaseResult;
import com.nic.ctFire.common.utils.MapperUtils;
import com.nic.ctFire.web.device.service.DataService;
import com.nic.ctFire.web.device.service.DeviceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DataService dataService;

    @ModelAttribute("editDevice")
    public TbDevice editDevice(String id) {
        TbDevice editDevice = null;
        if (StringUtils.isBlank(id)) {
            editDevice = new TbDevice();
        } else {
            String json = deviceService.get(id);
            if (StringUtils.isNotBlank(json)) {
                try {
                    BaseResult res = MapperUtils.json2pojo(json, BaseResult.class);
                    editDevice = MapperUtils.map2pojo((Map) res.getData(), TbDevice.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return editDevice;
    }

    @GetMapping(value = {"", "main"})
    public String main() {
        return "main";
    }

    @GetMapping(value = "list")
    public String list() {
        return "list";
    }

    @GetMapping(value = "form")
    public String form() {
        return "form";
    }

    @GetMapping(value = "chat")
    public String chat(String did, Model model) {
        model.addAttribute("did", did);
        return "chat";
    }

    @PostMapping(value = "save")
    public String save(TbDevice tbDevice, RedirectAttributes redirectAttributes) {
        String deviceJson = null;
        try {
            deviceJson = MapperUtils.obj2json(tbDevice);
            BaseResult baseResult = MapperUtils.json2pojo(deviceService.save(deviceJson), BaseResult.class);
            redirectAttributes.addFlashAttribute("baseResult", baseResult);
            // 保存成功
            if (baseResult.getSuccess().endsWith("成功")) {
                return "redirect:/list";
            }
            // 保存失败
            else {
                return "redirect:/form";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @PostMapping(value = "delete")
    public BaseResult delete(String ids) {
        BaseResult res = null;
        try {
            res = MapperUtils.json2pojo(deviceService.delete(ids), BaseResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @ResponseBody
    @GetMapping(value = "close")
    public BaseResult close(String id) {
        BaseResult res = null;
        try {
            res = MapperUtils.json2pojo(deviceService.close(id), BaseResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "devicePage", method = RequestMethod.GET)
    public PageInfo<TbDevice> page(HttpServletRequest request, TbDevice tbDevice) {
        PageInfo page = new PageInfo();
        String drawStr = request.getParameter("draw");
        String startStr = request.getParameter("start");
        String lengthStr = request.getParameter("length");
        int draw = drawStr == null ? 0 : Integer.parseInt(drawStr);
        int start = startStr == null ? 0 : Integer.parseInt(startStr);
        int length = lengthStr == null ? 10 : Integer.parseInt(lengthStr);
        try {
            String deviceJson = MapperUtils.obj2json(tbDevice);
            String res = deviceService.page(start, length, deviceJson);
            page = MapperUtils.json2pojo(res, PageInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }

    @ResponseBody
    @RequestMapping(value = "dataPage", method = RequestMethod.GET)
    public PageInfo<TbData> page(HttpServletRequest request, TbData tbData) {
        PageInfo page = new PageInfo();
        String drawStr = request.getParameter("draw");
        String startStr = request.getParameter("start");
        String lengthStr = request.getParameter("length");
        int draw = drawStr == null ? 0 : Integer.parseInt(drawStr);
        int start = startStr == null ? 0 : Integer.parseInt(startStr);
        int length = lengthStr == null ? 10 : Integer.parseInt(lengthStr);
        try {
            String userJson = MapperUtils.obj2json(tbData);
            String res = dataService.page(start,length,userJson);
            page = MapperUtils.json2pojo(res,PageInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }
}
