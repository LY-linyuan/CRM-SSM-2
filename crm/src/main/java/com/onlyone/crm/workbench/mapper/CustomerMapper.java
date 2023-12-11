package com.onlyone.crm.workbench.mapper;

import com.onlyone.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbggenerated Sun Dec 10 15:03:04 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbggenerated Sun Dec 10 15:03:04 CST 2023
     */
    int insert(Customer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbggenerated Sun Dec 10 15:03:04 CST 2023
     */
    int insertSelective(Customer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbggenerated Sun Dec 10 15:03:04 CST 2023
     */
    Customer selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbggenerated Sun Dec 10 15:03:04 CST 2023
     */
    int updateByPrimaryKeySelective(Customer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbggenerated Sun Dec 10 15:03:04 CST 2023
     */
    int updateByPrimaryKey(Customer record);

    /**
     * 添加一跳客户信息
     * @param customer
     * @return
     */
    int insertCustomer(Customer customer);

    /**
     * 查询所有客户的名字
     * @return
     */
    List<String> selectAllCustomerName(String name);

    /**
     * 根据客户名字查询客户
     * @param customerName
     * @return
     */
    Customer selectCustomerByName(String customerName);
}
