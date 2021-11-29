package bo;

import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.ItemBOImpl;
import bo.custom.impl.PlaceOrderBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBOFactory() {
        if (boFactory == null) {
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public SuperBO getBO(BoTypes types) {
        switch (types) {
            case ITEM:
                return new ItemBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case  PLACE_ORDER:
                return new PlaceOrderBOImpl();
            default:
                return null;
        }
    }

    public enum BoTypes {
        CUSTOMER, ITEM, PLACE_ORDER
    }
}
