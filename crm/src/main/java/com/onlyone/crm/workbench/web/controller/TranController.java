package com.onlyone.crm.workbench.web.controller;

import com.onlyone.crm.commons.contants.Contants;
import com.onlyone.crm.commons.domain.ReturnObject;
import com.onlyone.crm.settings.domain.DicValue;
import com.onlyone.crm.settings.domain.User;
import com.onlyone.crm.settings.service.DicValueService;
import com.onlyone.crm.settings.service.UserService;
import com.onlyone.crm.workbench.domain.Tran;
import com.onlyone.crm.workbench.domain.TranHistory;
import com.onlyone.crm.workbench.domain.TranRemark;
import com.onlyone.crm.workbench.service.CustomerService;
import com.onlyone.crm.workbench.service.TranHistoryService;
import com.onlyone.crm.workbench.service.TranRemarkService;
import com.onlyone.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @Author 临渊
 * @Date 2023-12-11 18:54
 */

@Controller
public class TranController {

    @Autowired
    private DicValueService dicValueService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TranService tranService;

    @Autowired
    private TranRemarkService tranRemarkService;

    @Autowired
    private TranHistoryService tranHistoryService;




    @RequestMapping("/workbench/transaction/index")
    public String index(HttpServletRequest request) {
        List<DicValue> stageList = dicValueService.selectDicValueByTypeCode("stage");
        List<DicValue> transactionTypeList = dicValueService.selectDicValueByTypeCode("transactionType");
        List<DicValue> sourceList = dicValueService.selectDicValueByTypeCode("source");
        request.setAttribute("stageList", stageList);
        request.setAttribute("transactionTypeList", transactionTypeList);
        request.setAttribute("sourceList", sourceList);
        return  "workbench/transaction/index";
    }

    @RequestMapping("/workbench/transaction/toSave")
    public String toSave(HttpServletRequest request) {
        List<User> userList = userService.selectAllNoLockStateUser();
        List<DicValue> stageList = dicValueService.selectDicValueByTypeCode("stage");
        List<DicValue> transactionTypeList = dicValueService.selectDicValueByTypeCode("transactionType");
        List<DicValue> sourceList = dicValueService.selectDicValueByTypeCode("source");
        request.setAttribute("userList", userList);
        request.setAttribute("stageList", stageList);
        request.setAttribute("transactionTypeList", transactionTypeList);
        request.setAttribute("sourceList", sourceList);
        return  "workbench/transaction/save";
    }

    @RequestMapping("/workbench/transaction/getPossibilityByStage")
    public @ResponseBody String getPossibilityByStage(String stageValue) {
        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
        return bundle.getString(stageValue);
    }

    @RequestMapping("/workbench/transaction/queryAllCustomerName")
    public @ResponseBody Object queryAllCustomerName(String customerName) {
        return customerService.queryAllCustomerName(customerName);
    }

    @RequestMapping("/workbench/transaction/saveCreateTran")
    public @ResponseBody Object saveCreateTran(@RequestParam Map<String,Object> map, HttpSession session){
        map.put(Contants.SESSION_USER,session.getAttribute(Contants.SESSION_USER));
        ReturnObject returnObject=new ReturnObject();
        try {
            tranService.saveCreateTran(map);
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试....");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/transaction/detailTran")
    public String detailTran(String id, HttpServletRequest request){
        // 调用service层方法，查询数据
        Tran tran = tranService.queryTranForDetailById(id);
        List<TranRemark> remarkList = tranRemarkService.queryTranRemarkForDetailByTranId(id);
        List<TranHistory> historyList = tranHistoryService.queryTranHistoryForDetailByTranId(id);

        // 根据tran所处阶段名称查询可能性
        ResourceBundle bundle=ResourceBundle.getBundle("possibility");
        String possibility=bundle.getString(tran.getStage());
        tran.setPossibility(possibility);

        //把数据保存到request中
        request.setAttribute("tran",tran);
        request.setAttribute("remarkList",remarkList);
        request.setAttribute("historyList",historyList);
        // request.setAttribute("possibility",possibility);

        // 调用service方法，查询交易所有的阶段
        List<DicValue> stageList = dicValueService.selectDicValueByTypeCode("stage");
        request.setAttribute("stageList",stageList);

        // 请求转发
        return "workbench/transaction/detail";
    }

}
