import java.util.ArrayList;
import java.util.List;

public class BranchManagementStrategy implements DataManagementStrategy<Branch> {
    static List<Branch> allBranches=new ArrayList<>();
    @Override
    public void addItem(Branch item) {
        allBranches.add(item);
    }

    @Override
    public void removeItem(String itemName) {
        int deleteIndex=-1;
        for (int i=0;i<allBranches.size();i++)
        {
            if(allBranches.get(i).getBranchName().equalsIgnoreCase(itemName))
            {
                deleteIndex=i;
                break;

            }
        }
        if(deleteIndex!=-1)
            allBranches.remove(deleteIndex);

    }

    @Override
    public Branch findItem(String itemName) {
        int findIndex=-1;
        for (int i=0;i<allBranches.size();i++)
        {
            if(allBranches.get(i).getBranchName().equalsIgnoreCase(itemName))
            {
                findIndex=i;
                break;

            }
        }
        if(findIndex!=-1)
            return allBranches.get(findIndex);
        return null;
    }
}



