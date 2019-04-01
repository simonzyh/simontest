package parkingmanage.model;

import lombok.Data;

import java.util.Date;

/**
 * base model
 */
@Data
public class BaseModel implements java.io.Serializable {

    private Long id;

    private Date gmtCreated;


    private Date gmtUpdated;

    private Integer dataVersion;

}
