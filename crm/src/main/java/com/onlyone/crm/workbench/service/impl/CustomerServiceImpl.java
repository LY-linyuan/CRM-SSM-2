package com.onlyone.crm.workbench.service.impl;

import com.onlyone.crm.workbench.mapper.CustomerMapper;
import com.onlyone.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 临渊
 * @Date 2023-12-11 20:07
 */

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerMapper customerMapper;

    @Override
    public List<String> queryAllCustomerName(String name) {
        return customerMapper.selectAllCustomerName(name);
    }
}
