package com.ghca.easyview.im.framework.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ghca.easyview.im.framework.entity.User;
import com.ghca.easyview.im.framework.util.IdGenUtil;
import com.ghca.easyview.im.framework.util.StringUtil;

import java.util.Date;


/**
 * Description  数据Entity类
 *
 * @author <a href="mailto:pan.luo@ghca.com">Luopan</a>
 * @version 1.0
 * @Company: 上海光华冠群软件有限公司
 * @PROJECT_NAME easyview-vdc-master
 * @Package com.ghca.easyview.cloud.core.persistence
 * @date 2016/1/14 15:25
 */
public abstract class DataEntity<T> extends BaseEntity<T> {

    private static final long serialVersionUID = 1L;

    protected String code;    // 编号
    protected String name;    // 名称
    protected String serialnumber;//序列号
    protected Integer ciId;             //CI编号
    protected String resourceId; //资源统一id

    protected String root_code;//	联通云编号
    protected String oss_code;//	基地编号
    protected String building_code;//	楼宇编号
    protected String module_code;//	模块编号
    protected String floor_code;//	楼层编号
    protected String room_code;//	机房编号
    protected String rack_code;//	机架编号
    protected Integer biz_state;//	业务状态
    protected Integer mgr_state;//	管理状态	 1：有效 0：无效  默认：1

    protected Integer mntState;//资源状态
    protected Integer grtState;//维保状态

    protected User createUser;    // 创建者
    protected Date createTime;    // 创建日期
    protected User modifyUser;    // 更新者
    protected Date modifyTime;    // 更新日期

    private String searchKey;//搜索name或者code
    protected String preSearchKey; //列表查询前左边树的sql文件
    protected String relaSearchKey; //拼接快速连接的sql

    protected String start = "1";
    protected String limit = "-1";

    protected String flag;//是维保模板还是合同
    protected String maintCode;//维保信息编码（维保合同编码，维保模板编码）
    protected String preMaintExe;//拼接连接表
    protected String preSearchByCode;//拼接连接表

    public DataEntity() {
        super();
        this.mgr_state = MGR_FLAG_NORMAL;
    }

    /**
     *查询的时候手动调用，将会根据flag查询维保模板或维保合同
     */
    public void preMaintExe(){
        StringBuffer sb = new StringBuffer();
        StringBuffer buf = new StringBuffer();
        /*if(this.flag.equals("1")){
            this.preMaintExe = buf.append("  LEFT JOIN biz_maintenance_contract Z ON Z.CODE = I.MAINT_INFO_CODE  ").toString();//" LEFT JOIN biz_maintenance_contract Z ON Z.CODE = I.MAINT_INFO_CODE ";
            this.preSearchByCode = sb.append("  AND tb.CIID = I.CIID AND Z.CODE=").append("'").append(this.maintCode).append("'  ").toString();//" AND tb.CIID = I.CIID AND Z.CODE='"+this.maintCode+"'";
        }
        if(this.flag.equals("2")){
            this.preMaintExe = "   LEFT JOIN biz_maintenance_template T ON T.CODE = I.MAINT_INFO_CODE  ";
            this.preSearchByCode = sb.append("  AND tb.CIID = I.CIID AND T.CODE='").append(this.maintCode).append("'  ").toString();//"AND tb.CIID = I.CIID AND AND T.CODE='"+this.maintCode+"'";
        }*/
    }

    /**
     * 查询的时候 手动执行一下这个方式 把左边的树的sql
     */
    public void preSearch(){
        StringBuffer buffer = new StringBuffer();

        /*if(StringUtils.isNotEmpty(this.oss_code)) {//基地
            buffer.append("  and tb.OSS_CODE = '").append(this.oss_code).append("'  ");
        }
        if (StringUtils.isNotEmpty(this.building_code)) {//楼宇
            buffer.append("  and tb.BUILDING_CODE = '").append(this.building_code).append("'  ");
        }
        if (StringUtils.isNotEmpty(this.module_code)) {//模块
            buffer.append("  and tb.MODULE_CODE = '").append(this.module_code).append("'  ");
        }
        if (StringUtils.isNotEmpty(this.floor_code)) {//楼层
            buffer.append("  and tb.FLOOR_CODE = '").append(this.floor_code).append("'  ");
        }
        if (StringUtils.isNotEmpty(this.room_code)) {//机房
            buffer.append("  and tb.ROOM_CODE = '").append(this.room_code).append("'  ");
        }
        if (buffer != null){//至少选择的是基地，或者楼宇等等
            this.preSearchKey = buffer.toString();
        }
        System.out.println("TYPE 是-》"+this.getClass().getSimpleName());
        String className = this.getClass().getSimpleName();
        RoleSpaceUtil rs =  new RoleSpaceUtil();
        this.preSearchKey = rs.getSerachSql(className,this.preSearchKey,"'"+this.oss_code+"'","'"+this.building_code+"'","'"+this.module_code+"'","'"+this.floor_code+"'","'"+this.room_code+"'");*/
    }

    /**
     * 由于空间资源没有下级资源的code字段，会导致出错，添加一个方法以供空间资源使用
     * @param spaceType
     */
    public void preSearch(String spaceType){
        StringBuffer buffer = new StringBuffer();

        /*if("room".equals(spaceType)){
            if(StringUtils.isNotEmpty(this.oss_code)) {//基地
                buffer.append("  and tb.OSS_CODE = '").append(this.oss_code).append("'  ");
            }
            if (StringUtils.isNotEmpty(this.building_code)) {//楼宇
                buffer.append("  and tb.BUILDING_CODE = '").append(this.building_code).append("'  ");
            }
            if (StringUtils.isNotEmpty(this.module_code)) {//模块
                buffer.append("  and tb.MODULE_CODE = '").append(this.module_code).append("'  ");
            }
            if (StringUtils.isNotEmpty(this.floor_code)) {//楼层
                buffer.append("  and tb.FLOOR_CODE = '").append(this.floor_code).append("'  ");
            }
            if (StringUtils.isNotEmpty(this.room_code)) {//机房
                buffer.append("  and tb.CODE = '").append(this.room_code).append("'  ");
            }
        }else {

        }




        if (buffer != null){//至少选择的是基地，或者楼宇等等
            this.preSearchKey = buffer.toString();
        }
        System.out.println("TYPE 是-》"+this.getClass().getSimpleName());
        String className = this.getClass().getSimpleName();
        RoleSpaceUtil rs =  new RoleSpaceUtil();
        this.preSearchKey = rs.getSerachSql(className,this.preSearchKey,"'"+this.oss_code+"'","'"+this.building_code+"'","'"+this.module_code+"'","'"+this.floor_code+"'","'"+this.room_code+"'");
        */
    }

    /**
     * 插入之前执行方法，需要手动调用
     */
    @Override
    public void preInsert() {
        // 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
        if (!this.isNewRecord) {
            setId(IdGenUtil.uuid());
        }
//        User user = UserUtils.getCurrentUser();
        User user = new User();
        if (StringUtil.isNotBlank(user.getId())) {
            this.modifyUser = user;
            this.createUser = user;
        }
        this.modifyTime = new Date();
        this.createTime = this.modifyTime;
    }

    /**
     * 插入之前执行方法，需要手动调用
     */
    //@Override
    public void preInsert(String loginName) {
        if (this.isNewRecord) {
            setId(IdGenUtil.uuid());
        }
        User user = new User();
        if (StringUtil.isBlank(loginName))
            loginName = "admin";
        user.setId(loginName);

        this.modifyUser = user;
        this.createUser = user;
        this.modifyTime = new Date();
        this.createTime = this.modifyTime;
        //this.setMgr_state(1);
        //this.setBiz_state(1);
    }


    @Override
    public void preUpdate() {
//        User user = UserUtils.getCurrentUser();

        User user = new User();
        if (StringUtil.isNotBlank(user.getId())) {
            this.modifyUser = user;
        }

        this.modifyTime = new Date();
    }

    //@Override
    public void preUpdate(String loginName) {

        User user = new User();
        if (StringUtil.isBlank(loginName))
            loginName = "admin";
        user.setId(loginName);

        this.modifyUser = user;
        this.modifyTime = new Date();
    }

    public String getPreSearchByCode() {
        return preSearchByCode;
    }

    public void setPreSearchByCode(String preSearchByCode) {
        this.preSearchByCode = preSearchByCode;
    }

    public String getPreSearchKey() {
        return preSearchKey;
    }

    public void setPreSearchKey(String preSearchKey) {
        this.preSearchKey = preSearchKey;
    }

    public String getPreMaintExe() {
        return preMaintExe;
    }

    public void setPreMaintExe(String preMaintExe) {
        this.preMaintExe = preMaintExe;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMaintCode() {
        return maintCode;
    }

    public void setMaintCode(String maintCode) {
        this.maintCode = maintCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public Integer getCiId() {
        return ciId;
    }

    public void setCiId(Integer ciId) {
        this.ciId = ciId;
    }

    public String getRoot_code() {
        return root_code;
    }

    public void setRoot_code(String root_code) {
        this.root_code = root_code;
    }

    public String getOss_code() {
        return oss_code;
    }

    public void setOss_code(String oss_code) {
        this.oss_code = oss_code;
    }

    public String getBuilding_code() {
        return building_code;
    }

    public void setBuilding_code(String building_code) {
        this.building_code = building_code;
    }

    public String getModule_code() {
        return module_code;
    }

    public void setModule_code(String module_code) {
        this.module_code = module_code;
    }

    public String getFloor_code() {
        return floor_code;
    }

    public void setFloor_code(String floor_code) {
        this.floor_code = floor_code;
    }

    public String getRoom_code() {
        return room_code;
    }

    public void setRoom_code(String room_code) {
        this.room_code = room_code;
    }

    public String getRack_code() {
        return rack_code;
    }

    public void setRack_code(String rack_code) {
        this.rack_code = rack_code;
    }

    public Integer getBiz_state() {
        return biz_state;
    }

    public void setBiz_state(Integer biz_state) {
        this.biz_state = biz_state;
    }

    public Integer getMgr_state() {
        return mgr_state;
    }

    public void setMgr_state(Integer mgr_state) {
        this.mgr_state = mgr_state;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(User lastModifyUser) {
        this.modifyUser = lastModifyUser;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date lastModifyTime) {
        this.modifyTime = lastModifyTime;
    }

    @JsonIgnore
    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    @JsonIgnore
    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getMntState() {
        return mntState;
    }

    public void setMntState(Integer mntState) {
        this.mntState = mntState;
    }

    public Integer getGrtState() {
        return grtState;
    }

    public void setGrtState(Integer grtState) {
        this.grtState = grtState;
    }

    public String getRelaSearchKey() {
        return relaSearchKey;
    }

    public void setRelaSearchKey(String relaSearchKey) {
        this.relaSearchKey = relaSearchKey;
    }
}
