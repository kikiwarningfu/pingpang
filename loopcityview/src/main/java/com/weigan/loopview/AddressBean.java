package com.weigan.loopview;

import java.util.List;

/**
 * ClassName ${CLASS_NAME}
 * Created by Administrator on 2016/8/30.
 * DES 本地地址管理省市区三级数据
 */
public class AddressBean {
    public String name;//省级名字
    public List<CityBean> city;//市级集合

    public class CityBean {
        public String name;//市级名字
        public List<String> area;//区级集合

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getArea() {
            return area;
        }

        public void setArea(List<String> area) {
            this.area = area;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }
}
