package com.ghca.easyview.im.framework.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ghca.easyview.im.framework.common.Global;
import com.ghca.easyview.im.framework.util.StringUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Map;


/**
 * Description Entity支持类Mybatis扫描，一般情况下实体类都需要集成此类，以便于mybatis扫描到对于的类
 *
 * @author <a href="mailto:pan.luo@ghca.com">Luopan</a>
 * @version 1.0
 * @Company: 上海光华冠群软件有限公司
 * @PROJECT_NAME easyview-vdc-master
 * @Package com.ghca.easyview.cloud.core.persistence
 * @date 2016/1/14 15:25
 */
public abstract class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 实体编号（唯一标识）
     */
    protected String id;


    /**
     * 当前实体分页对象
     */
    protected Page<T> pageData;


    protected String sord = "desc";//默认排序

    protected String sidx;//排序字段


    /**
     * 自定义SQL（SQL标识，SQL内容）
     */
    protected Map<String, String> sqlMap;

    /**
     * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     */
    protected boolean isNewRecord = false;


    public BaseEntity() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    @XmlTransient
    public Page<T> getPageData() {

        if (pageData == null) {
            pageData = new Page<T>();
        }
        return pageData;

    }

    public Page<T> setPageData(Page<T> pageData) {
        this.pageData = pageData;
        return pageData;
    }

    @JsonIgnore
    @XmlTransient
    public Map<String, String> getSqlMap() {
        if (sqlMap == null) {
            sqlMap = Maps.newHashMap();
        }
        return sqlMap;
    }

    public void setSqlMap(Map<String, String> sqlMap) {
        this.sqlMap = sqlMap;
    }

    /**
     * 插入之前执行方法，子类实现
     */
    public abstract void preInsert();

    /**
     * 插入之前执行方法，子类实现
     */
    //public abstract void preInsert(String loginName);


    /**
     * 更新之前执行方法，子类实现
     */
    public abstract void preUpdate();

    /**
     * 更新之前执行方法，子类实现
     */
    //public abstract void preUpdate(String loginName);

    /**
     * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     *
     * @return
     */
    @JsonIgnore
    public boolean getIsNewRecord() {
        return isNewRecord || StringUtil.isBlank(getId());
    }

    /**
     * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     */
    public void setIsNewRecord(boolean isNewRecord) {
        this.isNewRecord = isNewRecord;
    }

    /**
     * 全局变量对象
     */
    @JsonIgnore
    public Global getGlobal() {
        return Global.getInstance();
    }

    /**
     * 获取数据库名称
     */
    @JsonIgnore
    public String getDbName() {
        return Global.getConfig("jdbc.type");
    }

    /**
     * 重写eques方法
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        BaseEntity<?> that = (BaseEntity<?>) obj;
        return null == this.getId() ? false : this.getId().equals(that.getId());
    }

    /**
     * 从写toString方法
     *
     * @return
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    /**
     * 删除标记:正常{@value}
     */
    public static final Integer DEL_FLAG_NORMAL = 1;
    /**
     * 删除标记:删除{@value}
     */
    public static final Integer DEL_FLAG_DELETE = 0;

    /**
     * 管理状态:有效{@value}
     */
    public static final Integer MGR_FLAG_NORMAL = 1;
    /**
     * 管理状态:无效{@value}
     */
    public static final Integer MGR_FLAG_INVALID = 0;


    @JsonIgnore
    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    @JsonIgnore
    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }
}
