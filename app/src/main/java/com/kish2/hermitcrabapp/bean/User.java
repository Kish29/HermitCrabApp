package com.kish2.hermitcrabapp.bean;

public class User {

    private String token;
    private String username;

    public enum GENDER {
        UNKNOWN,
        MALE,
        FEMALE
    }

    private String grade;
    private String mobile;
    private String email;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    private String studentId;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    private String department;

    public String getGender() {
        switch (gender) {
            case MALE:
                return "男";
            case FEMALE:
                return "女";
            default:
            case UNKNOWN:
                return "未设置";
        }
    }

    public void setGender(GENDER gender) {
        this.gender = gender;
    }

    private GENDER gender;

    private String avatarPath;
    /* 除了头像，其他一律保存在本地 */
    private String bannerBgPath;
    private String personalBannerBgPath;
    private String sideMenuBgPath;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getBannerBgPath() {
        return bannerBgPath;
    }

    public void setBannerBgPath(String bannerBgPath) {
        this.bannerBgPath = bannerBgPath;
    }

    public String getPersonalBannerBgPath() {
        return personalBannerBgPath;
    }

    public void setPersonalBannerBgPath(String personalBannerBgPath) {
        this.personalBannerBgPath = personalBannerBgPath;
    }

    public String getSideMenuBgPath() {
        return sideMenuBgPath;
    }

    public void setSideMenuBgPath(String sideMenuBgPath) {
        this.sideMenuBgPath = sideMenuBgPath;
    }
}
