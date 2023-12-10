package com.onlyone.crm.workbench.mapper;

import com.onlyone.crm.workbench.domain.ContactsActivityRelation;

import java.util.List;

public interface ContactsActivityRelationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbggenerated Sun Dec 10 15:03:04 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbggenerated Sun Dec 10 15:03:04 CST 2023
     */
    int insert(ContactsActivityRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbggenerated Sun Dec 10 15:03:04 CST 2023
     */
    int insertSelective(ContactsActivityRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbggenerated Sun Dec 10 15:03:04 CST 2023
     */
    ContactsActivityRelation selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbggenerated Sun Dec 10 15:03:04 CST 2023
     */
    int updateByPrimaryKeySelective(ContactsActivityRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbggenerated Sun Dec 10 15:03:04 CST 2023
     */
    int updateByPrimaryKey(ContactsActivityRelation record);

    /**
     * 插入contactsActivityRelationList
     * @param contactsActivityRelationList
     * @return
     */
    int insertContactsActivityRelationByList(List<ContactsActivityRelation> contactsActivityRelationList);
}