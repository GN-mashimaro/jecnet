package jp.co.jecnet.originapp.model;

public class CookingData {
    private int cooking_id;
    private byte[] cooking_photo_1;
    private byte[] cooking_photo_2;
    private String cooking_data;
    private String cooking_memo;


    public int getCooking_id() {
        return cooking_id;
    }

    public void setCooking_id(int cooking_id) {
        this.cooking_id = cooking_id;
    }

    public byte[] getCooking_photo_1() {
        return cooking_photo_1;
    }

    public void setCooking_photo_1(byte[] cooking_photo_1) {
        this.cooking_photo_1 = cooking_photo_1;
    }

    public byte[] getCooking_photo_2() {
        return cooking_photo_2;
    }

    public void setCooking_photo_2(byte[] cooking_photo_2) {
        this.cooking_photo_2 = cooking_photo_2;
    }

    public String getCooking_data() {
        return cooking_data;
    }

    public void setCooking_data(String cooking_data) {
        this.cooking_data = cooking_data;
    }

    public String getCooking_memo() {
        return cooking_memo;
    }

    public void setCooking_memo(String cooking_memo) {
        this.cooking_memo = cooking_memo;
    }
}
