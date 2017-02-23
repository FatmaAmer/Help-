package ideasgate.com.horizontallistview;

/**
 * Created by L E N O V O on 2/10/2017.
 */

public class SingleItemModel {

    private String P_name;
    private String P_price;
    private String Master_img;


    public SingleItemModel() {
    }

    public SingleItemModel(String P_name , String Master_img) {
        this.P_name = P_name;
        this.Master_img = Master_img;
    }




    public SingleItemModel( String p_name,String p_price, String master_img) {
        P_price = p_price;
        P_name = p_name;
        Master_img = master_img;
    }




    public String getMaster_img() {

        return Master_img;
    }

    public void setMaster_img(String master_img) {
        Master_img = master_img;
    }

    public String getP_name() {
        return P_name;
    }

    public void setP_name(String p_name) {
        P_name = p_name;
    }

    public String getP_price() {
        return P_price;
    }

    public void setP_price(String p_price) {
        P_price = p_price;
    }

}
