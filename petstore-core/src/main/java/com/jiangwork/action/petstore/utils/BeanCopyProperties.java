package com.jiangwork.action.petstore.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.scheduling.annotation.Scheduled;

public class BeanCopyProperties {

    public static class OneBean {
        private String name;
        private String phone;
        private String home;
        @Scheduled
        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getHome() {
            return home;
        }

        public void setHome(String home) {
            this.home = home;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "OneBean{" +
                    "name='" + name + '\'' +
                    ", phone='" + phone + '\'' +
                    ", home='" + home + '\'' +
                    '}';
        }

        public OneBean(String name, String phone, String home) {
            this.name = name;
            this.phone = phone;
            this.home = home;
        }

        public OneBean() {}
    }

    public static class TwoBean {
        private String name;
        private String home;
        private String school;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHome() {
            return home;
        }

        public void setHome(String home) {
            this.home = home;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        @Override
        public String toString() {
            return "TwoBean{" +
                    "name='" + name + '\'' +
                    ", home='" + home + '\'' +
                    ", school='" + school + '\'' +
                    '}';
        }

        public TwoBean(String name, String home, String school) {
            this.name = name;
            this.home = home;
            this.school = school;
        }

        public TwoBean() {}
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        OneBean one = new OneBean("name", "phone", "home");
        TwoBean two = new TwoBean();
        BeanUtils.copyProperties(one, two);
        System.out.println(one);
        System.out.println(two);
    }

}
