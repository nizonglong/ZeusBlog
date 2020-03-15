package com.nzl.dao.provider;

import com.nzl.pojo.ArticleBlog;
import org.apache.ibatis.jdbc.SQL;

public class ArticleBlogSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_blog
     *
     * @mbg.generated
     */
    public String insertSelective(ArticleBlog record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("article_blog");
        
        if (record.getArticleBlogId() != null) {
            sql.VALUES("article_blog_id", "#{articleBlogId,jdbcType=BIGINT}");
        }
        
        if (record.getTitle() != null) {
            sql.VALUES("title", "#{title,jdbcType=CHAR}");
        }
        
        if (record.getBlogTime() != null) {
            sql.VALUES("blog_time", "#{blogTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUid() != null) {
            sql.VALUES("uid", "#{uid,jdbcType=VARCHAR}");
        }
        
        if (record.getBlogTypeId() != null) {
            sql.VALUES("blog_type_id", "#{blogTypeId,jdbcType=VARCHAR}");
        }
        
        if (record.getBlogStatus() != null) {
            sql.VALUES("blog_status", "#{blogStatus,jdbcType=CHAR}");
        }
        
        if (record.getGmtModified() != null) {
            sql.VALUES("gmt_modified", "#{gmtModified,jdbcType=TIMESTAMP}");
        }
        
        if (record.getGmtCreate() != null) {
            sql.VALUES("gmt_create", "#{gmtCreate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getContent() != null) {
            sql.VALUES("content", "#{content,jdbcType=LONGVARCHAR}");
        }

        if (record.getDigest() != null) {
            sql.VALUES("digest", "#{digest,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_blog
     *
     * @mbg.generated
     */
    public String updateByPrimaryKeySelective(ArticleBlog record) {
        SQL sql = new SQL();
        sql.UPDATE("article_blog");
        
        if (record.getTitle() != null) {
            sql.SET("title = #{title,jdbcType=CHAR}");
        }
        
        if (record.getBlogTime() != null) {
            sql.SET("blog_time = #{blogTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUid() != null) {
            sql.SET("uid = #{uid,jdbcType=VARCHAR}");
        }
        
        if (record.getBlogTypeId() != null) {
            sql.SET("blog_type_id = #{blogTypeId,jdbcType=VARCHAR}");
        }
        
        if (record.getBlogStatus() != null) {
            sql.SET("blog_status = #{blogStatus,jdbcType=CHAR}");
        }
        
        if (record.getGmtModified() != null) {
            sql.SET("gmt_modified = #{gmtModified,jdbcType=TIMESTAMP}");
        }
        
        if (record.getGmtCreate() != null) {
            sql.SET("gmt_create = #{gmtCreate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getContent() != null) {
            sql.SET("content = #{content,jdbcType=LONGVARCHAR}");
        }

        if (record.getContent() != null) {
            sql.SET("digest = #{digest,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("article_blog_id = #{articleBlogId,jdbcType=BIGINT}");
        
        return sql.toString();
    }
}