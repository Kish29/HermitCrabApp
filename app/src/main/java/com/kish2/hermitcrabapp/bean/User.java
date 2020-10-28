package com.kish2.hermitcrabapp.bean;

public class User {

    private long uid;
    private String token;
    private String username;
    private String grade;
    private String regDate;
    private String mobile;
    private String email;
    private String gender;
    private String avatarPath;
    private String userType;
    private String status;
    /* 除了头像，其他一律保存在本地 */
    private String bannerBgPath;
    private String personalBannerBgPath;
    private String sideMenuBgPath;
    private boolean infoBind = false;

    public boolean isInfoBind() {
        return infoBind;
    }

    public void setInfoBind(boolean infoBind) {
        this.infoBind = infoBind;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

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
