import java.util.ArrayList;
import java.util.List;

public class OrdersManagementStrategy implements DataManagementStrategy<Order>{
    List<Order> orderList=new ArrayList<>();
    @Override
    public void addItem(Order item) {
        orderList.add(item);
    }

    @Override
    public void removeItem(String itemName) {
        int deleteIndex=-1;
        for (int i=0;i<orderList.size();i++)
        {
            if(orderList.get(i).getOrderId()==Integer.parseInt(itemName))
            {
                deleteIndex=i;
                break;

            }
        }
        if(deleteIndex!=-1)
            orderList.remove(deleteIndex);

    }

    @Override
    public Order findItem(String itemName) {
        int findIndex=-1;
        for (int i=0;i<orderList.size();i++)
        {
            if(orderList.get(i).getOrderId()==Integer.parseInt(itemName))
            {
                findIndex=i;
                break;

            }
        }
        if(findIndex!=-1)
            return orderList.get(findIndex);
        return null;
    }
}
