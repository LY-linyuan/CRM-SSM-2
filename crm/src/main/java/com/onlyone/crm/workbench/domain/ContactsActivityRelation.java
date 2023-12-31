package com.onlyone.crm.workbench.domain;

import java.util.Objects;

public class ContactsActivityRelation {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_contacts_activity_relation.id
     *
     * @mbggenerated Sun Dec 10 15:03:04 CST 2023
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_contacts_activity_relation.contacts_id
     *
     * @mbggenerated Sun Dec 10 15:03:04 CST 2023
     */
    private String contactsId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_contacts_activity_relation.activity_id
     *
     * @mbggenerated Sun Dec 10 15:03:04 CST 2023
     */
    private String activityId;

    @Override
    public String toString() {
        return "ContactsActivityRelation{" +
                "id='" + id + '\'' +
                ", contactsId='" + contactsId + '\'' +
                ", activityId='" + activityId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactsActivityRelation that = (ContactsActivityRelation) o;
        return Objects.equals(id, that.id) && Objects.equals(contactsId, that.contactsId) && Objects.equals(activityId, that.activityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contactsId, activityId);
    }

    public ContactsActivityRelation() {
    }

    public ContactsActivityRelation(String id, String contactsId, String activityId) {
        this.id = id;
        this.contactsId = contactsId;
        this.activityId = activityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_contacts_activity_relation.id
     *
     * @return the value of tbl_contacts_activity_relation.id
     *
     * @mbggenerated Sun Dec 10 15:03:04 CST 2023
     */


    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_contacts_activity_relation.id
     *
     * @param id the value for tbl_contacts_activity_relation.id
     *
     * @mbggenerated Sun Dec 10 15:03:04 CST 2023
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_contacts_activity_relation.contacts_id
     *
     * @return the value of tbl_contacts_activity_relation.contacts_id
     *
     * @mbggenerated Sun Dec 10 15:03:04 CST 2023
     */
    public String getContactsId() {
        return contactsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_contacts_activity_relation.contacts_id
     *
     * @param contactsId the value for tbl_contacts_activity_relation.contacts_id
     *
     * @mbggenerated Sun Dec 10 15:03:04 CST 2023
     */
    public void setContactsId(String contactsId) {
        this.contactsId = contactsId == null ? null : contactsId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_contacts_activity_relation.activity_id
     *
     * @return the value of tbl_contacts_activity_relation.activity_id
     *
     * @mbggenerated Sun Dec 10 15:03:04 CST 2023
     */
    public String getActivityId() {
        return activityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_contacts_activity_relation.activity_id
     *
     * @param activityId the value for tbl_contacts_activity_relation.activity_id
     *
     * @mbggenerated Sun Dec 10 15:03:04 CST 2023
     */
    public void setActivityId(String activityId) {
        this.activityId = activityId == null ? null : activityId.trim();
    }
}
