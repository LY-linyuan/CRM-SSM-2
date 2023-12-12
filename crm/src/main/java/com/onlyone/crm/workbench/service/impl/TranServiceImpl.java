package com.onlyone.crm.workbench.service.impl;

import com.onlyone.crm.commons.contants.Contants;
import com.onlyone.crm.commons.utils.DateUtil;
import com.onlyone.crm.commons.utils.UUIDUtil;
import com.onlyone.crm.settings.domain.User;
import com.onlyone.crm.workbench.domain.Customer;
import com.onlyone.crm.workbench.domain.FunnelVO;
import com.onlyone.crm.workbench.domain.Tran;
import com.onlyone.crm.workbench.domain.TranHistory;
import com.onlyone.crm.workbench.mapper.CustomerMapper;
import com.onlyone.crm.workbench.mapper.TranHistoryMapper;
import com.onlyone.crm.workbench.mapper.TranMapper;
import com.onlyone.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author 临渊
 * @Date 2023-12-11 20:29
 */

@Service
public class TranServiceImpl implements TranService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private TranMapper tranMapper;

    @Autowired
    private TranHistoryMapper tranHistoryMapper;

    @Override
    public void saveCreateTran(Map<String, Object> map) {
        String customerName=(String) map.get("customerName");
        User user=(User) map.get(Contants.SESSION_USER);
        // 根据name精确查询客户
        Customer customer = customerMapper.selectCustomerByName(customerName);
        // 如果客户不存在，则新建客户
        if(customer == null){
            customer=new Customer();
            customer.setOwner(user.getId());
            customer.setName(customerName);
            customer.setId(UUIDUtil.getUUID());
            customer.setCreateTime(DateUtil.formatDateTime(new Date()));
            customer.setCreateBy(user.getId());
            customerMapper.insertCustomer(customer);
        }
        // 保存创建的交易
        Tran tran = new Tran();
        tran.setStage((String) map.get("stage"));
        tran.setOwner((String) map.get("owner"));
        tran.setNextContactTime((String) map.get("nextContactTime"));
        tran.setName((String) map.get("name"));
        tran.setMoney((String) map.get("money"));
        tran.setId(UUIDUtil.getUUID());
        tran.setExpectedDate((String) map.get("expectedDate"));
        tran.setCustomerId(customer.getId());
        tran.setCreateTime(DateUtil.formatDateTime(new Date()));
        tran.setCreateBy(user.getId());
        tran.setContactSummary((String) map.get("contactSummary"));
        tran.setContactsId((String) map.get("contactsId"));
        tran.setActivityId((String) map.get("activityId"));
        tran.setDescription((String) map.get("description"));
        tran.setSource((String) map.get("source"));
        tran.setType((String) map.get("type"));
        tranMapper.insertTran(tran);
        // 保存交易历史
        TranHistory tranHistory=new TranHistory();
        tranHistory.setCreateBy(user.getId());
        tranHistory.setCreateTime(DateUtil.formatDateTime(new Date()));
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setStage(tran.getStage());
        tranHistory.setTranId(tran.getId());
        tranHistoryMapper.insertTranHistory(tranHistory);
    }

    @Override
    public Tran queryTranForDetailById(String id) {
        return tranMapper.selectTranForDetailById(id);
    }

    @Override
    public List<FunnelVO> queryCountOfTranGroupByStage() {
        return tranMapper.selectCountOfTranGroupByStage();
    }
}
