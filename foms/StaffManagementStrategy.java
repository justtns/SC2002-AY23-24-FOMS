import java.util.ArrayList;
import java.util.List;

public class StaffManagementStrategy implements DataManagementStrategy<Staff> {
    static List<Staff> allStaff=new ArrayList<>();
    @Override
    public void addItem(Staff item) {
        allStaff.add(item);
    }

    @Override
    public void removeItem(String itemName) {
        int deleteIndex=-1;
        for (int i=0;i<allStaff.size();i++)
        {
            if(allStaff.get(i).getName().equalsIgnoreCase(itemName))
            {
                deleteIndex=i;
                break;

            }
        }
        if(deleteIndex!=-1)
            allStaff.remove(deleteIndex);

    }

    @Override
    public Staff findItem(String itemName) {
        int findIndex=-1;
        for (int i=0;i<allStaff.size();i++)
        {
            if(allStaff.get(i).getName().equalsIgnoreCase(itemName))
            {
                findIndex=i;
                break;

            }
        }
        if(findIndex!=-1)
            return allStaff.get(findIndex);
        return null;
    }
}
