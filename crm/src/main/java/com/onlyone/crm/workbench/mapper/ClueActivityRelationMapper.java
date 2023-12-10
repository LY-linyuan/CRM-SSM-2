package com.onlyone.crm.workbench.mapper;

import com.onlyone.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Fri Dec 08 17:48:10 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Fri Dec 08 17:48:10 CST 2023
     */
    int insert(ClueActivityRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Fri Dec 08 17:48:10 CST 2023
     */
    int insertSelective(ClueActivityRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Fri Dec 08 17:48:10 CST 2023
     */
    ClueActivityRelation selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Fri Dec 08 17:48:10 CST 2023
     */
    int updateByPrimaryKeySelective(ClueActivityRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Fri Dec 08 17:48:10 CST 2023
     */
    int updateByPrimaryKey(ClueActivityRelation record);


    /**
     * 根据ClueActivityRelation List 集合 一次性添加多条 ClueActivityRelation
     * @param clueActivityRelationsList
     * @return
     */
    int insertClueActivityRelationByList(List<ClueActivityRelation> clueActivityRelationsList);

    /**
     * 根据ClueActivityRelationId 删除 ClueActivityRelation
     * @param clueActivityRelation
     * @return
     */
    int deleteClueActivityRelationById(ClueActivityRelation clueActivityRelation);

    /**
     * 根据clueId查询clueActivityRelationList
     * @param clueId
     * @return
     */
    List<ClueActivityRelation> selectClueActivityRelationByClueId(String clueId);

    /**
     * 根据clueId 删除 clueActivityRelation
     * @param clueId
     * @return
     */
    int deleteClueActivityRelationByClueId(String clueId);
}
