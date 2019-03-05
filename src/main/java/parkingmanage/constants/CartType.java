package parkingmanage.constants;


import lombok.Getter;

/**
 * 汽车类型
 */
public enum CartType {
    CAR("CAR", "小汽车"),
    TRUCK("TRUCK", "货车");
    @Getter
    String code;
    @Getter
    String name;

    private CartType(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
