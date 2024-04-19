import java.util.ArrayList;
import java.util.List;

public class MenuItemStrategy implements DataManagementStrategy<MenuItem>{
    static List<MenuItem> menuItems =new ArrayList<>();
    @Override
    public void addItem(MenuItem item) {
        menuItems.add(item);
    }

    @Override
    public void removeItem(String itemName) {
        int deleteIndex=-1;
        for (int i = 0; i< menuItems.size(); i++)
        {
            if(menuItems.get(i).getName().equalsIgnoreCase(itemName))
            {
                deleteIndex=i;
                break;

            }
        }
        if(deleteIndex!=-1)
            menuItems.remove(deleteIndex);

    }

    @Override
    public MenuItem findItem(String itemName) {
        int findIndex=-1;
        for (int i = 0; i< menuItems.size(); i++)
        {
            if(menuItems.get(i).getName().equalsIgnoreCase(itemName))
            {
                findIndex=i;
                break;

            }
        }
        if(findIndex!=-1)
            return menuItems.get(findIndex);
        return null;
    }
}
