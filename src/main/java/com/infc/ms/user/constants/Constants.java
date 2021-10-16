package com.infc.ms.user.constants;

public class Constants {

    public static final String FETCH_USER="SELECT user_id,country_phone_code,created_date_time,device_id,mobile_number,public_key,status FROM TM_USER WHERE mobile_number = ? AND DEVICE_ID=? and STATUS=?";
    public static final String FETCH_USER_BY_USER_ID_AND_STATUS="SELECT user_id,country_phone_code,created_date_time,device_id,mobile_number,public_key,status FROM TM_USER WHERE mobile_number = ?  and STATUS=?";
    public static final String INSERT_USER="INSERT INTO TM_USER(user_id,country_phone_code,created_date_time,device_id,mobile_number,public_key,status)" +
            " (?,?,?,?,?,?) RETURNING user_id";


}
