package com.work.boot.pojo.vo;

import com.work.boot.pojo.entity.Guarantee;
import lombok.Data;

@Data
public class GuaranteeAllVo extends Guarantee {
    private String maintenanceusername;
    private String uphone;
    private String uUsername;


}
