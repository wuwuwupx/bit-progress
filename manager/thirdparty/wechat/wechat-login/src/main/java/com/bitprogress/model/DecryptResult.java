package com.bitprogress.model;

/**
 * @author wuwuwupx
 */
public class DecryptResult {

    /**
     * 用户绑定的手机号（国外手机号会有区号）
     */
    private String phoneNumber;

    /**
     * 没有区号的手机号
     */
    private String purePhoneNumber;

    /**
     * 区号
     */
    private String countryCode;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPurePhoneNumber() {
        return purePhoneNumber;
    }

    public void setPurePhoneNumber(String purePhoneNumber) {
        this.purePhoneNumber = purePhoneNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return "PhoneResult{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", purePhoneNumber='" + purePhoneNumber + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }

}
