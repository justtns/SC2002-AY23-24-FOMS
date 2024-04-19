import java.util.ArrayList;
import java.util.List;

public class PaymentMethodStrategy implements DataManagementStrategy<PaymentMethod>{
  static List<PaymentMethod> paymentMethods=new ArrayList<>();
    @Override
    public void addItem(PaymentMethod item) {
        paymentMethods.add(item);
    }

    @Override
    public void removeItem(String itemName) {
        int deleteIndex=-1;
        for (int i=0;i<paymentMethods.size();i++)
        {
            if(paymentMethods.get(i).getMethod().equalsIgnoreCase(itemName))
            {
                deleteIndex=i;
                break;

            }
        }
        if(deleteIndex!=-1)
            paymentMethods.remove(deleteIndex);

    }

    @Override
    public PaymentMethod findItem(String itemName) {
        int findIndex=-1;
        for (int i=0;i<paymentMethods.size();i++)
        {
            if(paymentMethods.get(i).getMethod().equalsIgnoreCase(itemName))
            {
                findIndex=i;
                break;

            }
        }
        if(findIndex!=-1)
            return paymentMethods.get(findIndex);
        return null;
    }
}
