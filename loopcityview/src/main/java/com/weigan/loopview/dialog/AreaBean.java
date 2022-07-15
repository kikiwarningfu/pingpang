package com.weigan.loopview.dialog;

import java.util.List;

/**
 * Created by Administrator on 2017/10/26 0026.
 */

public class AreaBean {

    private String areaName;
    private int areaId;
    private int areaorderby;
    private List<AreaBean> children;


    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getAreaorderby() {
        return areaorderby;
    }

    public void setAreaorderby(int areaorderby) {
        this.areaorderby = areaorderby;
    }

    public List<AreaBean> getChildren() {
        return children;
    }

    public void setChildren(List<AreaBean> children) {


        this.children = children;
    }

    @Override
    public String toString() {
        return areaName;
    }
}
